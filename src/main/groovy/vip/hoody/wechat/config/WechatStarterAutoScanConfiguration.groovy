package vip.hoody.wechat.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * target scan package
 * @author Hoody
 */
@Configuration
@ComponentScan(basePackages = "vip.hoody.wechat")
class WechatStarterAutoScanConfiguration {
    WechatStarterAutoScanConfiguration() {
    }
}
