package vip.hoody.wechat.domain.event.menu

import vip.hoody.wechat.domain.event.BaseEvent

/**
 * 点击菜单跳转链接时的事件推送
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>
 *   <CreateTime>123456789</CreateTime>
 *   <MsgType><![CDATA[event]]></MsgType>
 *   <Event><![CDATA[VIEW]]></Event>
 *   <EventKey><![CDATA[www.qq.com]]></EventKey>
 * </xml>
 */
class ViewEvent extends BaseEvent {
    private String eventKey

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型 VIEW
     * @param eventKey 事件KEY值，设置的跳转URL
     */
    ViewEvent(String toUserName, String fromUserName, String createTime, String msgType,
              String event, String eventKey) {
        super(toUserName, fromUserName, createTime, msgType, event)
        this.eventKey = eventKey
    }

    String getEventKey() {
        return eventKey
    }

    void setEventKey(String eventKey) {
        this.eventKey = eventKey
    }

    @Override
    public String toString() {
        return "ViewEvent{" +
                "eventKey='" + eventKey + '\'' +
                "} " + super.toString();
    }
}
