package vip.hoody.wechat.api.media

import com.alibaba.fastjson.JSON
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import vip.hoody.wechat.api.IMediaApi
import vip.hoody.wechat.config.ConfigurationPropertiesAutoConfiguration
import vip.hoody.wechat.config.WechatStarterAutoScanConfiguration
import vip.hoody.wechat.domain.media.MediaCount
import vip.hoody.wechat.domain.media.MediaOtherPage
import vip.hoody.wechat.domain.media.MediaType

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class
])
@TestPropertySource(locations = "classpath:application.yml")
class MediaAPITest {

    @Autowired
    IMediaApi mediaApi


    /**
     * 4MOS0SN34wAVT4YDGkD2YDgIsdrbPzZmCL4P2s2jnyE
     */
    @Test
    void testUploadVideoMedia() {
        String filePath = "C:\\Users\\Hoody\\Desktop\\1458481699028.mp4"
        String mediaId = mediaApi.uploadVideoMedia(filePath, "荔枝舞", "练习时长不知道")
        println(mediaId)
    }

    /**
     * 4MOS0SN34wAVT4YDGkD2YADzcCZSVYLxY1wpfPySpYc
     */
    @Test
    void testUploadMedia() {
        String filePath = "C:\\Users\\Hoody\\Desktop\\捕获.PNG"
        String mediaId = mediaApi.uploadMedia(filePath, MediaType.IMAGE)
        println(mediaId)
    }

    @Test
    void testGetMediaCount() {
        MediaCount count = mediaApi.getMediaCount()
        println(count)
    }

    @Test
    void testGetMediaOtherList() {
        MediaOtherPage page = mediaApi.getMediaOtherList(MediaType.IMAGE, 0, 20)
        println(page)
        page = mediaApi.getMediaOtherList(MediaType.VIDEO, 0, 20)
        println(page)
        page = mediaApi.getMediaOtherList(MediaType.VOICE, 0, 20)
        println(page)
    }


    @Test
    void testGetMedia() {
        String image = "4MOS0SN34wAVT4YDGkD2YADzcCZSVYLxY1wpfPySpYc"
        String video = "4MOS0SN34wAVT4YDGkD2YDgIsdrbPzZmCL4P2s2jnyE"
        File imageFile = mediaApi.getMedia(image)
        File videoFile = mediaApi.getMedia(video)
        println(imageFile)
        println(videoFile)
    }

    @Test
    void testDeleteMedia() {
        testGetMediaCount()
        mediaApi.deleteMedia("4MOS0SN34wAVT4YDGkD2YDgIsdrbPzZmCL4P2s2jnyE")
        testGetMediaCount()
    }

}
