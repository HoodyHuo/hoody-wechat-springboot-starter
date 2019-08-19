package vip.hoody.wechat.api;

import vip.hoody.wechat.domain.media.*;
import vip.hoody.wechat.exception.WechatMediaException;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Hoody
 * wechat media manage api <br>
 * <pre>
 * use:<br>
 *     @Autowired
 *     IMediaApi mediaAPI
 * </pre>
 */
public interface IMediaApi {

    /**
     * upload temporary media
     * 上传临时素材
     *
     * @param filePath absolute path
     * @param type     media type
     * @return map
     * <pre>
     * "type":"TYPE", <br>
     * "media_id":"MEDIA_ID",<br>
     * "created_at":123456789 <br>
     * </pre>
     * @throws WechatMediaException api error
     */
    Map<String, String> uploadTemporaryMedia(String filePath, MediaType type) throws WechatMediaException;

    /**
     * 下载临时素材
     * TODO 增加重载,可以附带存储路径
     *
     * @param mediaId 素材编号
     * @return file 临时目录下的文件
     * @throws WechatMediaException api error
     */
    File getTemporaryMedia(String mediaId) throws WechatMediaException;

    /**
     * upload image for news media
     * 上传图文素材内的图片
     *
     * @param imgFilePath imgFilePath (only jpg/png)
     * @return url for image
     * @throws WechatMediaException api error
     */
    String uploadNewsImg(String imgFilePath) throws WechatMediaException;

    /**
     * add news media
     * 添加图文素材
     *
     * @param items news list
     * @return mediaId for the news media
     * @throws WechatMediaException api error
     */
    String uploadNewsMedia(List<NewsItem> items) throws WechatMediaException;

    /**
     * upload video media 上传视频永久素材<br>
     * ps: <br>
     * upload others media type <br>
     * use this.uploadMedia(String filePath, MediaType type)
     *
     * @param filePath     absolute file path
     * @param title        video title
     * @param introduction video introduction
     * @return mediaId
     * @throws WechatMediaException api error
     */
    String uploadVideoMedia(String filePath, String title, String introduction) throws WechatMediaException;

    /**
     * upload media(image/voice/thumb) <br>
     * 上传永久素材 (图片/音频/缩略图)
     *
     * @param filePath absolute file path
     * @param type     MediaType image/voice/thumb
     * @return media id
     * @throws WechatMediaException api error
     */
    String uploadMedia(String filePath, MediaType type) throws WechatMediaException;

    /**
     * delete media 删除永久素材 <br>
     * image/voice/news/thumb
     *
     * @param mediaId wechat media_id
     * @throws WechatMediaException api error
     */
    void deleteMedia(String mediaId) throws WechatMediaException;

    /**
     * get media file  获取永久素材<br>
     * ps:<br>
     * get news media <br>
     * use: getNewsMedia(String mediaId)
     *
     * @param mediaId wechat media_id
     * @return file in temp
     * @throws WechatMediaException api error
     */
    File getMedia(String mediaId) throws WechatMediaException;

    /**
     * get news media 获取图文永久素材<br>
     * ps:<br>
     * get others media <br>
     * use: getMedia(String mediaId)
     *
     * @param mediaId wechat media_id
     * @return file in temp
     * @throws WechatMediaException api error
     */
    List<NewsItem> getNewsMedia(String mediaId) throws WechatMediaException;

    /**
     * update news media
     * 修改/更新图文素材
     *
     * @param mediaId news media id
     * @param item    news value
     * @param index   of news media list (start from 0)
     *                要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
     * @throws WechatMediaException api error
     */
    void updateMedia(String mediaId, NewsItem item, String index) throws WechatMediaException;

    /**
     * get media count group by type<br>
     * 获取永久素材总数<br>
     * 图片和图文消息素材（包括单图文和多图文）的总数上限为5000，其他素材的总数上限为1000
     *
     * @return map <pre>
     *      "voice_count":COUNT,<br>
     *      "video_count":COUNT,<br>
     *      "image_count":COUNT,<br>
     *      "news_count":COUNT<br>
     *      </pre>
     * @throws WechatMediaException api error
     */
    Map<String, Integer> getMediaCount() throws WechatMediaException;

    /**
     * get media list (extra news type)
     *
     * @param type   图片（image）、视频（video）、语音 （voice）
     * @param offset offset (start from 0) 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  count (1-20) 返回素材的数量，取值在1到20之间
     * @return media list of type
     * @throws WechatMediaException api error
     */
    MediaOtherPage getMediaOtherList(MediaType type, int offset, int count) throws WechatMediaException;

    /**
     * get news media list
     *
     * @param offset offset (start from 0) 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  count (1-20) 返回素材的数量，取值在1到20之间
     * @return news media list
     * @throws WechatMediaException api error
     */
    MediaNewsPage getMediaNewsList(int offset, int count) throws WechatMediaException;
}

