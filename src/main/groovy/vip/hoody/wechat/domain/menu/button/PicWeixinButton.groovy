package vip.hoody.wechat.domain.menu.button;

/**
 * pic_weixin
 * 弹出微信相册发图器用户点击按钮后，
 * 微信客户端将调起微信相册，
 * 完成选择操作后，将选择的相片发送给开发者的服务器，
 * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
 */
public class PicWeixinButton extends BaseButton {
    private String key;

    public PicWeixinButton(String name, String key) {
        super(name, ButtonType.PIC_WEIXIN);
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
