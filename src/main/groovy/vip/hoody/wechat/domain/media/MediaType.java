package vip.hoody.wechat.domain.media;

/**
 * 临时素材类型
 */
public enum MediaType {

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
    THUMB("thumb");

    private String key;

    private MediaType(String key) {
        this.key = key;
    }

    public static MediaType get(String key) {
        for (MediaType type : values()) {
            if (type.key.equals(key)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public String getKey() {
        return this.key;
    }
}
