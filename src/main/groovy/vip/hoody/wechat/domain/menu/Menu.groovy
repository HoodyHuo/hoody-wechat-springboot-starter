package vip.hoody.wechat.domain.menu;


import vip.hoody.wechat.domain.menu.button.BaseButton;
import vip.hoody.wechat.exception.WechatMenuSizeException;

/**
 * 微信自定义菜单
 */
class Menu {

    private static final int MAX_BUTTON = 3

    private List<BaseButton> buttons

    Menu() {
        this.buttons = new ArrayList<BaseButton>()
    }

    boolean addButton(BaseButton btn) {
        if (this.buttons.size() > MAX_BUTTON) {
            throw new WechatMenuSizeException(MAX_BUTTON)
        }
        return this.buttons.add(btn)
    }

    boolean removeButton(BaseButton btn) {
        return this.buttons.remove(btn)
    }

    String toParam() {
        //TODO Menu toParam
        String btns = ""
        this.buttons.each { BaseButton btn ->
            btns += "${btn.toParam()},"
        }
        String param = """
{
    "button":[${btns}]
}
"""
        return param
    }

    static Menu parse(String json) {
        return new Menu()
    }
}
