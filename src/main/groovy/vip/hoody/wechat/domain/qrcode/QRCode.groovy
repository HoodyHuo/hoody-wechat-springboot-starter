package vip.hoody.wechat.domain.qrcode

/**
 * 创建
 */
class QRCode {
    private String ticket
    private Long exporeSeconds
    private String url

    QRCode(String ticket, Long exporeSeconds, String url) {
        this.ticket = ticket
        this.exporeSeconds = exporeSeconds
        this.url = url
    }

    String getTicket() {
        return ticket
    }

    void setTicket(String ticket) {
        this.ticket = ticket
    }

    Long getExporeSeconds() {
        return exporeSeconds
    }

    void setExporeSeconds(Long exporeSeconds) {
        this.exporeSeconds = exporeSeconds
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    @Override
    public String toString() {
        return """\
QRCode{
    ticket='$ticket', 
    exporeSeconds=$exporeSeconds, 
    url='$url'
}"""
    }
}
