package vip.hoody.wechat.api.user

import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONArray
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.IUserInfoApi
import vip.hoody.wechat.api.WechatApi
import vip.hoody.wechat.domain.user.OpenIdListPage
import vip.hoody.wechat.domain.user.UserInfo
import vip.hoody.wechat.exception.user.WechatUserInfoException
import vip.hoody.wechat.utils.Lang

@Component
class UserInfoApi implements IUserInfoApi {

    @Autowired
    RestTemplate restTemplate

    @Autowired
    WechatApi wechatApi

    OpenIdListPage getUserList() throws WechatUserInfoException {
        return getUserList(null)
    }

    OpenIdListPage getUserList(String nextOpenId) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=${wechatApi.getAccessToken()}${nextOpenId != null ? "&next_openid=${nextOpenId}" : ""}"
        String result = restTemplate.getForObject(url, String.class)
        if (result.contains("errcode")) {
            throw new WechatUserInfoException(result)
        }
        OpenIdListPage page = JSON.parseObject(result, OpenIdListPage.class)
        return page
    }

    void setAliasName(String userOpenId, String aliasName) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=${wechatApi.getAccessToken()}"
        String params = """{"openid":"${userOpenId}","remark":"${aliasName}"}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(params, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        if (!result.contains("\"errcode\":0")) {
            throw new WechatUserInfoException(result)
        }
    }

    UserInfo getUserInfo(String openId, Lang lang) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=${wechatApi.getAccessToken()}&openid=${openId}&lang=${lang}"
        String result = restTemplate.getForObject(url, String.class)
        if (result.contains("errcode")) {
            throw new WechatUserInfoException(result)
        }
        UserInfo userInfo = JSON.parseObject(result, UserInfo.class)
        return userInfo
    }

    UserInfo getUserInfo(String openId) throws WechatUserInfoException {
        return getUserInfo(openId, Lang.zh_CN)
    }

    List<UserInfo> getUserInfoList(List<String> openIds) throws WechatUserInfoException {
        return getUserInfoList(openIds, Lang.zh_CN)
    }

    List<UserInfo> getUserInfoList(List<String> openIds, Lang lang) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=${wechatApi.getAccessToken()}"
        String params = """{"user_list":
        ${
            openIds.collect { String id ->
                """{"openid":"${id}","lang":"${lang}"}"""
            }
        }}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(params, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        if (result.contains("errcode")) {
            throw new WechatUserInfoException(result)
        }
        JSONArray listArray = new JSONObject(result).getJSONArray("user_info_list")
        List<UserInfo> infoList = JSON.parseArray(listArray.toString(), UserInfo.class)
        return infoList
    }

    void addBlacklist(List<String> openIds) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=${wechatApi.getAccessToken()}"
        String param = """{ "openid_list":${openIds.collect { "\"${it}\"" }}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        if (!result.contains("ok")) {
            throw new WechatUserInfoException(result)
        }
    }

    void removeBlacklist(List<String> openIds) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=${wechatApi.getAccessToken()}"
        String param = """{ "openid_list":${openIds.collect { "\"${it}\"" }}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(param, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        if (!result.contains("ok")) {
            throw new WechatUserInfoException(result)
        }
    }

    OpenIdListPage getBlacklist() throws WechatUserInfoException {
        return getBlacklist(null)
    }

    OpenIdListPage getBlacklist(String nextOpenid) throws WechatUserInfoException {
        String url = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=${wechatApi.getAccessToken()}"
        String params = """{"begin_openid":"${nextOpenid == null ? "" : nextOpenid}"}"""
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(params, headers)
        String result = restTemplate.postForObject(url, entity, String.class)
        if (result.contains("errcode")) {
            throw new WechatUserInfoException(result)
        }
        OpenIdListPage page = JSON.parseObject(result, OpenIdListPage.class)
        return page
    }
}
