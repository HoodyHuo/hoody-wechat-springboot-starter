package vip.hoody.wechat.exception;

/**
 * @author Hoody
 * 微信一级菜单数量应小于3
 */
public class WechatMenuSizeException extends WechatMenuException {
    public WechatMenuSizeException(int num) {
        super("No more than " + num + " buttons!");
    }

}
