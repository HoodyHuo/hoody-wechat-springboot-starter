package vip.hoody.wechat.domain.media

class MediaCount {
    private int voiceCount
    private int videoCount
    private int imageCount
    private int newsCount


    int getVoiceCount() {
        return voiceCount
    }

    void setVoiceCount(int voiceCount) {
        this.voiceCount = voiceCount
    }

    int getVideoCount() {
        return videoCount
    }

    void setVideoCount(int videoCount) {
        this.videoCount = videoCount
    }

    int getImageCount() {
        return imageCount
    }

    void setImageCount(int imageCount) {
        this.imageCount = imageCount
    }

    int getNewsCount() {
        return newsCount
    }

    void setNewsCount(int newsCount) {
        this.newsCount = newsCount
    }

    @Override
    public String toString() {
        return "MediaCount{" +
                "voiceCount=" + voiceCount +
                ", videoCount=" + videoCount +
                ", imageCount=" + imageCount +
                ", newsCount=" + newsCount +
                '}';
    }
}
