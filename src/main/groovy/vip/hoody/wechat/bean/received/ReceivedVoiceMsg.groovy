package vip.hoody.wechat.bean.received

/**
 * 语音
 * 微信用户消息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1357290913</CreateTime>
 *   <MsgType><![CDATA[voice]]></MsgType>
 *   <MediaId><![CDATA[media_id]]></MediaId>
 *   <Format><![CDATA[Format]]></Format>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedVoiceMsg extends ReceivedBaseMsg {
    private String MediaId
    private String Format

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，语音为voice
     * @param msgId 消息id，64位整型
     * @param mediaId 语音消息媒体id，可以调用获取临时素材接口拉取数据。
     * @param format 语音格式，如amr，speex等
     */
    ReceivedVoiceMsg(String toUserName, String fromUserName, String createTime, String msgType, String msgId, String mediaId, String format) {
        super(toUserName, fromUserName, createTime, msgType, msgId)
        this.MediaId = mediaId
        this.Format = format
    }
}
