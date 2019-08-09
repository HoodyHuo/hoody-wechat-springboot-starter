package vip.hoody.wechat.domain.event

/**扫描带参数二维码事件
 * 用户未关注时，进行关注后的事件推送
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>
 *   <CreateTime>123456789</CreateTime>
 *   <MsgType><![CDATA[event]]></MsgType>
 *   <Event><![CDATA[subscribe]]></Event>
 *   <EventKey><![CDATA[qrscene_123123]]></EventKey>
 *   <Ticket><![CDATA[TICKET]]></Ticket>
 * </xml>
 */
class ScanQRUnSubEvent extends BaseEvent {
    private String eventKey
    private String ticket

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型 subscribe
     * @param eventKey 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     * @param ticket 二维码的ticket，可用来换取二维码图片
     */
    ScanQRUnSubEvent(String toUserName, String fromUserName, String createTime, String msgType,
                     String event, String eventKey, String ticket) {
        super(toUserName, fromUserName, createTime, msgType, event)
        this.eventKey = eventKey
        this.ticket = ticket
    }


    String getEventKey() {
        return eventKey
    }

    void setEventKey(String eventKey) {
        this.eventKey = eventKey
    }

    String getTicket() {
        return ticket
    }

    void setTicket(String ticket) {
        this.ticket = ticket
    }

    @Override
    public String toString() {
        return "ScanQRUnSubEvent{" +
                "eventKey='" + eventKey + '\'' +
                ", ticket='" + ticket + '\'' +
                "} " + super.toString();
    }
}
