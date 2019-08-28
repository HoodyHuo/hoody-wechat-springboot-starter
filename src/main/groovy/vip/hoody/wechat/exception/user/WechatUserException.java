package vip.hoody.wechat.exception.user;

import vip.hoody.wechat.exception.WechatException;

public class WechatUserException extends WechatException {
    public WechatUserException(String var1) {
        super(var1);
    }

    public WechatUserException(String var1, Throwable var2) {
        super(var1, var2);
    }

    public WechatUserException(Throwable var1) {
        super(var1);
    }
}
