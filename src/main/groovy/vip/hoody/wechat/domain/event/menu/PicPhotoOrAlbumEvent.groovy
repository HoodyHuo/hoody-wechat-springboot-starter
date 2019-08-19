package vip.hoody.wechat.domain.event.menu

import vip.hoody.wechat.domain.event.BaseEvent

/**
 * pic_photo_or_album 事件
 * 弹出拍照或者相册发图用户点击按钮后，
 * 微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。
 * 用户选择后即走其他两种流程。
 */
class PicPhotoOrAlbumEvent extends BaseEvent {
    /**
     * eventKey事件KEY值，与自定义菜单接口中KEY值对应
     */
    private String eventKey

    PicPhotoOrAlbumEvent(String toUserName, String fromUserName, String createTime, String msgType, String event, String eventKey) {
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
