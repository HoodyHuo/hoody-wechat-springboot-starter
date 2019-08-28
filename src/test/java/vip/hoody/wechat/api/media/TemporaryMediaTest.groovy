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
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.domain.media.TemporaryMediaUploadResult

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class
])
@TestPropertySource(locations = "classpath:application.yml")
class TemporaryMediaTest {
    @Autowired
    IMediaApi mediaApi

    /**
     * [type:video,
     * media_id:tPu57ueMnjxokHs5Z1sK8G4ZElaD_YaXUsa4GL4AJXpzF8NJsKO8HFHA1hfwcFd6,
     * created_at:1566979119]
     */
    @Test
    void testUploadTemporaryMedia() {
        String filePath = "C:\\Users\\Hoody\\Desktop\\1458481699028.mp4"
        TemporaryMediaUploadResult result = mediaApi.uploadTemporaryMedia(filePath, MediaType.VIDEO)
        println(result)
    }

    @Test
    void testGetTemporaryMedia() {
        String mediaId = "tPu57ueMnjxokHs5Z1sK8G4ZElaD_YaXUsa4GL4AJXpzF8NJsKO8HFHA1hfwcFd6"
        File file = mediaApi.getTemporaryMedia(mediaId)
        println(file)
    }
}
