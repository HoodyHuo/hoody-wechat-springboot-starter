package vip.hoody.wechat.domain.reply

/**
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[voice]]></MsgType>
 *   <Voice>
 *     <MediaId><![CDATA[media_id]]></MediaId>
 *   </Voice>
 * </xml>
 */
class ReplyVoiceMsg extends ReplyBaseMsg {
    private String MediaId

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，语音为voice
     * @param mediaId 通过素材管理中的接口上传多媒体文件，得到的id。
     */
    ReplyVoiceMsg(String toUserName, String fromUserName, String createTime, String msgType, String mediaId) {
        super(toUserName, fromUserName, createTime, msgType)
        MediaId = mediaId
    }

    @Override
    String toXml() {
        return """
<xml>
  <ToUserName>${this.toUserName}</ToUserName>
  <FromUserName>${this.fromUserName}</FromUserName>
  <CreateTime>${this.createTime}</CreateTime>
  <MsgType>${this.msgType}</MsgType>
  <Voice>
    <MediaId>${this.MediaId}</MediaId>
  </Voice>
</xml>
"""
    }
}
