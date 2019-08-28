package vip.hoody.wechat.domain.media

import com.alibaba.fastjson.annotation.JSONField

class TemporaryMediaUploadResult {
    private String type
    private String mediaId
    private Date createdAt


    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getMediaId() {
        return mediaId
    }

    void setMediaId(String mediaId) {
        this.mediaId = mediaId
    }

    Date getCreatedAt() {
        return createdAt
    }

    void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt
    }

    @JSONField(name = "created_at")
    void setCreatedAt(long time) {
        this.createdAt = new Date(time * 1000)
    }

    @Override
    public String toString() {
        return "TemporaryMediaUploadResult{" +
                "type='" + type + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
