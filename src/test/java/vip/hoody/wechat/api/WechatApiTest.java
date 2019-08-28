package vip.hoody.wechat.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;
import vip.hoody.wechat.config.ConfigurationPropertiesAutoConfiguration;
import vip.hoody.wechat.config.WechatStarterAutoScanConfiguration;

import static org.testng.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ConfigurationPropertiesAutoConfiguration.class,
        WechatStarterAutoScanConfiguration.class})
@TestPropertySource(locations = "classpath:application.yml")
public class WechatApiTest {

    @Autowired
    private WechatApi wechatApi;

    @Test
    public void testGetAccessToken() {
        assert wechatApi.getAccessToken() != null;
    }
}