package vip.hoody.wechat.domain.event.menu

import vip.hoody.wechat.domain.event.BaseEvent

/**
 * pic_sysphoto事件
 * PicSysPhotoButton 拍摄图片发送后触发
 */
class PicSysPhotoEvent extends BaseEvent {
    /**
     * eventKey事件KEY值，与自定义菜单接口中KEY值对应
     */
    private String eventKey

    PicSysPhotoEvent(String toUserName, String fromUserName, String createTime, String msgType, String event, String eventKey) {
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
