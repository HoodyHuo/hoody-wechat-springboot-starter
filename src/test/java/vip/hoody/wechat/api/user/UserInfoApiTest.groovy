package vip.hoody.wechat.api.user

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import vip.hoody.wechat.api.IUserInfoApi
import vip.hoody.wechat.config.ConfigurationPropertiesAutoConfiguration
import vip.hoody.wechat.config.WechatStarterAutoScanConfiguration
import vip.hoody.wechat.domain.user.OpenIdListPage
import vip.hoody.wechat.domain.user.UserInfo
import vip.hoody.wechat.utils.Lang

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class])
@TestPropertySource(locations = "classpath:application.yml")
public class UserInfoApiTest {

    @Autowired
    IUserInfoApi userInfoApi


    @Test
    public void getUserList() {
        OpenIdListPage page = userInfoApi.getUserList()
        println(page)
    }

    /**
     * ohivqtyUvC8RRdRehMXkXhwAMCEc,
     * ohivqt5dp6zSbKz70SEhg5hDoxgI
     */
    @Test
    public void getUserInfo() {
        UserInfo info = userInfoApi.getUserInfo("ohivqtyUvC8RRdRehMXkXhwAMCEc")
        println(info)
        info = userInfoApi.getUserInfo("ohivqtyUvC8RRdRehMXkXhwAMCEc", Lang.en)
        println(info)
    }

    @Test
    void getUserInfoList() {
        List<String> ids = ["ohivqtyUvC8RRdRehMXkXhwAMCEc", "ohivqt5dp6zSbKz70SEhg5hDoxgI"]
        List<UserInfo> infoList = userInfoApi.getUserInfoList(ids, Lang.zh_TW)
        println(infoList)
    }

    @Test
    void setAliasName() {
        String remark = "开发者"
        userInfoApi.setAliasName("ohivqtyUvC8RRdRehMXkXhwAMCEc", remark)
        UserInfo info = userInfoApi.getUserInfo("ohivqtyUvC8RRdRehMXkXhwAMCEc")
        assert info.remark == remark
    }

    @Test
    void blacklist() {
        List<String> ids = ["ohivqtyUvC8RRdRehMXkXhwAMCEc", "ohivqt5dp6zSbKz70SEhg5hDoxgI"]
        userInfoApi.addBlacklist(ids)
        OpenIdListPage page = userInfoApi.getBlacklist()
        assert page.getOpenIdList().containsAll(ids)

        userInfoApi.removeBlacklist(ids)
        page = userInfoApi.getBlacklist()

        assert !page.getOpenIdList().contains(ids[0])
        assert !page.getOpenIdList().contains(ids[1])
    }
}