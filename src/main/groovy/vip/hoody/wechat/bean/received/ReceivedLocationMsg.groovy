package vip.hoody.wechat.bean.received

/**
 * 地理位置
 * 微信用户信息对象
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>1351776360</CreateTime>
 *   <MsgType><![CDATA[location]]></MsgType>
 *   <Location_X>23.134521</Location_X>
 *   <Location_Y>113.358803</Location_Y>
 *   <Scale>20</Scale>
 *   <Label><![CDATA[位置信息]]></Label>
 *   <MsgId>1234567890123456</MsgId>
 * </xml>
 */
class ReceivedLocationMsg extends ReceivedBaseMsg {

    private String Location_X
    private String Location_Y
    private String Scale
    private String Label

    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，地理位置为location
     * @param msgId 消息id，64位整型
     * @param location_X 地理位置维度
     * @param location_Y 地理位置经度
     * @param scale 地图缩放大小
     * @param label 地理位置信息
     */
    ReceivedLocationMsg(String toUserName, String fromUserName, String createTime, String msgType, String msgId, String location_X, String location_Y, String scale, String label) {
        super(toUserName, fromUserName, createTime, msgType, msgId)
        Location_X = location_X
        Location_Y = location_Y
        Scale = scale
        Label = label
    }
}
