package vip.hoody.wechat.domain.media;

/**
 * 多媒体消息基本对象
 */
public class Media {
    /**
     * 媒体文件上传后，获取标识
     */
    private String mediaId;

    /**
     * 媒体文件类型，
     * 分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
     */
    private String type;

    /**
     * 媒体文件上传时间戳
     */
    private Long createdAt;

    public Media(String mediaId, MediaType type, Long createdAt) {
        this.mediaId = mediaId;
        this.type = type.getKey();
        this.createdAt = createdAt;
    }

    protected Media(String mediaId, String type, Long createdAt) {
        this.mediaId = mediaId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type.getKey();
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
