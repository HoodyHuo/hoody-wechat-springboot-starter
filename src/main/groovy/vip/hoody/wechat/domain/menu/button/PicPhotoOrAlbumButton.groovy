package vip.hoody.wechat.domain.menu.button;

/**
 * pic_photo_or_album
 * 弹出拍照或者相册发图用户点击按钮后，
 * 微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。
 * 用户选择后即走其他两种流程。
 */
public class PicPhotoOrAlbumButton extends BaseButton {
    private String key;

    PicPhotoOrAlbumButton(String name, String key) {
        super(name, ButtonType.PIC_PHOTO_OR_ALBUM);
        this.key = key
    }

    @Override
    public String toParam() {
        return """
{
    "type":"${this.type}",
    "name":"${this.name}",
    "key":"${this.key}",
}
"""
    }
}
