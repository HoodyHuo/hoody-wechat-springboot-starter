package vip.hoody.wechat.domain.menu.button

/**
 * media_id
 * 下发消息（除文本消息）
 * 用户点击media_id类型按钮后，
 * 微信服务器会将开发者填写的永久素材id对应的素材下发给用户，
 * 永久素材类型可以是图片、音频、视频、图文消息。
 * 请注意：
 * 永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 */
class MediaButton extends BaseButton {
    private String mediaId

    MediaButton(String name, String mediaId) {
        super(name, ButtonType.MEDIA_ID)
        this.mediaId = mediaId
    }

    String getMediaId() {
        return mediaId
    }

    void setMediaId(String mediaId) {
        this.mediaId = mediaId
    }

    @Override
    String toParam() {
        return """
{
    "name": "${this.name}",
    "type":"${this.type}",
    "media_id" : "${this.mediaId}"
}
"""
    }

    @Override
    public String toString() {
        return "MediaButton{" +
                "mediaId='" + mediaId + '\'' +
                super.toString() +
                "} "
    }
}
