package vip.hoody.wechat.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class Config {

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate()
    }
}
