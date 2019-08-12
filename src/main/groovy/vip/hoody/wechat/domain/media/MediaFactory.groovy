package vip.hoody.wechat.domain.media

import org.springframework.boot.configurationprocessor.json.JSONObject
import vip.hoody.wechat.exception.WechatMediaException

class MediaFactory {

    static Media create(JSONObject data) {
        try {
            return new Media(data.media_id, data.type, new Long(data.created_at))
        } catch (NumberFormatException e) {
            throw new WechatMediaException("wechat media api error", e)
        }
    }
}
