package vip.hoody.wechat.api;

import vip.hoody.wechat.domain.qrcode.QRCode;
import vip.hoody.wechat.exception.qrcode.WechatQrCodeException;

import java.io.File;

/**
 * @author Hoody
 */
public interface IQrCodeApi {

    public QRCode createTempQrCode(Long expireSeconds, String param) throws WechatQrCodeException;

    public QRCode createTempQrCode(Long expireSeconds, int param) throws WechatQrCodeException;

    public QRCode createLimitQrCode(String param) throws WechatQrCodeException;

    public QRCode createLimitQrCode(int param) throws WechatQrCodeException;

    public File getQrCodePic(String ticket) throws WechatQrCodeException;
}
