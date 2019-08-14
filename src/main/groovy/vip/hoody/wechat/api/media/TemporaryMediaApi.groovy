package vip.hoody.wechat.api.media

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.ResponseExtractor
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.WeChatApi
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.exception.WechatMediaException

@Component
class TemporaryMediaApi {

    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private WeChatApi weChatApi

    private String getAccessToken() {
        return weChatApi.getAccessToken()
    }

    protected Map<String, String> uploadTemporaryMedia(String filePath, MediaType type) throws WechatMediaException {
        //检查文件是否符合微信要求
        FileCheck.check(filePath, type)
        //接口地址
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=${getAccessToken()}&type=${type.toString()}"
        //创建请求参数
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>()
        parts.add("file", new FileSystemResource(filePath))
        //发送请求
        String resultString = restTemplate.postForObject(url, parts, String.class)
        JSONObject result = new JSONObject(resultString)
        //判断微信接口响应
        if (!result.isNull("errcode")) {
            throw new WechatMediaException("upload media fail :${result.toString()}")
        }
        //返回数据
        return [
                type      : result.getString("type"),
                media_id  : result.getString("media_id"),
                created_at: result.getString("created_at"),
        ]
    }

    /**
     * 下载临时素材
     * TODO 增加重载,可以附带存储路径
     *
     * @param mediaId 素材编号
     * @return file 临时目录下的文件
     * @throws WechatMediaException api error
     */
    protected File getTemporaryMedia(String mediaId) {
        //微信接口地址
        String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=${getAccessToken()}&media_id=${mediaId}"

        File mediaFile = restTemplate.execute(url, HttpMethod.GET, null,
                //自定义处理器
                new ResponseExtractor<File>() {
                    @Override
                    File extractData(ClientHttpResponse response) throws IOException {
                        //判断响应数据类型,如果是text/plain 则解析内容
                        if (response.getHeaders().getContentType() == org.springframework.http.MediaType.TEXT_PLAIN) {
                            InputStream inputStream = response.getBody()
                            byte[] bytes = new byte[inputStream.available()]
                            inputStream.read(bytes)
                            String str = new String(bytes)
                            JSONObject result = new JSONObject(str)
                            //判断响应数据是否微信错误提示
                            if (!result.isNull("errcode")) {
                                throw new WechatMediaException("get Temporary Media fail :${result.toString()}")
                            }
                            //如果是视频地址,则下载
                            String videoUrl = new JSONObject(str).getString("video_url")
                            return downloadVideo(videoUrl)
                        }
                        //如果不是文本,则直接将输入流保存为文件
                        else {
                            InputStream inputStream = response.getBody()
                            //获取文件名
                            String filename = response.getHeaders().getContentDisposition().getFilename()
                            //使用返回文件名创建临时文件,并写入输入流
                            File file = File.createTempFile(
                                    filename.substring(0, filename.lastIndexOf(".")),
                                    filename.substring(filename.lastIndexOf("."), filename.length())
                            )
                            file.deleteOnExit()
                            FileOutputStream fileOutputStream = new FileOutputStream(file)
                            byte[] bytes = new byte[1024]
                            int ch
                            while ((ch = inputStream.read(bytes)) > -1) {
                                fileOutputStream.write(bytes, 0, ch);
                            }
                            fileOutputStream.close()
                            //返回临时文件
                            return file
                        }
                    }
                })

        return mediaFile
    }

    /**
     * 下载文件并存储到临时文件
     * @param url 文件地址
     * @return file 临时文件
     */
    protected File downloadVideo(url) {
        File mediaFile = restTemplate.execute(url, HttpMethod.GET, null, new ResponseExtractor<File>() {
            @Override
            File extractData(ClientHttpResponse response) throws IOException {
                //获取输入流
                InputStream inputStream = response.getBody()
                //获取文件名
                String filename = response.getHeaders().getContentDisposition().getFilename()
                //使用响应文件名,创建临时文件
                File file = File.createTempFile(
                        filename.substring(0, filename.lastIndexOf(".")),
                        filename.substring(filename.lastIndexOf("."), filename.length())
                )
                file.deleteOnExit()
                //写入输入流到临时文件
                FileOutputStream fileOutputStream = new FileOutputStream(file)
                byte[] bytes = new byte[1024]
                int ch
                while ((ch = inputStream.read(bytes)) > -1) {
                    fileOutputStream.write(bytes, 0, ch);
                }
                fileOutputStream.close();
                //返回文件
                return file
            }
        })
        return mediaFile
    }

}
