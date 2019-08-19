package vip.hoody.wechat.domain.media

/**
 * 图文素材列表API中的单个图文素材信息
 *
 */
class NewsMedia {
    /**
     * 素材ID
     */
    String mediaId
    /**
     * 素材内的多图文
     */
    List<NewsItem> content
    /**
     * 最后更新时间
     */
    String updateTime

    NewsMedia(String mediaId, String updateTime) {
        this.content = new ArrayList<NewsItem>()
        this.mediaId = mediaId
        this.updateTime = updateTime
    }

    String getMediaId() {
        return mediaId
    }

    List<NewsItem> getContent() {
        return content
    }

    String getUpdateTime() {
        return updateTime
    }
}
