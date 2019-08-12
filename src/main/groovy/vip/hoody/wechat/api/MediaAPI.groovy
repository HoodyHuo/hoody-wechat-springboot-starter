package vip.hoody.wechat.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONException
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpRequest
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RequestCallback
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

    private static final String baseUrl = "https://api.weixin.qq.com/cgi-bin/media"

    @Autowired
    private HttpUtil httpUtil

    private RestTemplate restTemplate

    @Autowired
    private WeChatApi weChatApi

    MediaAPI() {
        this.restTemplate = new RestTemplate()
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
    JSONObject updateTemporaryMedia(String filePath, MediaType type) throws WechatMediaException {
        fileCheck(filePath, type)
        String url = "${baseUrl}/upload?access_token=${weChatApi.getAccessToken()}&type=${type.toString()}"
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>()
        parts.add("file", new FileSystemResource(filePath))
        String resultString = restTemplate.postForObject(url, parts, String.class)
        JSONObject result = new JSONObject(resultString)
        if (!result.isNull("errcode")) {
            throw new WechatMediaException("upload media fail :${result.toString()}")
        }
        return result
    }

    /**
     *  获取临时视频素材
     *  TODO 增加重载,可以附带存储路径
     * @param mediaId 临时视频素材id
     * @return url 下载url
     */
    File getTemporaryMedia(String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=${weChatApi.getAccessToken()}&media_id=${mediaId}"

        File mediaFile = restTemplate.execute(url, HttpMethod.GET, null, new ResponseExtractor<File>() {
            @Override
            File extractData(ClientHttpResponse response) throws IOException {
                if (response.getHeaders().getContentType() == org.springframework.http.MediaType.TEXT_PLAIN) {
                    InputStream inputStream = response.getBody()
                    byte[] bytes = new byte[inputStream.available()]
                    inputStream.read(bytes)
                    String str = new String(bytes)
                    JSONObject result = new JSONObject(str)
                    if (!result.isNull("errcode")) {
                        throw new WechatMediaException("get Temporary Media fail :${result.toString()}")
                    }
                    String videoUrl = new JSONObject(str).getString("video_url")
                    return downloadVideo(videoUrl)
                } else {
                    InputStream inputStream = response.getBody()
                    String filename = response.getHeaders().getContentDisposition().getFilename()
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
                    fileOutputStream.close();

                    return file
                }
            }
        })

        return mediaFile
    }

    /**
     * 下载临时视频
     * @param url
     * @return
     */
    protected File downloadVideo(url) {
        File mediaFile = restTemplate.execute(url, HttpMethod.GET, null, new ResponseExtractor<File>() {
            @Override
            File extractData(ClientHttpResponse response) throws IOException {
                InputStream inputStream = response.getBody()
                String filename = response.getHeaders().getContentDisposition().getFilename()
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
                fileOutputStream.close();
                return file
            }
        })

        return mediaFile
    }


    /**
     * 上传图文消息内的图片
     * @param imgFilePath 图片仅支持jpg/png格式
     * @return 图片url
     */
    String uploadNewsImg(String imgFilePath) {
        fileCheck(imgFilePath, MediaType.IMAGE)
        Map<String, String> result = httpUtil.doPostRequestWithFile(
                "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=${weChatApi.getAccessToken()}",
                imgFilePath
        )
        if (result?.url) {
            return result.url
        } else {
            throw new WechatMediaException("upload news img fail :${result.toString()}")
        }
    }

    /**
     * 新增图文素材
     * @param item
     * @return
     */
    String addNews(List<NewsItem> items) {
        String newsJsons = ""
        items.each { item ->
            newsJsons += "${item.toJSON()},"
        }
        Map<String, String> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=${weChatApi.getAccessToken()}",
                """{"articles":[${newsJsons}]}"""
        )
        if (result?.media_id) {
            return result.media_id
        } else {
            throw new WechatMediaException("upload news fail:${result.toString()}")
        }
    }

    /**
     * 修改图文素材(永久图文素材)
     * @param mediaId 要修改的图文消息的id
     * @param item
     * @param index 要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @return
     */
    void updateNews(String mediaId, NewsItem item, String index) {
        Map<String, String> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=${weChatApi.getAccessToken()}",
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
     * 新增其他类型永久素材
     * @param filePath 文件路径
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @param title
     * @param introduction
     * @return {"media_id":MEDIA_ID, "url":URL}
     */
    Map<String, String> uploadNewsImg(String filePath, MediaType type, String title, String introduction) {
        fileCheck(filePath, type)
        Map<String, String> result = httpUtil.doPostRequestWithFileAndJson(
                "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=${weChatApi.getAccessToken()}",
                filePath,
                """{
                            "title":${title},
                            "introduction":"${introduction}"
                        }"""
        )
        if (result?.media_id) {
            return result
        } else {
            throw new WechatMediaException("upload news img fail :${result.toString()}")
        }
    }

    /**
     * 获取永久素材总数
     * 开发者可以根据本接口来获取"永久素材"的列表，需要时也可保存到本地。
     * 图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
     *  "voice_count":COUNT,
     *   "video_count":COUNT,
     *   "image_count":COUNT,
     *   "news_count":COUNT
     * @return
     */
    Map<String, String> getMediaCount() {
        Map<String, String> result = httpUtil.doGetRequest(
                "https://api.weixin.qq.com/cgi-bin/material/get_materialcount",
                ["access)token": weChatApi.getAccessToken()]
        )
        return result
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
     * @param mediaId
     */
    List<NewsItem> getNewsMedia(String mediaId) {
        Map<String, Object> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${weChatApi.getAccessToken()}",
                """{"media_id":"${mediaId}" }"""
        )
        if (result.news_item != null) {
            List<NewsItem> list = new ArrayList<>()
            result.news_item.each { Map<String, String> item ->
                NewsItem newsItem = new NewsItem(
                        item.title,
                        item.thumb_media_id,
                        item.show_cover_pic,
                        item.author,
                        item.digest,
                        item.content,
                        item.url,
                        item.content_source_url
                )
                list << newsItem
            }
            return list
        } else {
            throw new WechatMediaException("get news media  fail :${result.toString()}")
        }
    }

    /**
     *  获取图片永久素材
     * @param mediaId
     */
    InputStream getImageMedia(String mediaId) {
        InputStream inputStream = httpUtil.getInputStreamDoPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${weChatApi.getAccessToken()}",
                """{"media_id":"${mediaId}" }"""
        )
        return inputStream
    }


    /**
     *  获取语音永久素材
     * @param mediaId
     */
    InputStream getVoiceMedia(String mediaId) {
        InputStream inputStream = httpUtil.getInputStreamDoPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${weChatApi.getAccessToken()}",
                """{"media_id":"${mediaId}" }"""
        )
        return inputStream
    }

    /**
     *  获取视频永久素材
     * @param mediaId
     *{  "title":TITLE,
     *   "description":DESCRIPTION,
     *   "down_url":DOWN_URL,
     *}
     */
    Map<String, Object> getVideoMedia(String mediaId) {
        Map<String, Object> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${weChatApi.getAccessToken()}",
                """{"media_id":"${mediaId}" }"""
        )
        if (result.down_url != null) {
            return result
        } else {
            throw new WechatMediaException("get video media  fail :${result.toString()}")
        }
    }
    /**
     *  获取缩略图永久素材
     * @param mediaId
     */
    InputStream getThumbMedia(String mediaId) {
        InputStream inputStream = httpUtil.getInputStreamDoPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=${weChatApi.getAccessToken()}",
                """{"media_id":"${mediaId}" }"""
        )
        return inputStream
    }

    /**
     * 删除永久素材
     * @param mediaId 素材ID
     */
    void deleteMediaMaterial(String mediaId) {
        Map<String, Object> result = httpUtil.doPostRequestWithJson(
                "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=${weChatApi.getAccessToken()}",
                """{"media_id":"${mediaId}"} """
        )
        if (result.errcode != null && result.errcode != 0) {
            throw new WechatMediaException("delete media material fail :${result.toString()}")
        }
    }


    /**
     * 检查上传素材
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
