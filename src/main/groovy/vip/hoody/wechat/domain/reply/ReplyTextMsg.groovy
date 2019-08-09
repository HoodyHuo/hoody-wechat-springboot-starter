package vip.hoody.wechat.domain.reply

/**
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[text]]></MsgType>
 *   <Content><![CDATA[你好]]></Content>
 * </xml>
 */
class ReplyTextMsg extends ReplyBaseMsg {
    private String Content

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，文本为text
     * @param content 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     */
    ReplyTextMsg(String toUserName, String fromUserName, String createTime, String content) {
        super(toUserName, fromUserName, createTime, "text")
        Content = content
    }

    @Override
    String toXml() {
        return """
<xml>
  <ToUserName>${this.toUserName}</ToUserName>
  <FromUserName>${this.fromUserName}</FromUserName>
  <CreateTime>${this.createTime}</CreateTime>
  <MsgType>${this.msgType}</MsgType>
  <Content>${this.Content}</Content>
</xml>
"""
    }
}
