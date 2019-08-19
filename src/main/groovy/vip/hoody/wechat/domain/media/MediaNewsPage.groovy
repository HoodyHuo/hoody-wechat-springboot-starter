package vip.hoody.wechat.domain.media

/**
 * 获取永久图文素材列表的返回数据
 */
class MediaNewsPage {
    /**
     *  该类型的素材的总数
     */
    Integer totalCount
    /**
     * 本次调用获取的素材的数量
     */
    Integer itemCount

    /**
     * 图文素材详情
     */
    List<NewsMedia> newsItemList
    /**
     *
     * @param totalCount
     * @param itemCount
     */
    MediaNewsPage(Integer totalCount, Integer itemCount) {
        this.totalCount = totalCount
        this.itemCount = itemCount
        this.newsItemList = new ArrayList<NewsMedia>()
    }

    Integer getTotalCount() {
        return totalCount
    }

    void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount
    }

    Integer getItemCount() {
        return itemCount
    }

    void setItemCount(Integer itemCount) {
        this.itemCount = itemCount
    }

    List<NewsMedia> getNewsItemList() {
        return newsItemList
    }

    void setNewsItemList(List<NewsMedia> newsItemList) {
        this.newsItemList = newsItemList
    }
}
