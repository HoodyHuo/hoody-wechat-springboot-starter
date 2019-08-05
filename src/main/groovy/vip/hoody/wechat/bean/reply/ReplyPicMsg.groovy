package vip.hoody.wechat.bean.reply

/**
 *
 *
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[image]]></MsgType>
 *   <Image>
 *     <MediaId><![CDATA[media_id]]></MediaId>
 *   </Image>
 * </xml>
 */
class ReplyPicMsg extends ReplyBaseMsg {
    private String MediaId

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，文本为image
     * @param mediaId 通过素材管理中的接口上传多媒体文件，得到的id。
     */
    ReplyPicMsg(String toUserName, String fromUserName, String createTime, String msgType, String mediaId) {
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
  <Image>
    <MediaId>${this.MediaId}</MediaId>
  </Image>
</xml>
"""
    }
}
