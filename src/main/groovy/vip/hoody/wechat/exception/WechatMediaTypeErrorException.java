package vip.hoody.wechat.exception;

import vip.hoody.wechat.domain.media.MediaType;

/**
 * wechat media file type error
 *
 * @author Hoody
 */
public class WechatMediaTypeErrorException extends WechatMediaException {

    private MediaType target;
    private String filePath;

    public WechatMediaTypeErrorException(MediaType target, String filePath) {

        super("");
        this.target = target;
        this.filePath = filePath;
    }

    public WechatMediaTypeErrorException(String msg, Throwable caseError) {
        super(msg, caseError);
    }

    public WechatMediaTypeErrorException(Throwable caseError) {
        super(caseError);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
