package vip.hoody.wechat.api.media

import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.exception.WechatFileNotExists
import vip.hoody.wechat.exception.WechatMediaException

class FileCheck {
    /**
     * 检查上传素材文件
     * @param filePath
     * @param type
     */
    public static void check(String filePath, MediaType type) {
        File file = new File(filePath)
        if (!file.exists() || !file.isFile()) {
            throw new WechatFileNotExists(filePath)
        }
        checkFileSize(file, type)
    }


    /**
     * 检查素材文件体积
     * @param filePath
     * @param type
     */
    private static void checkFileSize(File file, MediaType type) {
        switch (type) {
            case MediaType.IMAGE:
                if (file.length() > 2 * 1024 * 1024) {
                    throw new WechatMediaException("IMAGE file must less than 2m")
                }
                break
            case MediaType.VOICE:
                if (file.length() > 2 * 1024 * 1024) {
                    throw new WechatMediaException("VOICE file must less than 2m")
                }
                break
            case MediaType.VIDEO:
                if (file.length() > 10 * 1024 * 1024) {
                    throw new WechatMediaException("VIDEO file must less than 10m")
                }
                break
            case MediaType.THUMB:
                if (file.length() > 64 * 1024) {
                    throw new WechatMediaException("THUMB file must less than 64kb")
                }
                break
        }
    }
}
