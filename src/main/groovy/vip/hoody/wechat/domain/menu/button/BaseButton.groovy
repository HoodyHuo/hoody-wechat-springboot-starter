package vip.hoody.wechat.domain.menu.button;

import vip.hoody.wechat.exception.WechatParamSizeException;


/**
 * @author Hoody* 一级菜单按钮
 */
public class BaseButton {

    private static final int MAX_NAME_LENGTH = 16
    /**
     * 菜单标题
     * 不超过16个字节
     */
    private String name

    /**
     * 菜单类型
     */
    private ButtonType type

    public BaseButton(String name, ButtonType type) {
        this.setName(name)
        this.type = type
    }

    public String getName() {
        return name
    }

    public ButtonType getType() {
        return type
    }

    public void setName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new WechatParamSizeException(
                    "invalid button name size " +
                            "max length is 16," +
                            "but get " + name + ":" + name.length());
        }
        this.name = name
    }

    public void setType(ButtonType type) {
        this.type = type
    }

    public String toParam() {
        return """
{
    "name": "${this.name}",
    "type": "${this.type}"
}
"""
    }
}
