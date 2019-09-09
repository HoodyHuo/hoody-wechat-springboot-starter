package vip.hoody.wechat.exception.qrcode

import vip.hoody.wechat.exception.WechatException

class WechatQrCodeException extends WechatException {
    WechatQrCodeException(String var1) {
        super(var1)
    }

    WechatQrCodeException(String var1, Throwable var2) {
        super(var1, var2)
    }

    WechatQrCodeException(Throwable var1) {
        super(var1)
    }
}
