package vip.hoody.wechat.api

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
import vip.hoody.wechat.domain.media.MaterialType
import vip.hoody.wechat.domain.media.MediaItem
import vip.hoody.wechat.domain.media.MediaNewsPage
import vip.hoody.wechat.domain.media.MediaOtherPage
import vip.hoody.wechat.domain.media.NewsItem
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.domain.media.NewsMedia
import vip.hoody.wechat.exception.WechatFileNotExists
import vip.hoody.wechat.exception.WechatMediaException
import vip.hoody.wechat.utils.HttpUtil


/**
 * 素材管理
 * 临时:
 * 1、临时素材media_id是可复用的。
 * 2、媒体文件在微信后台保存时间为3天，即3天后media_id失效。
 * 3、上传临时素材的格式、大小限制与公众平台官网一致。
 *
 * 永久:
 * 1、最近更新：永久图片素材新增后，将带有URL返回给开发者，开发者可以在腾讯系域名内使用（腾讯系域名外使用，图片将被屏蔽）。
 * 2、公众号的素材库保存总数量有上限：图文消息素材、图片素材上限为5000，其他类型为1000。
 * 3、素材的格式大小等要求与公众平台官网一致：
 *
 * TODO 素材管理
 */
@Component
class MediaAPI {

    @Autowired
    private HttpUtil httpUtil

    private RestTemplate restTemplate

    @Autowired
    private WeChatApi weChatApi

    MediaAPI() {
        this.restTemplate = new RestTemplate()
    }

    /**
     * 获取微信访问令牌
     * @return access_token
     */
    String getAccessToken() {
        return weChatApi.getAccessToken()
    }

