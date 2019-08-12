package vip.hoody.wechat.domain.media;

/**
 * 获取素材列表
 * 其他类型（图片、语音、视频）的返回
 * 图文素材
 *
 * @see vip.hoody.wechat.domain.media.NewsItem
 */
public class MediaItem {
    String mediaId;
    String name;
    String updateTime;
    String url;

    /**
     * @param mediaId    素材ID
     * @param name       文件名称
     * @param updateTime 上传时间
     * @param url        资源URL
     */
    public MediaItem(String mediaId, String name, String updateTime, String url) {
        this.mediaId = mediaId;
        this.name = name;
        this.updateTime = updateTime;
        this.url = url;
    }
}
