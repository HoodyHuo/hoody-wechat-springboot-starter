package vip.hoody.wechat.handler;

import vip.hoody.wechat.domain.event.*;
import vip.hoody.wechat.domain.reply.ReplyBaseMsg;

/**
 * 微信事件处理器接口
 */
public interface IWechatEventHandler {

    ReplyBaseMsg handle(LocationEvent event);

    ReplyBaseMsg handle(MenuClickEvent event);

    ReplyBaseMsg handle(MenuViewEvent event);

    ReplyBaseMsg handle(ScanQREvent event);

    ReplyBaseMsg handle(ScanQRUnSubEvent event);

    ReplyBaseMsg handle(SubscribeEvent event);

}
