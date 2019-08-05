package vip.hoody.wechat.bean.received

/**
 * 小视频
 * 微信用户消息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1357290913</CreateTime>
 *   <MsgType><![CDATA[shortvideo]]></MsgType>
 *   <MediaId><![CDATA[media_id]]></MediaId>
 *   <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedShortVideoMsg extends ReceivedVideoMsg {

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 小视频为shortvideo
     * @param msgId 消息id，64位整型
     * @param mediaId 视频消息媒体id，可以调用获取临时素材接口拉取数据。
     * @param thumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    ReceivedShortVideoMsg(String toUserName, String fromUserName, String createTime, String msgType, String msgId, String mediaId, String thumbMediaId) {
        super(toUserName, fromUserName, createTime, msgType, msgId, mediaId, thumbMediaId)
    }
}
