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

@Component
class WeChatApi {
    private static final Logger log = LoggerFactory.getLogger(this.class)
    private WechatConfig wechatConfig
    private HttpUtil httpUtil;
    private String Token
    private JsonSlurper jsonSlurper

    @Autowired
    WeChatApi(WechatConfig wechatConfig, HttpUtil httpUtil) {
        this.wechatConfig = wechatConfig
        this.httpUtil = httpUtil
        this.jsonSlurper = new JsonSlurper()
    }

    /**
     *  定时刷新access_token
     * @return token
     */
    @Scheduled(fixedDelayString = '${hoody.wechat.token-rate:7200}')
    String refreshToken() {
        // 创建URL
        String url = "https://api.weixin.qq.com/cgi-bin/token"
        Map var = [grant_type: "client_credential", appid: wechatConfig.APP_ID, secret: wechatConfig.APP_SECRET]
        ResponseEntity<String> response = httpUtil.doGetRequest(url, var)

        def data = jsonSlurper.parseText(response.body)
        log.info(data.toString())
        if (data?.access_token) {
            this.Token = data.access_token
            return this.Token
        } else {
            log.error("refreshToken未能获取Access_token:${data} \n id:${wechatConfig.APP_ID} secret:${wechatConfig.APP_SECRET}")
            return null
        }
    }

}
