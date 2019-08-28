package vip.hoody.wechat.domain.media

/**
 *  获取永久素材时,返回的图文素材
 *  其他类型
 * @see vip.hoody.wechat.domain.media.MediaItem
 */
class NewsItem {

    /**
     *  图文消息的标题
     */
    private String title
    /**
     * 图文消息的封面图片素材id（必须是永久mediaID）
     */
    private String thumbMediaId
    /**
     * 	是否显示封面，0为false，即不显示，1为true，即显示
     */
    private String showCoverPic
    /**
     * 作者
     */
    private String author
    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     */
    private String digest
    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     */
    private String content
    /**
     * 图文页的URL
     */
    private String url
    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    private String contentSourceUrl
    /**
     * 是否打开评论，0不打开，1打开
     */
    private String needOpenComment
    /**
     * 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    private String onlyFansCanComment

    /**
     *
     * @param title 图文消息的标题
     * @param thumbMediaId 图文消息的封面图片素材id（必须是永久mediaID）
     * @param showCoverPic 是否显示封面，0为false，即不显示，1为true，即显示
     * @param author 作者
     * @param digest 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     * @param content 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     * @param url 图文页的URL
     * @param contentSourceUrl 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    NewsItem(String title, String thumbMediaId, String showCoverPic, String author, String digest, String content, String url, String contentSourceUrl) {
        this.title = title
        this.thumbMediaId = thumbMediaId
        this.showCoverPic = showCoverPic
        this.author = author
        this.digest = digest
        this.content = content
        this.url = url
        this.contentSourceUrl = contentSourceUrl
    }

    /**
     *
     * @param title 图文消息的标题
     * @param thumbMediaId 图文消息的封面图片素材id（必须是永久mediaID）
     * @param showCoverPic 是否显示封面，0为false，即不显示，1为true，即显示
     * @param content 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     * @param contentSourceUrl 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    NewsItem(String title, String thumbMediaId, String showCoverPic, String content, String contentSourceUrl) {
        this.title = title
        this.thumbMediaId = thumbMediaId
        this.showCoverPic = showCoverPic
        this.content = content
        this.contentSourceUrl = contentSourceUrl
    }

    /**
     *
     * @param title 图文消息的标题
     * @param thumbMediaId 图文消息的封面图片素材id（必须是永久mediaID）
     * @param showCoverPic 是否显示封面，0为false，即不显示，1为true，即显示
     * @param author 作者
     * @param digest 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
     * @param content 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
     * @param url 图文页的URL
     * @param contentSourceUrl 图文消息的原文地址，即点击“阅读原文”后的URL
     * @param needOpenComment 是否打开评论，0不打开，1打开
     * @param onlyFansCanComment 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    NewsItem(String title, String thumbMediaId, String showCoverPic, String author, String digest, String content, String url, String contentSourceUrl, String needOpenComment, String onlyFansCanComment) {
        this.title = title
        this.thumbMediaId = thumbMediaId
        this.showCoverPic = showCoverPic
        this.author = author
        this.digest = digest
        this.content = content
        this.url = url
        this.contentSourceUrl = contentSourceUrl
        this.needOpenComment = needOpenComment
        this.onlyFansCanComment = onlyFansCanComment
    }

    String toJSON() {
        return """ {
            "title" : "${this.title}" ,
            "thumb_media_id" : "${this.thumbMediaId}" ,
            "author" : "${this.author}" ,
            "digest" : "${this.digest}" ,
            "show_cover_pic" : "${this.showCoverPic}" ,
            "content" : "${this.content}" ,
            "content_source_url" : "${this.contentSourceUrl}" ,
            "need_open_comment" : "${this.needOpenComment}" ,
            "only_fans_can_comment" : "${this.onlyFansCanComment}"
        }"""
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

    String getShowCoverPic() {
        return showCoverPic
    }

    void setShowCoverPic(String showCoverPic) {
        this.showCoverPic = showCoverPic
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

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    String getContentSourceUrl() {
        return contentSourceUrl
    }

    void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl
    }

    String getNeedOpenComment() {
        return needOpenComment
    }

    void setNeedOpenComment(String needOpenComment) {
        this.needOpenComment = needOpenComment
    }

    String getOnlyFansCanComment() {
        return onlyFansCanComment
    }

    void setOnlyFansCanComment(String onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", showCoverPic='" + showCoverPic + '\'' +
                ", author='" + author + '\'' +
                ", digest='" + digest + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", contentSourceUrl='" + contentSourceUrl + '\'' +
                ", needOpenComment='" + needOpenComment + '\'' +
                ", onlyFansCanComment='" + onlyFansCanComment + '\'' +
                '}';
    }
}
