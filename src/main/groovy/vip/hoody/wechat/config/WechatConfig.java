package vip.hoody.wechat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信配置类
 */
@Component
@ConfigurationProperties(
        prefix = "hoody.wechat"
)
public class WechatConfig {
    private static final Logger log = LoggerFactory.getLogger(WechatConfig.class);
    /**
     * 微信开发者ID
     */
    private String appId;
    /**
     * 开发者密码
     */
    private String appSecret;
    /**
     * 用户在微信服务器配置写的token
     */
    private String token;
    /**
     * 微信access-token 刷新频率
     * 单位:秒
     * default: 7200
     */
    private Long tokenRate = 7200L * 1000;

    /**
     * 映射微信处理地址
     */
    private String url = "wechat";

    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
    public static final String EVENT_TYPE_SUBSCRIBE = "SUBSCRIBE";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "UNSUBSCRIBE";
    public static final String EVENT_TYPE_SCAN = "SCAN";
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    public static final String EVENT_TYPE_CLICK = "CLICK";
    public static final String FromUserName = "FromUserName";
    public static final String ToUserName = "ToUserName";
    public static final String MsgType = "MsgType";
    public static final String Content = "Content";
    public static final String Event = "Event";


    @Override
    public String toString() {
        return "WechatConfig{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", token='" + token + '\'' +
                ", tokenRate=" + tokenRate +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenRate() {
        return tokenRate;
    }

    /**
     * 刷新频率 单位:秒
     *
     * @param tokenRate
     */
    public void setTokenRate(Long tokenRate) {
        if (tokenRate == null) {
            this.tokenRate = 7200L * 1000;
            return;
        }
        this.tokenRate = tokenRate * 1000;
    }
}
