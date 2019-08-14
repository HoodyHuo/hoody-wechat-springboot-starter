package vip.hoody.wechat.api.media

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONArray
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.WeChatApi
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.domain.media.NewsItem
import vip.hoody.wechat.exception.WechatMediaException

@Component
class NewsMediaApi {
    @Autowired
    private RestTemplate restTemplate

    @Autowired
    private WeChatApi weChatApi

    private String getAccessToken() {
        return weChatApi.getAccessToken()
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
        //检查文件是否符合微信接口要求
        FileCheck.check(imgFilePath, MediaType.IMAGE)
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
     * add news media
     * 添加图文素材
     *
     * @param items news list
     * @return mediaId for the news media
     * @throws WechatMediaException api error
     */
    String uploadNewsMedia(List<NewsItem> items) {
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
     * update news media
     * 修改/更新图文素材
     *
     * @param mediaId news media id
     * @param item news value
     * @param index of news media list (start from 0)
     *                要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @throws WechatMediaException api error
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
     * get news media 获取图文永久素材<br>
     * ps:<br>
     * get others media <br>
     * use: getMedia(String mediaId)
     *
     * @param mediaId wechat media_id
     * @return file in temp
     * @throws WechatMediaException api error
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

}
