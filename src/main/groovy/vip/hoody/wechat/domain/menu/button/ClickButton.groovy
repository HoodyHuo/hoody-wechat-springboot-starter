package vip.hoody.wechat.domain.menu.button;

/**
 * 点击类型按钮
 * 点击后会将key值作为用户消息发送给服务器
 */
public class ClickButton extends BaseButton {

    /**
     * click等点击类型必须
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;

    public ClickButton(String name, String key) {
        super(name, ButtonType.CLICK);
        this.key = key;
    }

    @Override
    String toParam() {
        return """
{
    "type":"click",
    "name":"${this.name}",
    "key":"${this.key}",
}
"""
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
