package vip.hoody.wechat.domain.event

/**
 * 在微信用户和公众号产生交互的过程中，
 * 用户的某些操作会使得微信服务器通过事件推送的形式,
 * 通知到开发者在开发者中心处设置的服务器地址，
 * 从而开发者可以获取到该信息。
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[FromUser]]></FromUserName>
 *   <CreateTime>123456789</CreateTime>
 *   <MsgType><![CDATA[event]]></MsgType>
 *   <Event><![CDATA[subscribe]]></Event>
 * </xml>
 * */
class BaseEvent {
    private String toUserName
    private String fromUserName
    private String createTime
    private String msgType
    private String event

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型
     */
    BaseEvent(String toUserName, String fromUserName, String createTime, String msgType, String event) {
        this.toUserName = toUserName
        this.fromUserName = fromUserName
        this.createTime = createTime
        this.msgType = msgType
        this.event = event
    }

    String getToUserName() {
        return toUserName
    }

    void setToUserName(String toUserName) {
        this.toUserName = toUserName
    }

    String getFromUserName() {
        return fromUserName
    }

    void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName
    }

    String getCreateTime() {
        return createTime
    }

    void setCreateTime(String createTime) {
        this.createTime = createTime
    }

    String getMsgType() {
        return msgType
    }

    void setMsgType(String msgType) {
        this.msgType = msgType
    }

    String getEvent() {
        return event
    }

    void setEvent(String event) {
        this.event = event
    }

    @Override
    public String toString() {
        return "BaseEvent{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
