package vip.hoody.wechat.exception;

/**
 * 微信异常
 * 文件不存在
 *
 * @author Hoody
 */
public class WechatFileNotExists extends WechatMediaException {
    private String filePath;

    public WechatFileNotExists(String filePath) {
        super("");
        this.filePath = filePath;
    }

    public WechatFileNotExists(String filePath, Throwable cause) {
        super("", cause);
        this.filePath = filePath;
    }

    @Override
    public String getMessage() {
        return "file " + this.filePath + " not exist";
    }
}
