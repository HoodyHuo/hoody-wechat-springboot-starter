package vip.hoody.wechat.domain.event.menu

import vip.hoody.wechat.domain.event.BaseEvent

/**
 * pic_weixin 事件
 * PicWeixinButton 发送图片后触发
 */
class PicWeixinEvent extends BaseEvent {
/**
 * eventKey事件KEY值，与自定义菜单接口中KEY值对应
 */
    private String eventKey

    PicWeixinEvent(String toUserName, String fromUserName, String createTime, String msgType, String event, String eventKey) {
        super(toUserName, fromUserName, createTime, msgType, event)
        this.eventKey = eventKey
    }

    String getEventKey() {
        return eventKey
    }

    void setEventKey(String eventKey) {
        this.eventKey = eventKey
    }
}
