package vip.hoody.wechat.domain.menu.button

import vip.hoody.wechat.exception.WechatMenuParasException
import vip.hoody.wechat.exception.WechatMenuSizeException

/**
 * menu button factory
 * @author Hoody
 */
class ButtonFactory {

    /**
     * 根据参数创建对应类型按钮
     * @param type
     * @param data
     * @return
     */
    static createButton(String type, Map<String, Object> data) {
        switch (type) {
            case ButtonType.CLICK.key:
                return new ClickButton(data.name, data.key)
                break
            case ButtonType.LOCATION_SELECT.key:
                return new LocationSelectButton(data.name, data.key)
                break
            case ButtonType.MEDIA_ID.key:
                return new MediaButton(data.name, data.media_id)
                break
            case ButtonType.MINI_PROGRAM.key:
                return new MiniProgrammaButton(data.name, data.url, data.appid, data.pagepath)
                break
            case ButtonType.PIC_PHOTO_OR_ALBUM.key:
                return new PicPhotoOrAlbumButton(data.name, data.key)
                break
            case ButtonType.PIC_SYSPHOTO.key:
                return new PicSysPhotoButton(data.name, data.key)
                break
            case ButtonType.PIC_WEIXIN.key:
                return new PicWeixinButton(data.name, data.key)
                break
            case ButtonType.SCAN_CODE_PUSH.key:
                return new ScanCodePushButton(data.name, data.key)
                break
            case ButtonType.SCAN_CODE_WAIT_MSG.key:
                return new ScanCodeWaitMsgButton(data.name, data.key)
                break
            case ButtonType.VIEW.key:
                return new ViewButton(data.name, data.url)
                break
            case ButtonType.VIEW_LIMITED.key:
                return new ViewLimitedButton(data.name, data.media_id)
                break
            default:
                throw new WechatMenuParasException("unknown button type:${type} -${data.toString()}")
        }

    }


}
