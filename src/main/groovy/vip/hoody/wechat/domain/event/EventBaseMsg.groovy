package vip.hoody.wechat.domain.event

import vip.hoody.wechat.domain.received.ReceivedBaseMsg


/**
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>
 *   <CreateTime>123456789</CreateTime>
 *   <MsgType><![CDATA[event]]></MsgType>
 *   <Event><![CDATA[subscribe]]></Event>
 * </xml>
 */
class EventBaseMsg extends ReceivedBaseMsg {
    private String Event

    /**
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    EventBaseMsg(String toUserName, String fromUserName, String createTime, String msgType, String event) {
        super(toUserName, fromUserName, createTime, msgType, "")
        Event = event
    }
}
