package vip.hoody.wechat.api.user

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import vip.hoody.wechat.api.IUserTagApi
import vip.hoody.wechat.config.ConfigurationPropertiesAutoConfiguration
import vip.hoody.wechat.config.WechatStarterAutoScanConfiguration
import vip.hoody.wechat.domain.user.OpenIdListPage
import vip.hoody.wechat.domain.user.Tag

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class])
@TestPropertySource(locations = "classpath:application.yml")
class IUserTagApiTest {

    @Autowired
    IUserTagApi userTagApi

    @Test
    void tagCreate() {
        Tag tag = userTagApi.tagCreate("Test2标签")
        println(tag)
    }

    @Test
    void tagList() {
        List<Tag> tagList = userTagApi.tagList()
        println(tagList)
    }

    @Test
    void tagUpdate() {
        userTagApi.tagUpdate("101", "jsjsj")
    }

    @Test
    void tagDelete() {
        userTagApi.tagDelete("101")
    }

    @Test
    void tagUsers() {
        String tagId = "100"
        List<String> ids = ["ohivqtyUvC8RRdRehMXkXhwAMCEc", "ohivqt5dp6zSbKz70SEhg5hDoxgI"]
        userTagApi.tagUsers("2", ids)
        userTagApi.tagUsers("102", "ohivqtyUvC8RRdRehMXkXhwAMCEc")
    }

    @Test
    void getTagsByUser() {
        String id = "ohivqtyUvC8RRdRehMXkXhwAMCEc"
        List<String> tagIds = userTagApi.getTagsByUser(id)
        println(tagIds)
    }

    @Test
    void unTagUsers() {
        String id = "ohivqtyUvC8RRdRehMXkXhwAMCEc"
        List<String> ids = ["ohivqtyUvC8RRdRehMXkXhwAMCEc", "ohivqt5dp6zSbKz70SEhg5hDoxgI"]
        String tag = "100"
//        userTagApi.unTagUsers(tag, id)
        userTagApi.unTagUsers(tag, ids)
    }

    @Test
    void getUserListByTag() {
        String tag = "2"
        OpenIdListPage page = userTagApi.getUserListByTag(tag)
        println(page)
        page = userTagApi.getUserListByTag(tag, "ohivqtyUvC8RRdRehMXkXhwAMCEc")
        println(page)
    }
}
