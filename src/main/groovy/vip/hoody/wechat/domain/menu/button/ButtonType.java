package vip.hoody.wechat.domain.menu.button;

public enum ButtonType {

    /**
     * 自按钮类型
     */
    SUB_BUTTON("sub_btn"),
    /**
     * 点击类型
     */
    CLICK("click"),
    /**
     * 网页类型
     */
    VIEW("view"),
    /**
     * 小程序类型
     */
    MINI_PROGRAM("miniprogram"),

    /**
     * 扫码推事件
     */
    SCAN_CODE_PUSH("scancode_push"),

    /**
     * 扫码推事件且弹出“消息接收中”提示框
     */
    SCAN_CODE_WAIT_MSG("scancode_waitmsg"),
    /**
     * 调用相机拍照并发给开发者
     */
    PIC_SYSPHOTO("pic_sysphoto"),

    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),

    PIC_WEIXIN("pic_weixin"),

    LOCATION_SELECT("location_select"),

    MEDIA_ID("media_id"),

    VIEW_LIMITED("view_limited");

    private String key;

    private ButtonType(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}
