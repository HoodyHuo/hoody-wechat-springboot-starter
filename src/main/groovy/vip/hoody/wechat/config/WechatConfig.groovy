package vip.hoody.wechat.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * 微信配置类
 */
@Configuration
class WechatConfig {
    private static final Logger log = LoggerFactory.getLogger(this.class)
    /** 微信开发者ID */
    private String APP_ID
    /** 开发者密码 */
    private String APP_SECRET

    /** 用户在微信服务器配置写的token */
    private String TOKEN

    public static final String RESP_MESSAGE_TYPE_TEXT = "text"
    public static final String REQ_MESSAGE_TYPE_TEXT = "text"
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image"
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice"
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video"
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location"
    public static final String REQ_MESSAGE_TYPE_LINK = "link"
    public static final String REQ_MESSAGE_TYPE_EVENT = "event"
    public static final String EVENT_TYPE_SUBSCRIBE = "SUBSCRIBE"
    public static final String EVENT_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE"
    public static final String EVENT_TYPE_SCAN = "SCAN"
    public static final String EVENT_TYPE_LOCATION = "LOCATION"
    public static final String EVENT_TYPE_CLICK = "CLICK"
    public static final String FromUserName = "FromUserName"
    public static final String ToUserName = "ToUserName"
    public static final String MsgType = "MsgType"
    public static final String Content = "Content"
    public static final String Event = "Event"

    WechatConfig(
            @Value('${hoody.wechat.app-id}') String APP_ID,
            @Value('${hoody.wechat.app-secret}') String APP_SECRET,
            @Value('${hoody.wechat.token}') String TOKEN
    ) {
        if (APP_ID == null || APP_SECRET == null) {
            log.warn("""缺少微信开发者信息:
wechat.app-id='xxx'
wechat.app-secret='xxx'
需要在application配置文件中声明
""")
        }
        this.TOKEN = TOKEN
        this.APP_ID = APP_ID
        this.APP_SECRET = APP_SECRET
        log.info(this.toString())
    }


    String getAPP_ID() {
        return APP_ID
    }

    String getAPP_SECRET() {
        return APP_SECRET
    }

    String getTOKEN() {
        return TOKEN
    }
}
