package vip.hoody.wechat.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vip.hoody.wechat.handler.IWechatMsgHandler
import vip.hoody.wechat.WeChatFactory
import vip.hoody.wechat.bean.received.ReceivedBaseMsg
import vip.hoody.wechat.bean.reply.ReplyBaseMsg

@Service
class WeChatService {
    private static final Logger log = LoggerFactory.getLogger(this.class)

    /**
     * 处理器接口,使用项目实现接口
     */
    @Autowired
    private IWechatMsgHandler wechatHandler


    /**
     * 处理收到的微信信息
     * @param xml 文本流
     * @return 响应xml文本,符合wechat接口
     */
    String processRequest(InputStream xml) {
        ReceivedBaseMsg userMsg = WeChatFactory.getWechatReceivedMsg(xml)
        try {
            ReplyBaseMsg replyBaseMsg = wechatHandler.handle(userMsg)
            return replyBaseMsg.toXml()
        } catch (Exception e) {
            log.warn("处理用户微信消息出现异常", e)
            return WeChatFactory.createTextReply(userMsg, "处理用户微信消息出现异常").toXml()
        }
    }
}