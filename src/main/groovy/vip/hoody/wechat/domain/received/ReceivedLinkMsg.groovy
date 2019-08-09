package vip.hoody.wechat.domain.received

/**
 * 链接
 * 微信用户消息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1351776360</CreateTime>
 *   <MsgType><![CDATA[link]]></MsgType>
 *   <Title><![CDATA[公众平台官网链接]]></Title>
 *   <Description><![CDATA[公众平台官网链接]]></Description>
 *   <Url><![CDATA[url]]></Url>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedLinkMsg extends ReceivedBaseMsg {
    private String Title
    private String Description
    private String Url

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，链接为link
     * @param msgId 消息id，64位整型
     * @param title 消息标题
     * @param description 消息描述
     * @param url 消息链接
     */
    ReceivedLinkMsg(String toUserName, String fromUserName, String createTime, String msgType, String msgId, String title, String description, String url) {
        super(toUserName, fromUserName, createTime, msgType, msgId)
        Title = title
        Description = description
        Url = url
    }
}
