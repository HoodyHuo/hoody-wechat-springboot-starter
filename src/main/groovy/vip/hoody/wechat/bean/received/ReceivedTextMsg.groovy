package vip.hoody.wechat.bean.received

/**
 * 文本
 * 微信用户消息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1348831860</CreateTime>
 *   <MsgType><![CDATA[text]]></MsgType>
 *   <Content><![CDATA[this is a test]]></Content>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedTextMsg extends ReceivedBaseMsg {
    private String Content

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，图片为text
     * @param msgId 消息id，64位整型
     * @param Content 文本消息内容
     */
    ReceivedTextMsg(String toUserName,
                    String fromUserName,
                    String createTime,
                    String msgType,
                    String msgId,
                    String Content) {
        super(toUserName, fromUserName, createTime, msgType, msgId)
        this.Content = Content
    }

    String getContent() {
        return Content
    }

    void setContent(String content) {
        Content = content
    }
}
