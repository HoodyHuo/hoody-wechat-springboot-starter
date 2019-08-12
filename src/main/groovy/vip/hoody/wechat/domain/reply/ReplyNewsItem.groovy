package vip.hoody.wechat.domain.reply

class ReplyNewsItem {
    /**
     * 图文消息标题
     */
    private String title
    /**
     * 图文消息描述
     */
    private String description
    /**
     * 	图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
     */
    private String picUrl
    /**
     * 点击图文消息跳转链接
     */
    private String url

    ReplyNewsItem(String title, String description, String picUrl, String url) {
        this.title = title
        this.description = description
        this.picUrl = picUrl
        this.url = url
    }
}
