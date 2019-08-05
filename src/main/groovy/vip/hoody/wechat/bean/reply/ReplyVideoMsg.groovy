package vip.hoody.wechat.bean.reply

class ReplyVideoMsg extends ReplyBaseMsg {
    private String MediaId
    private String Title
    private String Description

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，视频为video
     * @param mediaId 通过素材管理中的接口上传多媒体文件，得到的id
     * @param title 视频消息的标题
     * @param description 视频消息的描述
     */
    ReplyVideoMsg(String toUserName, String fromUserName, String createTime, String msgType, String mediaId, String title, String description) {
        super(toUserName, fromUserName, createTime, msgType)
        MediaId = mediaId
        Title = title
        Description = description
    }

    @Override
    String toXml() {
        return """
<xml>
  <ToUserName>${this.toUserName}</ToUserName>
  <FromUserName>${this.fromUserName}</FromUserName>
  <CreateTime>${this.createTime}</CreateTime>
  <MsgType>${this.msgType}</MsgType>
  <Video>
    <MediaId>${this.MediaId}</MediaId>
    <Title>${this.Title}</Title>
    <Description>${this.Description}</Description>
  </Video>
</xml>
"""
    }
}