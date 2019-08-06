package vip.hoody.wechat.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vip.hoody.wechat.config.WechatConfig
import vip.hoody.wechat.service.WeChatService
import vip.hoody.wechat.utils.SignCheck

import javax.servlet.http.HttpServletRequest

/**
 * 处理微信验证开发者&用户消息
 */
@RestController
class WeChatController {

    WechatConfig config

    private WeChatService weChatService

    @Autowired
    WeChatController(WechatConfig config, WeChatService weChatService) {
        this.config = config
        this.weChatService = weChatService
    }

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     *
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping('#{wechatConfig.getUrl()}')
    String handleWeChat(@RequestParam("signature") String signature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce,
                        @RequestParam("echostr") String echostr) {
        boolean isTrust = SignCheck.checkSignature(config.token, signature, timestamp, nonce)
        if (isTrust) {
            return echostr
        } else {
            return null
        }
    }
    /**
     * 监听微信消息,返回符合要求的xml
     * @param request from wechat server
     * @return xml for send to user
     */
    @PostMapping(value = '#{wechatConfig.getUrl()}', produces = "application/xml;charset=UTF-8")
    String handleUserMsg(HttpServletRequest request) {
        return weChatService.processRequest(request.getInputStream())
    }

}