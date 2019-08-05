package vip.hoody.wechat.bean.reply

class ReplyMusicMsg extends ReplyBaseMsg {
    private String Title
    private String Description
    private String MusicURL
    private String HQMusicUrl
    private String ThumbMediaId

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，音乐为music
     * @param title 音乐标题
     * @param description 音乐描述
     * @param musicURL 音乐链接
     * @param HQMusicUrl 高质量音乐链接，WIFI环境优先使用该链接播放音乐
     * @param thumbMediaId 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
     */
    ReplyMusicMsg(String toUserName, String fromUserName, String createTime, String msgType, String title, String description, String musicURL, String HQMusicUrl, String thumbMediaId) {
        super(toUserName, fromUserName, createTime, msgType)
        Title = title
        Description = description
        MusicURL = musicURL
        this.HQMusicUrl = HQMusicUrl
        ThumbMediaId = thumbMediaId
    }

    @Override
    String toXml() {
        return """
<xml>
  <ToUserName>${this.toUserName}</ToUserName>
  <FromUserName>${this.fromUserName}</FromUserName>
  <CreateTime>${this.createTime}</CreateTime>
  <MsgType>${this.msgType}</MsgType>
  <Music>
    <Title>${this.Title}</Title>
    <Description>${this.Description}</Description>
    <MusicUrl>${this.MusicURL}</MusicUrl>
    <HQMusicUrl>${this.HQMusicUrl}</HQMusicUrl>
    <ThumbMediaId>${this.ThumbMediaId}</ThumbMediaId>
  </Music>
</xml>
"""
    }
}
