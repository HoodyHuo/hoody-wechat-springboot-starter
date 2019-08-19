package vip.hoody.wechat.domain.menu

import vip.hoody.wechat.domain.menu.button.*
import vip.hoody.wechat.exception.WechatMenuSizeException;

/**
 * 微信自定义菜单
 */
class Menu implements Serializable {

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
        String btns = ""
        for (int i = 0; i < this.buttons.size(); i++) {
            BaseButton btn = this.buttons.get(i)
            btns += "${i != 0 ? "," : ""}${btn.toParam()}"
        }
        String param = """
                        {
                            "button": [${btns}]
                        }
                        """
        return param
    }

    static Menu parse(Map<String, Object> data) {
        Menu menu = new Menu()
        data.button.each { btn ->
            if (btn.sub_button.size() == 0) {
                menu.addButton(ButtonFactory.createButton(btn.type, btn))
            } else {
                SubButton subButton = new SubButton(btn.name)
                btn.sub_button.each { sub ->
                    subButton.addButton(ButtonFactory.createButton(sub.type, sub))
                }
                menu.buttons.add(subButton)
            }
        }
        return menu
    }
}
