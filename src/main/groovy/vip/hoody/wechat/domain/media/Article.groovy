package vip.hoody.wechat.domain.media

/**
 * 图文素材
 * 微信文章,支持html 语法,会过滤js
 */
class Article {
    /**
     * 标题
     */
    private String title

    /**
     * 图文消息的封面图片素材id（必须是永久mediaID）
     * thumb_media_id
     */
    private String thumbMediaId
    /**
     * 作者
     */
    private String author
    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。
     * 如果本字段为没有填写，则默认抓取正文前64个字。
     */
    private String digest
    /**
     * 是否显示封面，0为false，即不显示，1为true，即显示
     */
    private boolean showCoverPic
    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS,
     * 涉及图片url必须来源 "上传图文消息内的图片获取URL"接口获取。
     * 外部图片url将被过滤。
     */
    private String content
    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    private String contentSourceUrl
    /**
     * Uint32 是否打开评论，0不打开，1打开
     */
    private boolean needOpenComment
    /**
     * Uint32 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    private boolean onlyFansCanComment

    /**
     * @param title 标题
     * @param thumbMediaId 图文消息的封面图片素材id（必须是永久mediaID）
     * @param showCoverPic 是否显示封面，0为false，即不显示，1为true，即显示
     * @param content 图文消息的具体内容 支持HTML标签，必须少于2万字符，小于1M 涉及图片url必须来源 "上传图文消息内的图片获取URL"接口获取
     * @param contentSourceUrl 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    Article(String title, String thumbMediaId, boolean showCoverPic, String content, String contentSourceUrl) {
        this.title = title
        this.thumbMediaId = thumbMediaId
        this.showCoverPic = showCoverPic
        this.content = content
        this.contentSourceUrl = contentSourceUrl
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getThumbMediaId() {
        return thumbMediaId
    }

    void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId
    }

    String getAuthor() {
        return author
    }

    void setAuthor(String author) {
        this.author = author
    }

    String getDigest() {
        return digest
    }

    void setDigest(String digest) {
        this.digest = digest
    }

    boolean getShowCoverPic() {
        return showCoverPic
    }

    void setShowCoverPic(boolean showCoverPic) {
        this.showCoverPic = showCoverPic
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    String getContentSourceUrl() {
        return contentSourceUrl
    }

    void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl
    }

    boolean getNeedOpenComment() {
        return needOpenComment
    }

    void setNeedOpenComment(boolean needOpenComment) {
        this.needOpenComment = needOpenComment
    }

    boolean getOnlyFansCanComment() {
        return onlyFansCanComment
    }

    void setOnlyFansCanComment(boolean onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment
    }
}
