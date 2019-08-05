package vip.hoody.wechat.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
/** 指定扫描包路径 */
@ComponentScan(basePackages = "vip.hoody.wechat")
class StarterConfiguration {

}
