package vip.hoody.wechat.domain.event.menu

import vip.hoody.wechat.domain.event.BaseEvent

/**
 *
 * 用户通过自定义菜单按钮扫码
 * ScanCodePushButton 操作后,收到的事件
 */
class ScanCodePushEvent extends BaseEvent {
    /**
     * eventKey事件KEY值，与自定义菜单接口中KEY值对应
     */
    private String eventKey

    ScanCodePushEvent(String toUserName, String fromUserName, String createTime, String msgType, String event, String eventKey) {
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
