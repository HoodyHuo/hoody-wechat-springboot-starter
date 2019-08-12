package vip.hoody.wechat.domain.media

class MediaOtherPage {
    /**
     *  该类型的素材的总数
     */
    Integer totalCount
    /**
     * 本次调用获取的素材的数量
     */
    Integer itemCount

    List<MediaItem> items

    MediaOtherPage(Integer totalCount, Integer itemCount) {
        this.totalCount = totalCount
        this.itemCount = itemCount
        this.items = new ArrayList<MediaItem>()
    }
}
