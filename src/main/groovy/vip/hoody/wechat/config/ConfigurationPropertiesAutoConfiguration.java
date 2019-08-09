package vip.hoody.wechat.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.hoody.wechat.handler.DefaultWechatMsgHandler;
import vip.hoody.wechat.handler.IWechatMsgHandler;

@Configuration
public class ConfigurationPropertiesAutoConfiguration {

    public ConfigurationPropertiesAutoConfiguration() {

    }

    /**
     * 当没有注入 IWechatMsgHandler 处理器时,注入默认消息处理器
     * @return IWechatMsgHandler default implement
     */
    @ConditionalOnMissingBean
    @Bean
    public IWechatMsgHandler getIWechatMsgHandler() {
        return new DefaultWechatMsgHandler();
    }
}
