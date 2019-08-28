package vip.hoody.wechat.domain.media

/**
 * 获取非图文素材列表
 * (视频,图片,缩略图,语音)
 */
class MediaOtherPage {
    /**
     *  该类型的素材的总数
     */
    Integer totalCount
    /**
     * 本次调用获取的素材的数量
     */
    Integer itemCount

    /**
     * 各素材信息
     */
    List<MediaItem> items

    MediaOtherPage(Integer totalCount, Integer itemCount) {
        this.totalCount = totalCount
        this.itemCount = itemCount
        this.items = new ArrayList<MediaItem>()
    }

    Integer getTotalCount() {
        return totalCount
    }

    Integer getItemCount() {
        return itemCount
    }

    List<MediaItem> getItems() {
        return items
    }

    @Override
    public String toString() {
        return "MediaOtherPage{" +
                "totalCount=" + totalCount +
                ", itemCount=" + itemCount +
                ", items=" + items +
                '}';
    }
}
