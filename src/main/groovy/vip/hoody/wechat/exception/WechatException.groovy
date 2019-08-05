package vip.hoody.wechat.exception

/**
 * 微信异常类
 */
class WechatException extends RuntimeException {
    WechatException(String var1) {
        super(var1)
    }

    WechatException(String var1, Throwable var2) {
        super(var1, var2)
    }

    WechatException(Throwable var1) {
        super(var1)
    }
}
