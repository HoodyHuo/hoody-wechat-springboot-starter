# hoody-wechat-springboot-starter

# 概述
> 为了方便个人使用  
基于`springboot-starter`的微信公众号工具  

## 功能:  
- 接收微信公众号转发的用户消息
- 提供微信接收/响应信息的xml消息解析
- 提供响应信息模板
- 管理微信access_token
- 提供微信服务器验证

## 使用

>没有远程仓库,所以需要本地编译后再添加依赖

### 1.Maven 下载源码
执行 安装
```cmd
   mvnw install 
```
### 2.引入依赖
`pom.xml`
```xml
    <dependencies>
       <dependency>
            <groupId>vip.hoody</groupId>
            <artifactId>wechat</artifactId>
            <version>version</version>
        </dependency>
    </dependencies>
```
 
### 3.配置微信开发者信息
`application.yml`
```yml
#微信开发者信息
hoody:
  wechat:
    app-id: 'your appid'
    app-secret: 'your appSecret'
    token: 'abc'   #微信服务器配置的令牌(Token),个人随便填的那个
    token-rate: 7200  # 单位: 秒 access_token refreash rate(default:7200)
    url: 'your url' #微信填写的服务器地址 eg. /wechat
``` 

### 4.实现`vip.hoody.wechat.handler.IWechatMsgHandler`接口

实现接口并注入Spring,响应收到的微信信息,  
`WechatHandlerImpl.java`
```java
@Component
public class WechatMsgHandler implements IWechatHandler {

    @Override
    public ReplyBaseMsg handle(ReceivedTextMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:图片信息");
    }

    @Override
    public ReplyBaseMsg handle(ReceivedImageMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:图片信息");
    }

    @Override
    public ReplyBaseMsg handle(ReceivedLinkMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:图链接息");
    }

    @Override
    public ReplyBaseMsg handle(ReceivedLocationMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:定位信息");
    }

    @Override
    public ReplyBaseMsg handle(ReceivedShortVideoMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:小视频信息");
    }

    @Override
    public ReplyBaseMsg handle(ReceivedVideoMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:视频信息");
    }

    @Override
    public ReplyBaseMsg handle(ReceivedVoiceMsg msg) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), "你在发是:音频信息");
    }

}
```

### 5.返回信息给用户
所有返回信息继承`ReplyBaseMsg`,  
可通过`WeChatFactory`创建返回信息

## 待续
1.上传/下载 微信多媒体信息  
2.完善 `WeChatFactory`  
