package vip.hoody.wechat.domain.event

/**
 * 上报地理位置事件
 * 用户同意上报地理位置后，
 * 每次进入公众号会话时，
 * 都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，
 * 公众号可以在公众平台网站中修改以上设置。
 * 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>123456789</CreateTime>
 *   <MsgType><![CDATA[event]]></MsgType>
 *   <Event><![CDATA[LOCATION]]></Event>
 *   <Latitude>23.137466</Latitude>
 *   <Longitude>113.352425</Longitude>
 *   <Precision>119.385040</Precision>
 * </xml>
 */
class LocationEvent extends BaseEvent {
    private String latitude
    private String longitude
    private String precision

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型 LOCATION
     * @param latitude 地理位置纬度
     * @param longitude 地理位置经度
     * @param precision 地理位置精度
     */
    LocationEvent(String toUserName, String fromUserName, String createTime, String msgType,
                  String event, String latitude, String longitude, String precision) {
        super(toUserName, fromUserName, createTime, msgType, event)
        this.latitude = latitude
        this.longitude = longitude
        this.precision = precision
    }

    String getLatitude() {
        return latitude
    }

    void setLatitude(String latitude) {
        this.latitude = latitude
    }

    String getLongitude() {
        return longitude
    }

    void setLongitude(String longitude) {
        this.longitude = longitude
    }

    String getPrecision() {
        return precision
    }

    void setPrecision(String precision) {
        this.precision = precision
    }

    @Override
    public String toString() {
        return "LocationEvent{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", precision='" + precision + '\'' +
                "} " + super.toString();
    }
}
