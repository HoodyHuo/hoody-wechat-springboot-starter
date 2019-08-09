package vip.hoody.wechat.domain.menu.button;

/**
 * pic_sysphoto
 * 弹出系统拍照发图用户点击按钮后，
 * 微信客户端将调起系统相机，
 * 完成拍照操作后，会将拍摄的相片发送给开发者，
 * 并推送事件给开发者，同时收起系统相机，
 * 随后可能会收到开发者下发的消息。
 */
public class PicSysPhotoButton extends BaseButton {
    private String key;

    public PicSysPhotoButton(String name, String key) {
        super(name, ButtonType.PIC_SYSPHOTO);
        this.key = key
    }

    String getKey() {
        return key
    }

    void setKey(String key) {
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
