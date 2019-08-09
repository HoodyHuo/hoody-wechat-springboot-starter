package vip.hoody.wechat.domain.event

/**
 * 订阅/取订事件
 */
class SubscribeEvent extends BaseEvent {
    /**
     *
     * @param toUserName 开发者微信号
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，event
     * @param event 事件类型
     */
    SubscribeEvent(String toUserName, String fromUserName, String createTime, String msgType, String event) {
        super(toUserName, fromUserName, createTime, msgType, event)
    }

    /**
     * 返回事件是订阅 还是取消订阅
     * @return
     */
    public boolean isSubscribe() {
        return this.event == "subscribe"
    }

    @Override
    public String toString() {
        return "SubscribeEvent{} " + super.toString();
    }
}
