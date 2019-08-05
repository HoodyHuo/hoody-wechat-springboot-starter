package vip.hoody.wechat.bean.received

/**
 * 图片
 * 微信用户消息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1348831860</CreateTime>
 *   <MsgType><![CDATA[image]]></MsgType>
 *   <PicUrl><![CDATA[this is a url]]></PicUrl>
 *   <MediaId><![CDATA[media_id]]></MediaId>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedImageMsg extends ReceivedBaseMsg {
    private String PicUrl
    private String MediaId

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，图片为image
     * @param msgId 消息id，64位整型
     * @param picUrl 图片链接（由系统生成）
     * @param MediaId 图片消息媒体id，可以调用获取临时素材接口拉取数据。
     */
    ReceivedImageMsg(String toUserName,
                     String fromUserName,
                     String createTime,
                     String msgType,
                     String msgId,
                     String picUrl,
                     String MediaId) {
        super(toUserName, fromUserName, createTime, msgType, msgId)
        this.PicUrl = picUrl
        this.MediaId = MediaId
    }
}
