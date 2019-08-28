package vip.hoody.wechat.api.user

import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.IUserTagApi
import vip.hoody.wechat.api.WechatApi
import vip.hoody.wechat.domain.user.OpenIdListPage
import vip.hoody.wechat.domain.user.Tag
import vip.hoody.wechat.exception.user.WechatUserTagException

/**
 * IUserTagApi implements
 */
@Component
class UserTagApi implements IUserTagApi {

    @Autowired
    RestTemplate restTemplate

    @Autowired
    WechatApi wechatApi

    Tag tagCreate(String tagName) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=${wechatApi.getAccessToken()}"
        String param = """{"tag":{"name":"${tagName}"}}"""
        String result = doPostWithJson(param, url)
        if (result.contains("errcode")) {
            throw new WechatUserTagException(result)
        }
        String tagString = new JSONObject(result).getJSONObject("tag").toString()
        Tag tag = JSON.parseObject(tagString, Tag.class)
        return tag
    }

    void tagUpdate(String tagId, String tagName) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=${wechatApi.getAccessToken()}"
        String param = """{"tag":{"id":${tagId},"name":"${tagName}"}}"""
        String result = doPostWithJson(param, url)
        if (!result.contains("ok")) {
            throw new WechatUserTagException(result)
        }
    }

    List<Tag> tagList() throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=${wechatApi.getAccessToken()}"
        String result = restTemplate.getForObject(url, String.class)
        if (result.contains("errcode")) {
            throw new WechatUserTagException(result)
        }
        String tagsStr = new JSONObject(result).getJSONArray("tags").toString()
        List<Tag> tagList = JSON.parseArray(tagsStr, Tag.class)
        return tagList
    }

    void tagDelete(String tagId) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=${wechatApi.getAccessToken()}"
        String param = """{"tag":{"id" : ${tagId}}"""
        String result = doPostWithJson(param, url)
        if (!result.contains("ok")) {
            throw new WechatUserTagException(result)
        }
    }

    private String doPostWithJson(String param, String url) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        return result
    }

    OpenIdListPage getUserListByTag(String tagId, String nextOpenId) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=${wechatApi.getAccessToken()}"
        String param = """{"tagid" : ${tagId}${nextOpenId != null ? ",\"next_openid\":\"${nextOpenId}\"" : ""}}"""
        String result = doPostWithJson(param, url)
        if (result.contains("errcode")) {
            throw new WechatUserTagException(result)
        }
        OpenIdListPage page = JSON.parseObject(result, OpenIdListPage.class)
        return page
    }

    OpenIdListPage getUserListByTag(String tagId) throws WechatUserTagException {
        return getUserListByTag(tagId, null)
    }

    void tagUsers(String tagId, List<String> userOpenIds) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=${wechatApi.getAccessToken()}"
        String param = """{   
                            "openid_list" : 
                            ${userOpenIds.collect { "\"${it}\"" }}
                            ,   
                            "tagid" : ${tagId}
                         }"""
        String result = doPostWithJson(param, url)
        if (!result.contains("ok")) {
            throw new WechatUserTagException(result)
        }
    }

    void tagUsers(String tagId, String userOpenId) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=${wechatApi.getAccessToken()}"
        String param = """{   
                            "openid_list" : ["${userOpenId}"],   
                            "tagid" : ${tagId}
                         }"""
        String result = doPostWithJson(param, url)
        if (!result.contains("ok")) {
            throw new WechatUserTagException(result)
        }
    }


    void unTagUsers(String tagId, List<String> userOpenIds) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=${wechatApi.getAccessToken()}"
        String param = """{   
                            "openid_list" : 
                            ${userOpenIds.collect { "\"${it}\"" }}
                            ,   
                            "tagid" : ${tagId}
                         }"""
        String result = doPostWithJson(param, url)
        if (!result.contains("ok")) {
            throw new WechatUserTagException(result)
        }
    }

    void unTagUsers(String tagId, String userOpenId) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=${wechatApi.getAccessToken()}"
        String param = """{   
                            "openid_list" : ["${userOpenId}"],   
                            "tagid" : ${tagId}
                         }"""
        String result = doPostWithJson(param, url)
        if (!result.contains("ok")) {
            throw new WechatUserTagException(result)
        }
    }

    List<String> getTagsByUser(String userOpenId) throws WechatUserTagException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=${wechatApi.getAccessToken()}"
        String param = """{"openid" : "${userOpenId}"}"""
        String result = doPostWithJson(param, url)
        if (result.contains("errcode")) {
            throw new WechatUserTagException(result)
        }
        String listStr = new JSONObject(result).getJSONArray("tagid_list").toString()
        List<String> tagIds = JSON.parseArray(listStr, String.class)
        return tagIds
    }
}
