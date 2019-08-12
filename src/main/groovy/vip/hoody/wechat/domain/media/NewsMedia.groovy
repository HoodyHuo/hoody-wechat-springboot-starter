package vip.hoody.wechat.domain.media

class NewsMedia {
    String mediaId
    List<NewsItem> content
    String updateTime

    NewsMedia(String mediaId, String updateTime) {
        this.content = new ArrayList<NewsItem>()
        this.mediaId = mediaId
        this.updateTime = updateTime
    }
}
