package vip.hoody.wechat.handler;

import vip.hoody.wechat.domain.received.*;
import vip.hoody.wechat.domain.reply.ReplyBaseMsg;

/**
 * 消息处理接口
 * 实现接口,返回消息对象
 */
public interface IWechatMsgHandler {

    ReplyBaseMsg handle(ReceivedTextMsg msg);

    ReplyBaseMsg handle(ReceivedImageMsg msg);

    ReplyBaseMsg handle(ReceivedLinkMsg msg);

    ReplyBaseMsg handle(ReceivedLocationMsg msg);

    ReplyBaseMsg handle(ReceivedShortVideoMsg msg);

    ReplyBaseMsg handle(ReceivedVideoMsg msg);

    ReplyBaseMsg handle(ReceivedVoiceMsg msg);

}
