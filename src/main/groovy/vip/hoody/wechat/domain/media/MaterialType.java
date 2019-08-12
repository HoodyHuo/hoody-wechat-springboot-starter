package vip.hoody.wechat.domain.media;

/**
 * 永久素材类型
 */
public enum MaterialType {
    /**
     * 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
     */
    IMAGE("image"),

    /**
     * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     */
    VOICE("voice"),

    /**
     * 视频（video）：10MB，支持MP4格式
     */
    VIDEO("video"),

    /**
     * 缩略图（thumb）：64KB，支持JPG格式
     */
    NEWS("news");

    private String key;

    private MaterialType(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public String getKey() {
        return this.key;
    }
}
