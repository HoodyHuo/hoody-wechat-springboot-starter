package vip.hoody.wechat.api

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import vip.hoody.wechat.config.WechatConfig
import vip.hoody.wechat.utils.HttpUtil

/**
 * Wechat API util
 */
@Component
class WechatApi {
    private static final Logger log = LoggerFactory.getLogger(this.class)
    private WechatConfig wechatConfig
    private HttpUtil httpUtil
    private String Token

    @Autowired
    WechatApi(WechatConfig wechatConfig, HttpUtil httpUtil) {
        this.wechatConfig = wechatConfig
        this.httpUtil = httpUtil
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
        Map<String, Object> response = httpUtil.doGetRequest(url, var)
        //parse json string to Map
        if (response?.access_token) {
            this.Token = response.access_token
            log.info("Request Wechat Access-token success :${response.toString()}")
            return this.Token
        } else {
            log.error("Request Wechat Access-token failed : app-id:${wechatConfig.appId} app-secret:${wechatConfig.appSecret} \n response: ${response} ")
            return null
        }
    }

    /**
     * Wechat Access_token
     * @return
     */
    String getAccessToken() {
        if (Token == null) {
            refreshToken()
        }
        return Token
    }
}
