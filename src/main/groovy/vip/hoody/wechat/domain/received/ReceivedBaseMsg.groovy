package vip.hoody.wechat.domain.received

/**
 * 基本
 * 微信用户消息对象
 */
class ReceivedBaseMsg {
    private String ToUserName
    private String FromUserName
    private String CreateTime
    private String MsgType
    private String MsgId

    ReceivedBaseMsg(String toUserName,
                    String fromUserName,
                    String createTime,
                    String msgType,
                    String msgId) {
        this.ToUserName = toUserName
        this.FromUserName = fromUserName
        this.CreateTime = createTime
        this.MsgType = msgType
        this.MsgId = msgId
    }

    String getToUserName() {
        return ToUserName
    }

    void setToUserName(String toUserName) {
        ToUserName = toUserName
    }

    String getFromUserName() {
        return FromUserName
    }

    void setFromUserName(String fromUserName) {
        FromUserName = fromUserName
    }

    String getCreateTime() {
        return CreateTime
    }

    void setCreateTime(String createTime) {
        CreateTime = createTime
    }

    String getMsgType() {
        return MsgType
    }

    void setMsgType(String msgType) {
        MsgType = msgType
    }

    String getMsgId() {
        return MsgId
    }

    void setMsgId(String msgId) {
        MsgId = msgId
    }
}
