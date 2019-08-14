package vip.hoody.wechat.api.media

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONArray
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.IMediaApi
import vip.hoody.wechat.api.WeChatApi
import vip.hoody.wechat.domain.media.MaterialType
import vip.hoody.wechat.domain.media.MediaItem
import vip.hoody.wechat.domain.media.MediaNewsPage
import vip.hoody.wechat.domain.media.MediaOtherPage
import vip.hoody.wechat.domain.media.NewsItem
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.domain.media.NewsMedia
import vip.hoody.wechat.exception.WechatMediaException

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
class MediaAPI implements IMediaApi {

    @Autowired
    WeChatApi weChatApi

    @Autowired
    private TemporaryMediaApi temporaryMediaApi

    @Autowired
    private NewsMediaApi newsMediaApi

    @Autowired
    private NormalMediaApi normalMediaApi

    Map<String, String> uploadTemporaryMedia(String filePath, MediaType type) throws WechatMediaException {
        return temporaryMediaApi.uploadTemporaryMedia(filePath, type)
    }

    File getTemporaryMedia(String mediaId) throws WechatMediaException {
        return temporaryMediaApi.getTemporaryMedia(mediaId)
    }

    /**
     * upload image for news media
     * 上传图文素材内的图片
     *
     * @param imgFilePath imgFilePath (only jpg/png)
     * @return url for image
     * @throws WechatMediaException api error
     */
    String uploadNewsImg(String imgFilePath) {
        return newsMediaApi.uploadNewsImg(imgFilePath)
    }

    /**
     * add news media
     * 添加图文素材
     *
     * @param items news list
     * @return mediaId for the news media
     * @throws WechatMediaException api error
     */
    String uploadNewsMedia(List<NewsItem> items) throws WechatMediaException {
        return newsMediaApi.uploadNewsMedia(items)
    }

    /**
     * update news media
     * 修改/更新图文素材
     *
     * @param mediaId news media id
     * @param item news value
     * @param index of news media list (start from 0)
     *                要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @throws WechatMediaException api error
     */
    void updateMedia(String mediaId, NewsItem item, String index) throws WechatMediaException {
        newsMediaApi.updateMedia(mediaId, item, index)
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
    String uploadVideoMedia(String filePath, String title, String introduction) {
        return normalMediaApi.uploadVideoMedia(filePath, title, introduction)
    }

    /**
     * upload media
     *
     * @param filePath absolute file path
     * @param type MediaType image/voice/thumb
     * @return media id
     * @throws WechatMediaException api error
     */
    String uploadMedia(String filePath, MediaType type) {
        return normalMediaApi.uploadMedia(filePath, type)
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
        return normalMediaApi.getMediaCount()
    }

    /**
     * 获取永久图文素材列表
     * 在新增了永久素材后，开发者可以分类型获取永久素材的列表。
     * 请注意：临时素材无法通过本接口获取
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count 返回素材的数量 ，取值在1到20之间
     */
    MediaNewsPage getMediaNewsList(int offset, int count) {
        //TODO 请求次数受限
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=${weChatApi.getAccessToken()}"
        String param = """{"type":"news","offset":${offset},"count":${count}}"""

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = new RestTemplate().postForObject(url, entity, String.class)
        JSONObject object = new JSONObject(result)
        if (!object.isNull("errcode")) {
            throw new WechatMediaException("get media list fail:${result}")
        }

        MediaNewsPage page = new MediaNewsPage(object.getInt("total_count"), object.getInt("item_count"))
        JSONArray mediaItems = object.getJSONArray("item")
        for (int mediaIndex = 0; mediaIndex < mediaItems.length(); mediaIndex++) {
            JSONObject media = mediaItems.getJSONObject(mediaIndex)
            NewsMedia newsMedia = new NewsMedia(media.getString("media_id"), media.getString("update_time"))
            JSONArray newsItems = media.getJSONObject("content").getJSONArray("news_item")
            for (int newsIndex = 0; newsIndex < newsItems.length(); newsIndex++) {
                JSONObject news = newsItems.getJSONObject(newsIndex)
                NewsItem newsItem = new NewsItem(
                        news.getString("title"),
                        news.getString("thumb_media_id"),
                        news.getString("show_cover_pic"),
                        news.getString("author"),
                        news.getString("digest"),
                        news.getString("content"),
                        news.getString("url"),
                        news.getString("content_source_url")
                )
                newsMedia.getContent().add(newsItem)
            }
            page.getNewsItemList().add(newsMedia)
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
    MediaOtherPage getMediaOtherList(MediaType type, int offset, int count) {
        return normalMediaApi.getMediaOtherList(type, offset, count)
    }


    /**
     *  获取图文永久素材
     *  其他类型 使用 getMedia(mediaId)
     * @param mediaId
     */
    List<NewsItem> getNewsMedia(String mediaId) {
        return newsMediaApi.getNewsMedia(mediaId)
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
        return normalMediaApi.getMedia(mediaId)
    }

    /**
     * 删除永久素材
     * @param mediaId 素材ID
     */
    void deleteMedia(String mediaId) {
        normalMediaApi.deleteMedia(mediaId)
    }
}
