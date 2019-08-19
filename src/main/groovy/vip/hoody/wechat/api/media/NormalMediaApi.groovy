package vip.hoody.wechat.api.media

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONArray
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.ResponseExtractor
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.WeChatApi
import vip.hoody.wechat.domain.media.MediaItem
import vip.hoody.wechat.domain.media.MediaOtherPage
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.exception.WechatMediaException

@Component
class NormalMediaApi {
    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private WeChatApi weChatApi

    private String getAccessToken() {
        return weChatApi.getAccessToken()
    }

    /**
     * upload media(image/voice/thumb) <br>
     * 上传永久素材 (图片/音频/缩略图)
     *
     * @param filePath absolute file path
     * @param type MediaType image/voice/thumb
     * @return media id
     * @throws WechatMediaException api error
     */
    String uploadMedia(String filePath, MediaType type) {
        //检查上传文件是否符合微信接口要求
        FileCheck.check(filePath, type)
        //微信接口地址
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=${getAccessToken()}&type=${type.key}"
        //准备参数
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>()
        parts.add("media", new FileSystemResource(filePath))
        //发送请求并分析结果
        String resultString = restTemplate.postForObject(url, parts, String.class)
        JSONObject result = new JSONObject(resultString)
        if (!result.isNull("errcode")) {
            throw new WechatMediaException("upload media fail :${result.toString()}")
        }
        return result.getString("media_id")
    }

    /**
     * upload video media 上传视频永久素材<br>
     * ps: <br>
     * upload others media type <br>
     * use this.uploadMedia(String filePath, MediaType type)
     *
     * @param filePath absolute file path
     * @param title video title
     * @param introduction video introduction
     * @return mediaId* @throws WechatMediaException api error
     */
    String uploadVideoMedia(String filePath, String title, String introduction) throws WechatMediaException {
        // 检查文件
        FileCheck.check(filePath, MediaType.VIDEO)
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=${getAccessToken()}&type=${MediaType.VIDEO.key}"
        //准备参数
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>()
        //添加文件
        parts.add("media", new FileSystemResource(filePath))
        //添加Json
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        //微信接口要求的视频描述参数
        String description = """{
                                    "title":"${title}",
                                    "introduction":"${introduction}"
                                }"""
        parts.add("description", new HttpEntity<>(description, headers))
        //发送请求
        String resultString = restTemplate.postForObject(url, parts, String.class)
        JSONObject result = new JSONObject(resultString)
        if (!result.isNull("errcode")) {
            throw new WechatMediaException("upload media fail :${result.toString()}")
        }
        return result.getString("media_id")
    }

    /**
     * delete media 删除永久素材 <br>
     * image/voice/news/thumb
     *
     * @param mediaId wechat media_id
     * @throws WechatMediaException api error
     */
    protected void deleteMedia(String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=${getAccessToken()}"
        String param = """{"media_id":"${mediaId}"} """
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        headers.setAccept([org.springframework.http.MediaType.APPLICATION_JSON_UTF8])
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = restTemplate.postForObject(url, entity, String.class)

        JSONObject object = new JSONObject(result)
        if (object.getString("errcode") != "0") {
            throw new WechatMediaException("delete media material fail :${result}")
        }
    }

    /**
     * get media file  获取永久素材<br>
     * ps:<br>
     * get news media <br>
     * use: getNewsMedia(String mediaId)
     *
     * @param mediaId wechat media_id
     * @return file in temp
     * @throws WechatMediaException api error
     */
    File getMedia(String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${getAccessToken()}"
        String json = """{"media_id":"${mediaId}"}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers)
        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, httpEntity, byte[].class)
        if (responseEntity.getHeaders().getContentType() == org.springframework.http.MediaType.TEXT_PLAIN) {
            String str = new String(responseEntity.body)
            JSONObject result = new JSONObject(str)
            if (!result.isNull("errcode")) {
                throw new WechatMediaException("get Media fail :${result.toString()}")
            }
            String videoUrl = new JSONObject(str).getString("down_url")
            return downloadVideo(videoUrl)
        } else {
            String filename = responseEntity.getHeaders().getContentDisposition().getFilename()
            File file = File.createTempFile(
                    "wechat-${filename.substring(0, filename.lastIndexOf("."))}",
                    filename.substring(filename.lastIndexOf("."), filename.length())
            )
            file.deleteOnExit()
            FileOutputStream fileOutputStream = new FileOutputStream(file)
            fileOutputStream.write(responseEntity.getBody())
            fileOutputStream.close()
            return file
        }
    }

    /**
     * get media count group by type<br>
     * 获取永久素材总数<br>
     * 图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
     *
     * @return map <pre>
     *      "voice_count":COUNT,<br>
     *      "video_count":COUNT,<br>
     *      "image_count":COUNT,<br>
     *      "news_count":COUNT<br>
     *      </pre>
     * @throws WechatMediaException api error
     */
    Map<String, Integer> getMediaCount() {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=${getAccessToken()}"
        String result = restTemplate.getForObject(url, String.class)
        JSONObject jsonObject = new JSONObject(result)
        if (!jsonObject.isNull("errcode")) {
            throw new WechatMediaException("get media count fail :${result.toString()}")
        }

        return ["voice_count": jsonObject.getInt("voice_count"),
                "video_count": jsonObject.getInt("video_count"),
                "image_count": jsonObject.getInt("image_count"),
                "news_count" : jsonObject.getInt("news_count")]
    }

    /**
     * 获取永久素材列表
     * 图片、语音、视频
     * @param type 素材类型
     * @param offset 偏移量
     * @param count 获取数
     * @return
     */
    MediaOtherPage getMediaOtherList(MediaType type, int offset, int count) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=${weChatApi.getAccessToken()}"
        String param = """{"type":"${type.key}","offset":${offset},"count":${count}}"""

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = new RestTemplate().postForObject(url, entity, String.class)
        JSONObject object = new JSONObject(result)
        if (!object.isNull("errcode")) {
            throw new WechatMediaException("get media list fail:${result}")
        }
        MediaOtherPage page = new MediaOtherPage(object.getInt("total_count"), object.getInt("item_count"))
        JSONArray items = object.getJSONArray("item")
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i)
            MediaItem mediaItem = new MediaItem(
                    item.getString("media_id"),
                    item.getString("name"),
                    item.getString("update_time"),
//                    item.getString("url")
            )
            page.getItems().add(mediaItem)
        }
        return page
    }

    /**
     * 下载文件并存储到临时文件
     * @param url 文件地址
     * @return file 临时文件
     */
    private File downloadVideo(url) {
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
