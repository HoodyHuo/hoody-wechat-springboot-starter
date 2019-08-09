package vip.hoody.wechat.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vip.hoody.wechat.domain.event.BaseEvent
import vip.hoody.wechat.handler.IWechatEventHandler
import vip.hoody.wechat.handler.IWechatMsgHandler
import vip.hoody.wechat.WeChatFactory
import vip.hoody.wechat.domain.received.ReceivedBaseMsg
import vip.hoody.wechat.domain.reply.ReplyBaseMsg

@Service
class WeChatService {
    private static final Logger log = LoggerFactory.getLogger(this.class)

    /**
     * 消息处理接口
     */
    @Autowired
    private IWechatMsgHandler msgHandler
    /**
     * 时间处理接口
     */
    @Autowired
    private IWechatEventHandler eventHandler

    /**
     * 处理收到的微信信息
     * @param xml 文本流
     * @return 响应xml文本,符合wechat接口
     */
    String processRequest(InputStream xml) {
        def msg = WeChatFactory.getWechatReceivedMsg(xml)
        try {
            ReplyBaseMsg replyBaseMsg = this.handle(msg)
            return replyBaseMsg.toXml()
        } catch (Exception e) {
            log.warn("处理用户微信消息出现异常", e)
            return WeChatFactory.createTextReply(userMsg, "处理用户微信消息出现异常").toXml()
        }
    }

    ReplyBaseMsg handle(BaseEvent event) {
        return eventHandler.handle(event)
    }

    ReplyBaseMsg handle(ReceivedBaseMsg msg) {
        return msgHandler.handle(msg)
    }

}