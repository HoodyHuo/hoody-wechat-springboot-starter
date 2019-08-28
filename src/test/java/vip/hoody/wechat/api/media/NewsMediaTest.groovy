package vip.hoody.wechat.api.media

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import vip.hoody.wechat.api.IMediaApi
import vip.hoody.wechat.config.ConfigurationPropertiesAutoConfiguration
import vip.hoody.wechat.config.WechatStarterAutoScanConfiguration
import vip.hoody.wechat.domain.media.MediaNewsPage
import vip.hoody.wechat.domain.media.NewsItem

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class
])
@TestPropertySource(locations = "classpath:application.yml")
class NewsMediaTest {
    @Autowired
    IMediaApi mediaApi

    /**
     * http://mmbiz.qpic.cn/mmbiz_png/kaMgW96icDyhI8ebj0FctOG4ztEP8xJG2RqNicmbP2mN1mlqTF9xt0jtmAWtiaOljicWpHIAXbC2xbAYl8MRvyxoKw/0
     */
    @Test
    void testUploadNewsImg() {
        String filePath = "C:\\Users\\Hoody\\Desktop\\QQ截图20160826171032.png"
        String url = mediaApi.uploadNewsImg(filePath)
        println(url)
    }

    /**
     * 单图文
     * 4MOS0SN34wAVT4YDGkD2YNIx7fjbNNjEvOCcNO_iPsQ
     */
    @Test
    void testUploadNewsMediaSingle() {
        List<NewsItem> list = new ArrayList<NewsItem>()
        NewsItem item1 = new NewsItem(
                "测试单元1",
                "4MOS0SN34wAVT4YDGkD2YADzcCZSVYLxY1wpfPySpYc",
                "1",
                "Hoody",
                "噜噜噜",
                "这是正文<img src=\"http://mmbiz.qpic.cn/mmbiz_png/kaMgW96icDyhI8ebj0FctOG4ztEP8xJG2RqNicmbP2mN1mlqTF9xt0jtmAWtiaOljicWpHIAXbC2xbAYl8MRvyxoKw/0\"/>",
                "http://hoody.vip",
                "http://hoody.vip/admin"
        )
        list.add(item1)
        String mediaId = mediaApi.uploadNewsMedia(list)
        println(mediaId)
    }

    /**
     * 多图文
     * 4MOS0SN34wAVT4YDGkD2YJZNbh4rWHP3Y6F1bcAbK00
     */
    @Test
    void testUploadNewsMediaMulti() {
        List<NewsItem> list = new ArrayList<NewsItem>()
        NewsItem item1 = new NewsItem(
                "测试单元1多图文",
                "4MOS0SN34wAVT4YDGkD2YADzcCZSVYLxY1wpfPySpYc",
                "1",
                "Hoody",
                "",
                "这是正文<img src=\"http://mmbiz.qpic.cn/mmbiz_png/kaMgW96icDyhI8ebj0FctOG4ztEP8xJG2RqNicmbP2mN1mlqTF9xt0jtmAWtiaOljicWpHIAXbC2xbAYl8MRvyxoKw/0\"/>",
                "http://hoody.vip",
                "http://hoody.vip/admin"
        )
        NewsItem item2 = new NewsItem(
                "测试单元2多图文",
                "4MOS0SN34wAVT4YDGkD2YADzcCZSVYLxY1wpfPySpYc",
                "1",
                "Hoody22",
                "",
                "这是正文222<img src=\"http://mmbiz.qpic.cn/mmbiz_png/kaMgW96icDyhI8ebj0FctOG4ztEP8xJG2RqNicmbP2mN1mlqTF9xt0jtmAWtiaOljicWpHIAXbC2xbAYl8MRvyxoKw/0\"/>",
                "http://hoody.vip",
                "http://hoody.vip/admin"
        )
        list.add(item1)
        list.add(item2)
        String mediaId = mediaApi.uploadNewsMedia(list)
        println(mediaId)
    }


    @Test
    void testUpdateMedia() {
        String mediaId = "4MOS0SN34wAVT4YDGkD2YJZNbh4rWHP3Y6F1bcAbK00"
        NewsItem item = new NewsItem(
                "测试单元2多图文修改后",
                "4MOS0SN34wAVT4YDGkD2YADzcCZSVYLxY1wpfPySpYc",
                "1",
                "Hoody22修改后",
                "",
                "这是正文222修改后<img src=\"http://mmbiz.qpic.cn/mmbiz_png/kaMgW96icDyhI8ebj0FctOG4ztEP8xJG2RqNicmbP2mN1mlqTF9xt0jtmAWtiaOljicWpHIAXbC2xbAYl8MRvyxoKw/0\"/>",
                "http://hoody.vip",
                "http://hoody.vip/admin"
        )
        mediaApi.updateMedia(mediaId, item, "0")
    }

    @Test
    void testGetMediaNewsList() {
        MediaNewsPage page = mediaApi.getMediaNewsList(0, 20)
        println(page)
    }

    @Test
    void testGetNewsMedia() {
        String mediaId = "4MOS0SN34wAVT4YDGkD2YGOawuovhlmwjdgL0qhZjsY"
        List<NewsItem> media = mediaApi.getNewsMedia(mediaId)
        println(media)
    }


    @Test
    void testDeleteMedia() {
        String mediaId = "4MOS0SN34wAVT4YDGkD2YGOawuovhlmwjdgL0qhZjsY"
        mediaApi.deleteMedia(mediaId)
    }
}
