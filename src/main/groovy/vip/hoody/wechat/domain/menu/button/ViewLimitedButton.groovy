package vip.hoody.wechat.domain.menu.button;

/**
 * view_limited
 * 跳转图文消息URL用户点击view_limited类型按钮后，
 * 微信客户端将打开开发者在按钮中填写的
 * 永久素材id对应的图文消息URL，永久素材类型只支持图文消息。
 * 请注意：
 * 永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
 */
public class ViewLimitedButton extends BaseButton {
    private String mediaId;

    public ViewLimitedButton(String name, String mediaId) {
        super(name, ButtonType.VIEW_LIMITED);
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }


    @Override
    String toParam() {
        return """
        {
            "name": "${this.name}",
            "type": "${this.type}",
            "media_id" : "${this.mediaId}"
        }
        """
    }

    @Override
    public String toString() {
        return "ViewLimitedButton{" +
                "mediaId='" + mediaId + '\'' +
                super.toString() +
                "} "
    }
}
