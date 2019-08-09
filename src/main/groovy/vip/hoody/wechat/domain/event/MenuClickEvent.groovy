package vip.hoody.wechat.domain.event

/**
 * 自定义菜单点击事件
 * 用户点击自定义菜单后，
 * 微信会把点击事件推送给开发者，
 * 请注意，点击菜单弹出子菜单，不会产生上报。
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>
 *   <CreateTime>123456789</CreateTime>
 *   <MsgType><![CDATA[event]]></MsgType>
 *   <Event><![CDATA[CLICK]]></Event>
 *   <EventKey><![CDATA[EVENTKEY]]></EventKey>
 * </xml>
 */
class MenuClickEvent extends BaseEvent {
    private String eventKey

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型 CLICK
     * @param eventKey 事件KEY值，与自定义菜单接口中KEY值对应
     */
    MenuClickEvent(String toUserName, String fromUserName, String createTime, String msgType,
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
        return "MenuClickEvent{" +
                "eventKey='" + eventKey + '\'' +
                "} " + super.toString();
    }
}