    /**
     * 上传临时素材
     * @param filePath 文件路径
     * @param type 素材类型
     * @return JSONObject <br>
     *  "type":"TYPE", <br>
     *  "media_id":"MEDIA_ID",<br>
     *  "created_at":123456789 <br>
     *  </pre>
     *
     * @throws WechatMediaException
     */
    Map<String, String> uploadTemporaryMedia(String filePath, MediaType type) throws WechatMediaException {
        //检查文件是否符合微信要求
        fileCheck(filePath, type)
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
     *  获取临时视频素材
     *  TODO 增加重载,可以附带存储路径
     * @param mediaId 临时视频素材id
     * @return file 临时目录下的文件
     */
    File getTemporaryMedia(String mediaId) {
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

    /**
     * 上传图文消息内的图片
     * @param imgFilePath 图片仅支持jpg/png格式
     * @return 图片访问url
     */
    String uploadNewsImg(String imgFilePath) {
        //检查文件是否符合微信接口要求
        fileCheck(imgFilePath, MediaType.IMAGE)
        //接口地址
        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=${getAccessToken()}"
        //准备请求参数
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>()
        parts.add("file", new FileSystemResource(imgFilePath))
        //发送请求,获取响应内容
        String resultString = restTemplate.postForObject(url, parts, String.class)
        JSONObject result = new JSONObject(resultString)
        //根据返回值判断是否上传成功
        if (result.isNull("url")) {
            throw new WechatMediaException("upload news images fail :${result.toString()}")
        }
        //返回上传成功后访问的url
        return result.getString("url")
    }

    /**
     * 新增图文素材
     * @param item
     * @return
     */
    String addNews(List<NewsItem> items) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=${getAccessToken()}"

        String newsJsons = ""
        items.each { item ->
            newsJsons += "${item.toJSON()},"
        }
        newsJsons = """{"articles":[${newsJsons}]}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(newsJsons, headers)
        String resultStr = restTemplate.postForObject(url, entity, String.class)
        JSONObject result = new JSONObject(resultStr)
        if (result.isNull("media_id")) {
            throw new WechatMediaException("upload news fail :${result.toString()}")
        }
        return result.getString("media_id")
    }

    /**
     * 修改图文素材(永久图文素材)
     * @param mediaId 要修改的图文消息的id
     * @param item
     * @param index 要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @return
     * todo
     */
    void updateMedia(String mediaId, NewsItem item, String index) {
        Map<String, String> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=${getAccessToken()}",
                """
                    {  
                        "media_id":"${mediaId}",
                        "index":"${index}",
                        "articles": ${item.toJSON()}
                    }"""
        )
        if (result.errcode != null && result.errcode != 0) {
            throw new WechatMediaException("update news fail:${result.toString()}")
        }
    }


    /**
     * 上传视频永久素材
     * @param filePath 文件路径
     * @param title 视频标题
     * @param introduction 视频介绍
     * @return mediaId 资源编号
     */
    String uploadVideoMedia(String filePath, String title, String introduction) {
        // 检查文件
        fileCheck(filePath, MediaType.VIDEO)
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
     * 上传永久素材分别有图片（image）、语音（voice）、缩略图（thumb）
     * 视频（video）上传使用uploadVideoMedia 方法
     * @param filePath
     * @param type
     * @return 微信素材media_id
     */
    String uploadMedia(String filePath, MediaType type) {
        //检查上传文件是否符合微信接口要求
        fileCheck(filePath, type)
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
     * 获取永久素材总数
     * 开发者可以根据本接口来获取"永久素材"的列表，需要时也可保存到本地。
     * 图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
     * @return map <pre>
     *  "voice_count":COUNT,<br>
     *  "video_count":COUNT,<br>
     *  "image_count":COUNT,<br>
     *  "news_count":COUNT<br>
     *  </pre>
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
     * 获取永久图文素材列表
     * 在新增了永久素材后，开发者可以分类型获取永久素材的列表。
     * 请注意：临时素材无法通过本接口获取
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count 返回素材的数量 ，取值在1到20之间
     */
    MediaNewsPage getMediaNewsList(int offset, int count) {
        Map<String, Object> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=${weChatApi.getAccessToken()}",
                """{"type":${MaterialType.NEWS.key},"offset":${offset},"count":${count}}"""
        )
        if (result.errcode != null && result.errcode != 0) {
            throw new WechatMediaException("get news list fail:${result.toString()}")
        }
        MediaNewsPage page = new MediaNewsPage(result.total_count, result.item_count)
        result.item.each { newsData ->
            NewsMedia newsMedia = new NewsMedia(newsData.media_id, newsData.update_time)
            newsData.content.news_item.each { Map<String, String> item ->
                newsMedia.content.add(new NewsItem(
                        item.title,
                        item.thumb_media_id,
                        item.show_cover_pic,
                        item.author,
                        item.digest,
                        item.content,
                        item.url,
                        item.content_source_url
                ))
            }
            page.newsItemList.add(newsMedia)
        }
        return page
    }

    /**
     * 获取永久素材列表
     * 图片、语音、视频
     * @param type 素材类型
     * @param offset 偏移量
     * @param count 获取数
     * @return
     */
    MediaOtherPage getMediaOtherList(MaterialType type, int offset, int count) {
        Map<String, Object> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=${weChatApi.getAccessToken()}",
                """{"type":${type.key},"offset":${offset},"count":${count}}"""
        )
        if (result.errcode != null && result.errcode != 0) {
            throw new WechatMediaException("get news list fail:${result.toString()}")
        }
        MediaOtherPage page = new MediaOtherPage(newsData.media_id, newsData.update_time)
        result.item.each { Map<String, String> item ->
            page.items.add(new MediaItem(
                    item.media_id,
                    item.name,
                    item.update_time,
                    item.url,
            ))
        }
        return page
    }


    /**
     *  获取图文永久素材
     *  其他类型 使用 getMedia(mediaId)
     * @param mediaId
     */
    List<NewsItem> getNewsMedia(String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${getAccessToken()}"
        String param = """{"media_id":"${mediaId}" }"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        headers.setAccept([org.springframework.http.MediaType.APPLICATION_JSON_UTF8])
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        JSONObject object = new JSONObject(result)
        if (!object.isNull("errcode")) {
            throw new WechatMediaException("get news media fail :${result}")
        }
        List<NewsItem> list = new ArrayList<>()
        JSONArray items = object.getJSONArray("news_item")
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = (JSONObject) items.get(i)
            NewsItem newsItem = new NewsItem(
                    item.getString("title"),
                    item.getString("thumb_media_id"),
                    item.getString("show_cover_pic"),
                    item.getString("author"),
                    item.getString("digest"),
                    item.getString("content"),
                    item.getString("url"),
                    item.getString("content_source_url")
            )
            list << newsItem
        }
        return list
    }

    /**
     *  获取永久素材
     *  不包括图文News素材
     * @param mediaId
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
     * 删除永久素材
     * @param mediaId 素材ID
     */
    void deleteMedia(String mediaId) {
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
     * 检查上传素材文件
     * @param filePath
     * @param type
     */
    private static void fileCheck(String filePath, MediaType type) {
        File file = new File(filePath)
        if (!file.exists() || !file.isFile()) {
            throw new WechatFileNotExists(filePath)
        }
        checkFileSize(file, type)
    }


    /**
     * 检查素材文件体积
     * @param filePath
     * @param type
     */
    private static void checkFileSize(File file, MediaType type) {
        switch (type) {
            case MediaType.IMAGE:
                if (file.length() > 2 * 1024 * 1024) {
                    throw new WechatMediaException("IMAGE file must less than 2m")
                }
                break
            case MediaType.VOICE:
                if (file.length() > 2 * 1024 * 1024) {
                    throw new WechatMediaException("VOICE file must less than 2m")
                }
                break
            case MediaType.VIDEO:
                if (file.length() > 10 * 1024 * 1024) {
                    throw new WechatMediaException("VIDEO file must less than 10m")
                }
                break
            case MediaType.THUMB:
                if (file.length() > 64 * 1024) {
                    throw new WechatMediaException("THUMB file must less than 64kb")
                }
                break
        }
    }
}
