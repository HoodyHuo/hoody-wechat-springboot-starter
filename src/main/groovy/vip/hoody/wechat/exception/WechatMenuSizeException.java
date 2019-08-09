package vip.hoody.wechat.exception;

/**
 * 微信一级菜单数量应小于3
 */
public class WechatMenuSizeException extends WechatException {

    public WechatMenuSizeException(int num) {
        super("No more than " + num + " buttons!");
    }

}
