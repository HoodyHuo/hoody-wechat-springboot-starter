package vip.hoody.wechat.api

import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import vip.hoody.wechat.config.WechatConfig
import vip.hoody.wechat.utils.HttpUtil

/**
 * Wechat API util
 */
@Component
class WeChatApi {
    private static final Logger log = LoggerFactory.getLogger(this.class)
    private WechatConfig wechatConfig
    private HttpUtil httpUtil
    private String Token
    private JsonSlurper jsonSlurper

    @Autowired
    WeChatApi(WechatConfig wechatConfig, HttpUtil httpUtil) {
        this.wechatConfig = wechatConfig
        this.httpUtil = httpUtil
        this.jsonSlurper = new JsonSlurper()
    }

    /**
     *  request access_token from wechat API Server
     * @return token
     */
    @Scheduled(fixedDelayString = '#{wechatConfig.getTokenRate()}')
    String refreshToken() {
        // request token URL for Wechat API
        String url = "https://api.weixin.qq.com/cgi-bin/token"
        //params
        Map var = [grant_type: "client_credential", appid: wechatConfig.appId, secret: wechatConfig.appSecret]
        // response
        ResponseEntity<String> response = httpUtil.doGetRequest(url, var)
        //parse json string to Map
        def data = jsonSlurper.parseText(response.body)
        if (data?.access_token) {
            this.Token = data.access_token
            log.info("Request Wechat Access-token success :${data.toString()}")
            return this.Token
        } else {
            log.error("Request Wechat Access-token failed : app-id:${wechatConfig.appId} app-secret:${wechatConfig.appSecret} \n response: ${data} ")
            return null
        }
    }

    /**
     * Wechat Access_token
     * @return
     */
    String getAccessToken() {
        return Token
    }
}
