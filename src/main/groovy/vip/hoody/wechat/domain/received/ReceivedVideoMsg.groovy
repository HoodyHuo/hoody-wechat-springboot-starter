package vip.hoody.wechat.domain.received

/**
 * 视频
 * 微信用户消息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1357290913</CreateTime>
 *   <MsgType><![CDATA[video]]></MsgType>
 *   <MediaId><![CDATA[media_id]]></MediaId>
 *   <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedVideoMsg extends ReceivedBaseMsg {
    private String MediaId
    private String ThumbMediaId

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 视频为video
     * @param msgId 消息id，64位整型
     * @param mediaId 视频消息媒体id，可以调用获取临时素材接口拉取数据。
     * @param thumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    ReceivedVideoMsg(String toUserName, String fromUserName, String createTime, String msgType, String msgId, String mediaId, String thumbMediaId) {
        super(toUserName, fromUserName, createTime, msgType, msgId)
        this.MediaId = mediaId
        this.ThumbMediaId = thumbMediaId
    }
}
