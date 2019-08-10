package vip.hoody.wechat.domain.menu.button

import vip.hoody.wechat.exception.WechatMenuSizeException

/**
 * 二级菜单按钮
 * 个数应为1~5个
 */
class SubButton extends BaseButton {
    private List<BaseButton> buttons
    private static final int MAX_BUTTON = 3

    SubButton(String name) {
        super(name, ButtonType.SUB_BUTTON)
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

    @Override
    String toParam() {
        String subs = ""
        this.buttons.each {
            subs += "${it.toParam()},"
        }

        return """
{
    "name": "${this.name}",
    "sub_button":[${subs}]
}
"""
    }
}
