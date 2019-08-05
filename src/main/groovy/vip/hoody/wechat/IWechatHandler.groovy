package vip.hoody.wechat

import vip.hoody.wechat.bean.event.EventBaseMsg
import vip.hoody.wechat.bean.received.*
import vip.hoody.wechat.bean.reply.ReplyBaseMsg

/**
 * 消息处理接口
 * 实现接口,返回消息对象
 */
interface IWechatHandler {

    ReplyBaseMsg handle(ReceivedTextMsg msg)

    ReplyBaseMsg handle(ReceivedImageMsg msg)

    ReplyBaseMsg handle(ReceivedLinkMsg msg)

    ReplyBaseMsg handle(ReceivedLocationMsg msg)

    ReplyBaseMsg handle(ReceivedShortVideoMsg msg)

    ReplyBaseMsg handle(ReceivedVideoMsg msg)

    ReplyBaseMsg handle(ReceivedVoiceMsg msg)

    ReplyBaseMsg handle(EventBaseMsg msg)

}
