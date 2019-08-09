package vip.hoody.wechat.handler


import vip.hoody.wechat.domain.event.EventBaseMsg
import vip.hoody.wechat.domain.received.*
import vip.hoody.wechat.domain.reply.ReplyBaseMsg
import vip.hoody.wechat.domain.reply.ReplyTextMsg

/**
 * 当 用户没有注入 IWechatMsgHandler 的时候,注入这个
 * @(IWechatMsgHandler.class) 参考
 */

class DefaultWechatMsgHandler implements IWechatMsgHandler {

    @Override
    ReplyBaseMsg handle(ReceivedTextMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:文字信息(${msg.content})")
    }

    @Override
    ReplyBaseMsg handle(ReceivedImageMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:图片信息")
    }

    @Override
    ReplyBaseMsg handle(ReceivedLinkMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:图链接息")
    }

    @Override
    ReplyBaseMsg handle(ReceivedLocationMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:定位信息")
    }

    @Override
    ReplyBaseMsg handle(ReceivedShortVideoMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:小视频信息")
    }

    @Override
    ReplyBaseMsg handle(ReceivedVideoMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:视频信息")
    }

    @Override
    ReplyBaseMsg handle(ReceivedVoiceMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:音频信息")
    }

    @Override
    ReplyBaseMsg handle(EventBaseMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:图片信息")
    }
}
