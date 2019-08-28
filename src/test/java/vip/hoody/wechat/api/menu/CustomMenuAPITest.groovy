package vip.hoody.wechat.api.menu

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import vip.hoody.wechat.api.IMenuApi
import vip.hoody.wechat.config.ConfigurationPropertiesAutoConfiguration
import vip.hoody.wechat.config.WechatStarterAutoScanConfiguration
import vip.hoody.wechat.domain.menu.Menu
import vip.hoody.wechat.domain.menu.button.ClickButton
import vip.hoody.wechat.domain.menu.button.LocationSelectButton
import vip.hoody.wechat.domain.menu.button.MediaButton
import vip.hoody.wechat.domain.menu.button.PicPhotoOrAlbumButton
import vip.hoody.wechat.domain.menu.button.PicSysPhotoButton
import vip.hoody.wechat.domain.menu.button.PicWeixinButton
import vip.hoody.wechat.domain.menu.button.ScanCodePushButton
import vip.hoody.wechat.domain.menu.button.ScanCodeWaitMsgButton
import vip.hoody.wechat.domain.menu.button.SubButton
import vip.hoody.wechat.domain.menu.button.ViewButton

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class
])
@TestPropertySource(locations = "classpath:application.yml")
class CustomMenuAPITest {

    @Autowired
    IMenuApi menuApi

    @Test
    void testCreateMenu() {
        Menu menu = new Menu()
        SubButton subButton1 = new SubButton("更多1..")
        SubButton subButton2 = new SubButton("更多2..")
        SubButton subButton3 = new SubButton("更多3..")

        ScanCodePushButton scanCodePushButton = new ScanCodePushButton("ScanCodePush", "ScanCodePush")
        ScanCodeWaitMsgButton waitMsgButton = new ScanCodeWaitMsgButton("ScanCodeWaitMsg", "ScanCodeWaitMsg")
        LocationSelectButton selectButtonl = new LocationSelectButton("LocationSelect", "LocationSelect")
        MediaButton mediaButton = new MediaButton("MediaButton", "4MOS0SN34wAVT4YDGkD2YL7fQqz3i4xNAfLlOlKsot8")
        PicPhotoOrAlbumButton photoOrAlbumButton = new PicPhotoOrAlbumButton("PicSysPhoto", "PicSysPhoto")
        subButton1.addButton(scanCodePushButton)
        subButton1.addButton(waitMsgButton)
        subButton1.addButton(selectButtonl)
        subButton1.addButton(mediaButton)
        subButton1.addButton(photoOrAlbumButton)

        PicSysPhotoButton picSysPhotoButton = new PicSysPhotoButton("PicSysPhoto", "PicSysPhoto")
        PicWeixinButton picWeixinButton = new PicWeixinButton("PicWeixin", "PicWeixin")
        subButton2.addButton(picSysPhotoButton)
        subButton2.addButton(picWeixinButton)

        ClickButton clickButton = new ClickButton("click", "clickkey")
        ViewButton viewButton = new ViewButton("view", "http://hoody.vip")
        subButton3.addButton(clickButton)
        subButton3.addButton(viewButton)

        menu.addButton(subButton1)
        menu.addButton(subButton2)
        menu.addButton(subButton3)
        menuApi.createMenu(menu)
    }

    @Test
    void testGetMenu() {
        Menu menu = menuApi.getMenu()
        println(menu)
    }

    @Test
    void testRemoveMenu() {
        menuApi.removeMenu()
    }
}
