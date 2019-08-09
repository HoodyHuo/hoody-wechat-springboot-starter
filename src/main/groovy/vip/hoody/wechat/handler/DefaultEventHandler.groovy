package vip.hoody.wechat.handler

import vip.hoody.wechat.domain.event.LocationEvent
import vip.hoody.wechat.domain.event.MenuClickEvent
import vip.hoody.wechat.domain.event.MenuViewEvent
import vip.hoody.wechat.domain.event.ScanQREvent
import vip.hoody.wechat.domain.event.ScanQRUnSubEvent
import vip.hoody.wechat.domain.event.SubscribeEvent
import vip.hoody.wechat.domain.reply.ReplyBaseMsg
import vip.hoody.wechat.domain.reply.ReplyTextMsg

/**
 * 默认事件处理器
 */
class DefaultEventHandler implements IWechatEventHandler {

    ReplyBaseMsg handle(LocationEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(MenuClickEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(MenuViewEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(ScanQREvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(ScanQRUnSubEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(SubscribeEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }
}
