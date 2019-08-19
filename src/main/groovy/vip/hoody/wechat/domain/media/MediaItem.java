package vip.hoody.wechat.domain.media;

/**
 * 获取素材列表
 * 其他类型（图片、语音、视频）的返回
 * 图文素材
 * <p>
 * 微信接口 说有url,但是实际返回值没有
 *
 * @see vip.hoody.wechat.domain.media.NewsItem
 */
public class MediaItem {
    private String mediaId;
    private String name;
    private String updateTime;

    /**
     * @param mediaId    素材ID
     * @param name       文件名称
     * @param updateTime 上传时间
     */
    public MediaItem(String mediaId, String name, String updateTime) {
        this.mediaId = mediaId;
        this.name = name;
        this.updateTime = updateTime;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getName() {
        return name;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
