package vip.hoody.wechat.domain.event.menu

import vip.hoody.wechat.domain.event.BaseEvent

/**
 * location_select 事件
 * LocationSelectButton 选择定位并发送后触发
 * 弹出地理位置选择器用户点击按钮后，
 * 微信客户端将调起地理位置选择工具，完成选择操作后，
 * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，
 * 随后可能会收到开发者下发的消息
 *
 */
class LocationSelectEvent extends BaseEvent {
    /**
     * eventKey事件KEY值，与自定义菜单接口中KEY值对应
     */
    private String eventKey

    LocationSelectEvent(String toUserName, String fromUserName, String createTime, String msgType, String event, String eventKey) {
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
