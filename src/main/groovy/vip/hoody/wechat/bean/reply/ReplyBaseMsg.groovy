package vip.hoody.wechat.bean.reply

/**
 * 基本
 * 回复消息对象
 */
class ReplyBaseMsg {
    private String ToUserName
    private String FromUserName
    private String CreateTime
    private String MsgType

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型
     */
    ReplyBaseMsg(String toUserName, String fromUserName, String createTime, String msgType) {
        ToUserName = toUserName
        FromUserName = fromUserName
        CreateTime = createTime
        MsgType = msgType
    }

    /**
     * 子类需要重写此方法
     * @return
     */
    String toXml() {
        return """
<xml>
  <ToUserName>${this.toUserName}</ToUserName>
  <FromUserName>${this.fromUserName}</FromUserName>
  <CreateTime>${this.createTime}</CreateTime>
  <MsgType>${this.msgType}</MsgType>
</xml>
"""
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
}
