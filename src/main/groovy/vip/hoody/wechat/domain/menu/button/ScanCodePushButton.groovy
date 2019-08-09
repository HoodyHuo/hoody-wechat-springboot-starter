package vip.hoody.wechat.domain.menu.button;

/**
 * @author Hoody*
 * scancode_push
 * 扫码推事件用户点击按钮后，
 * 微信客户端将调起扫一扫工具，
 * 完成扫码操作后显示扫描结果（如果是URL，将进入URL），
 * 且会将扫码的结果传给开发者，开发者可以下发消息。
 */
public class ScanCodePushButton extends BaseButton {
    private String key;

    public ScanCodePushButton(String name, String key) {
        super(name, ButtonType.SCAN_CODE_PUSH);
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
