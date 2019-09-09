package vip.hoody.wechat.api.qrcode

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import vip.hoody.wechat.api.IQrCodeApi
import vip.hoody.wechat.domain.qrcode.QRCode
import vip.hoody.wechat.exception.qrcode.WechatQrCodeException

@Component
class QrCodeApi implements IQrCodeApi {

    private RestTemplate restTemplate

    @Autowired
    QrCodeApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    QRCode createTempQrCode(Long expireSeconds, String param) throws WechatQrCodeException {
        return null
    }

    QRCode createTempQrCode(Long expireSeconds, int param) throws WechatQrCodeException {
        return null
    }

    QRCode createLimitQrCode(String param) throws WechatQrCodeException {
        return null
    }

    QRCode createLimitQrCode(int param) throws WechatQrCodeException {
        return null
    }

    File getQrCodePic(String ticket) throws WechatQrCodeException {
        return null
    }
}
