package vip.hoody.wechat.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import vip.hoody.wechat.handler.DefaultEventHandler;
import vip.hoody.wechat.handler.DefaultWechatMsgHandler;
import vip.hoody.wechat.handler.IWechatEventHandler;
import vip.hoody.wechat.handler.IWechatMsgHandler;

/**
 * 注入配置类
 * inject default handler and manager
 *
 * @author Hoody
 */
@Configuration
@EnableConfigurationProperties
public class ConfigurationPropertiesAutoConfiguration {

    public ConfigurationPropertiesAutoConfiguration() {

    }

    /**
     * 当没有注入 IWechatMsgHandler 处理器时,注入默认消息处理器
     *
     * @return IWechatMsgHandler default implement
     */
    @ConditionalOnMissingBean
    @Bean
    public IWechatMsgHandler getIWechatMsgHandler() {
        return new DefaultWechatMsgHandler();
    }

    /**
     * 当没有注入 IWechatEventHandler 处理器时,注入默认消息处理器
     *
     * @return IWechatEventHandler default implement
     */
    @ConditionalOnMissingBean
    @Bean
    public IWechatEventHandler getIWechatEventHandler() {
        return new DefaultEventHandler();
    }
}
