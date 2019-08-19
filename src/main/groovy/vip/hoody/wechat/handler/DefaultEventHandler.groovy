package vip.hoody.wechat.handler

import vip.hoody.wechat.domain.event.LocationEvent
import vip.hoody.wechat.domain.event.menu.ClickEvent
import vip.hoody.wechat.domain.event.menu.LocationSelectEvent
import vip.hoody.wechat.domain.event.menu.PicPhotoOrAlbumEvent
import vip.hoody.wechat.domain.event.menu.PicSysPhotoEvent
import vip.hoody.wechat.domain.event.menu.PicWeixinEvent
import vip.hoody.wechat.domain.event.menu.ViewEvent
import vip.hoody.wechat.domain.event.menu.ScanCodePushEvent
import vip.hoody.wechat.domain.event.menu.ScanCodeWaitEvent
import vip.hoody.wechat.domain.event.ScanQREvent
import vip.hoody.wechat.domain.event.ScanQRUnSubEvent
import vip.hoody.wechat.domain.event.SubscribeEvent
import vip.hoody.wechat.domain.menu.button.ScanCodeWaitMsgButton
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

    ReplyBaseMsg handle(ClickEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(ViewEvent event) {
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

    ReplyBaseMsg handle(ScanCodePushEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(ScanCodeWaitEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(LocationSelectEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(PicSysPhotoEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(PicPhotoOrAlbumEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }

    ReplyBaseMsg handle(PicWeixinEvent event) {
        return new ReplyTextMsg(
                event.fromUserName,
                event.toUserName,
                new Date().getTime().toString(),
                "收到事件:${event.toString()}")
    }
}
