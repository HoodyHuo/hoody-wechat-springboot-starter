# hoody-wechat-springboot-starter

# 概述
> 个人学习微信公众号开发,所以自己做的  
基于`springboot-starter`的微信公众号工具  

## 功能:  
- 接收微信公众号收到的的用户消息
- 自定义公众号菜单
- 公众号事件处理
- 提供响应信息模板,向用户发送信息
- 管理微信access_token
- 提供微信服务器验证


## 安装

>没有远程仓库,所以需要本地编译后再添加依赖

### 1.Maven 下载源码
源码目录执行 安装
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

## 功能

### 1.处理用户发送的信息
实现`vip.hoody.wechat.handler.IWechatMsgHandler`接口并注入  
要向用户返回信息使用`vip.hoody.wechat.domain.reply` 包 下的类型作为返回值 

### 2.处理微信事件
事件包括:
 - 用户事件(比如订阅,取消订阅,扫码等)
 - 自定义菜单事件(点击,发送图片,定位等)  

实现`vip.hoody.wechat.handler.IWechatEventHandler`接口并注入  
要向用户返回信息使用`vip.hoody.wechat.domain.reply` 包 下的类型作为返回值  
PS: 部分方法微信接口不支持返回消息给用户  

### 3.自定义菜单管理
注入并使用接口 `vip.hoody.wechat.api.IMenuApi`
```java
@Autowired
IMenuApi menuApi;
```

示例:
```java
import vip.hoody.wechat.domain.menu.Menu;
import vip.hoody.wechat.domain.menu.button.*;

@RestController
@RequestMapping("menu")
class MenuController {

    @Autowired
    IMenuApi menuAPI;

    /** 创建菜单,3个子菜单 */
    @GetMapping("create")
    String createMenu() {
        Menu menu = new Menu();
        SubButton subButton1 = new SubButton("更多1..");
        SubButton subButton2 = new SubButton("更多2..");
        SubButton subButton3 = new SubButton("更多3..");

        ScanCodePushButton scanCodePushButton = new ScanCodePushButton("ScanCodePush", "ScanCodePush");
        ScanCodeWaitMsgButton waitMsgButton = new ScanCodeWaitMsgButton("ScanCodeWaitMsg", "ScanCodeWaitMsg");
        LocationSelectButton selectButtonl = new LocationSelectButton("LocationSelect", "LocationSelect");
        MediaButton mediaButton = new MediaButton("MediaButton", "4MOS0SN34wAVT4YDGkD2YL7fQqz3i4xNAfLlOlKsot8");
        PicPhotoOrAlbumButton photoOrAlbumButton = new PicPhotoOrAlbumButton("PicSysPhoto", "PicSysPhoto");
        subButton1.addButton(scanCodePushButton);
        subButton1.addButton(waitMsgButton);
        subButton1.addButton(selectButtonl);
        subButton1.addButton(mediaButton);
        subButton1.addButton(photoOrAlbumButton);

        PicSysPhotoButton picSysPhotoButton = new PicSysPhotoButton("PicSysPhoto", "PicSysPhoto");
        PicWeixinButton picWeixinButton = new PicWeixinButton("PicWeixin", "PicWeixin");
        subButton2.addButton(picSysPhotoButton);
        subButton2.addButton(picWeixinButton);

        ClickButton clickButton = new ClickButton("click", "clickkey");
        ViewButton viewButton = new ViewButton("view", "http://hoody.vip");
        subButton3.addButton(clickButton);
        subButton3.addButton(viewButton);

        menu.addButton(subButton1);
        menu.addButton(subButton2);
        menu.addButton(subButton3);
        def json = menuAPI.createMenu(menu);
        return "success";
    }

    /** 删除所有菜单 */
    @PostMapping("delete")
    ResponseData deleteMenu() {
        menuAPI.removeMenu();
        return new ResponseData();
    }

    /** 获取当前自定义菜单*/
    @GetMapping("get")
    ResponseData getMenu() {
        Menu menu = menuAPI.getMenu();
        return menu.toParam();
    }
}
```


### 4.素材管理

上示例:
```groovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vip.hoody.pi.util.ResponseData
import vip.hoody.wechat.api.IMediaApi
import vip.hoody.wechat.domain.media.MediaNewsPage
import vip.hoody.wechat.domain.media.MediaOtherPage
import vip.hoody.wechat.domain.media.MediaType
import vip.hoody.wechat.domain.media.NewsItem

import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/media")
class MediaControler {

    @Autowired
    IMediaApi mediaAPI

    //----------------------------------------临时素材--------------------------------------
    /**vSMp0P4U4Wj92sZe6zO8Fc0guGdzNQBwm9a0_zxuGGf5IAng0XcJLZ2e26gT7dtE 图片
     * 上传临时素材
     * @param filePath
     * @param type
     * @return
     */
    @PostMapping("temporary")
    ResponseData uploadTemporaryMedia(@RequestParam("filePath") String filePath, @RequestParam("type") String type) {
        Map<String, String> result = mediaAPI.uploadTemporaryMedia(filePath, MediaType.get(type))
        return new ResponseData(data: ["type"      : result.get("type"),
                                       "media_id"  : result.get("media_id"),
                                       "created_at": result.get("created_at")])
    }

    /**
     * 下载临时素材
     * vSMp0P4U4Wj92sZe6zO8Fc0guGdzNQBwm9a0_zxuGGf5IAng0XcJLZ2e26gT7dtE 临时图片素材
     * @return
     */
    @GetMapping("temporary")
    void getTemporaryMedia(@RequestParam("mediaId") String mediaId, HttpServletResponse response) {
        File file = mediaAPI.getTemporaryMedia(mediaId)
        response.setHeader("Content-Disposition", "attachment; filename=${file.getName()}")
        response.setHeader("content-length", "${file.getBytes().length}")
        response.getOutputStream().write(file.getBytes())
    }

    //----------------------------------------永久素材--------------------------------------

    /**
     * 新增永久视频素材
     * 4MOS0SN34wAVT4YDGkD2YARrNmfsa-NpfBkPu_kwuKk
     * 4MOS0SN34wAVT4YDGkD2YGEmqtwWn_anc6nv9UqBspM
     * @param filePath
     * @param type
     * @return
     */
    @PostMapping("video")
    ResponseData uploadVideoMedia(@RequestParam("filePath") String filePath) {
        String mediaId = mediaAPI.uploadVideoMedia(filePath, "接口检查title", "就是接口检查")
        return new ResponseData(data: mediaId)

    }

    /**
     * 新增其他永久素材
     * 4MOS0SN34wAVT4YDGkD2YElAHebVa9LuNhYcoX4c5-w 图片
     *
     * @param filePath
     * @return
     */
    @PostMapping("/")
    ResponseData uploadMedia(@RequestParam("filePath") String filePath, @RequestParam("type") String type) {
        String mediaId = mediaAPI.uploadMedia(filePath, MediaType.get(type))
        return new ResponseData(data: mediaId)
    }

    /**
     * 新增永久图文素材
     * 4MOS0SN34wAVT4YDGkD2YJmsSQIzHGLpt4NQCVkL77A 单条
     * 4MOS0SN34wAVT4YDGkD2YL7fQqz3i4xNAfLlOlKsot8 2条
     * todo
     * @param content
     * @return
     */
    @PostMapping("news")
    ResponseData uploadNews() {
        List<NewsItem> list = new ArrayList<NewsItem>()

        list.add(new NewsItem(
                "the 3nd title",
                "4MOS0SN34wAVT4YDGkD2YA7luBtYZbOwlw6Ik1SflMQ",
                "1",
                """<h1>我是标题</h1><b>我是内容</b><img src=" http://mmbiz.qpic.cn/mmbiz_jpg/kaMgW96icDyggoE8QZztJO0TBIfYUEPa7zHzOWRE1Uibr2PT5xHgg1CicSy6QiaYgQj6r91iaHJHoCf472icDMUateNw/0"/> """,
                ""
        ))
        list.add(new NewsItem(
                "the 4th title",
                "4MOS0SN34wAVT4YDGkD2YA7luBtYZbOwlw6Ik1SflMQ",
                "1",
                """<h1>我是标题2</h1><b>我是222内容</b><img src="http://mmbiz.qpic.cn/mmbiz_jpg/kaMgW96icDyggoE8QZztJO0TBIfYUEPa7zHzOWRE1Uibr2PT5xHgg1CicSy6QiaYgQj6r91iaHJHoCf472icDMUateNw/0"/> """,
                ""
        ))
        String mediaId = mediaAPI.uploadNewsMedia(list)
        return new ResponseData(data: mediaId)
    }

    @PostMapping("news/update")
    ResponseData updateNews() {
//
        NewsItem item = new NewsItem(
                "the 3th G2",
                "4MOS0SN34wAVT4YDGkD2YA7luBtYZbOwlw6Ik1SflMQ",
                "1",
                """<h1>我是标题updateNews</h1>updateNews<b>我是222内容</b>updateNews<img src="http://mmbiz.qpic.cn/mmbiz_jpg/kaMgW96icDyggoE8QZztJO0TBIfYUEPa7zHzOWRE1Uibr2PT5xHgg1CicSy6QiaYgQj6r91iaHJHoCf472icDMUateNw/0"/> updateNews""",
                ""
        )

        mediaAPI.updateMedia(
                "4MOS0SN34wAVT4YDGkD2YL7fQqz3i4xNAfLlOlKsot8",
                item,
                "0"
        )
        return new ResponseData()
    }

    /**
     *  上传图文素材使用的图片
     * http://mmbiz.qpic.cn/mmbiz_jpg/kaMgW96icDyggoE8QZztJO0TBIfYUEPa7zHzOWRE1Uibr2PT5xHgg1CicSy6QiaYgQj6r91iaHJHoCf472icDMUateNw/0
     * @param filePath 文件路径
     * @return 访问url
     */
    @PostMapping("news/image")
    ResponseData uploadNewsImage(@RequestParam("fileName") String filePath) {
        String url = mediaAPI.uploadNewsImg(filePath)
        return new ResponseData(data: url)
    }

    /**
     * 获取永久图文素材
     * 4MOS0SN34wAVT4YDGkD2YJmsSQIzHGLpt4NQCVkL77A 单条
     * 4MOS0SN34wAVT4YDGkD2YKrmvATx07oxVJ5vGIdYAj0 2条
     * @param mediaId
     * @return todo
     */
    @GetMapping("news")
    ResponseData getNews(@RequestParam("mediaId") String mediaId) {
        List<NewsItem> a = mediaAPI.getNewsMedia(mediaId)
        return new ResponseData(data: a)
    }
    /**
     * 下载永久素材
     * 4MOS0SN34wAVT4YDGkD2YA7luBtYZbOwlw6Ik1SflMQ
     * 4MOS0SN34wAVT4YDGkD2YPr62P1eDk8MQQdVhhhEGkY 图
     * 4MOS0SN34wAVT4YDGkD2YAP6cxXo2Vu-yOi1rzsyaAs
     * 4MOS0SN34wAVT4YDGkD2YARrNmfsa-NpfBkPu_kwuKk 视频
     * @param mediaId
     */
    @GetMapping("/")
    void getMedia(@RequestParam("mediaId") String mediaId, HttpServletResponse response) {
        File file = mediaAPI.getMedia(mediaId)
        response.setHeader("Content-Disposition", "attachment; filename=${file.getName()}")
        response.setHeader("content-length", "${file.getBytes().length}")
        response.getOutputStream().write(file.getBytes())
    }

    /**
     * 删除永久素材
     * @param mediaId
     * @return
     */
    @PostMapping("delete")
    ResponseData deleteMedia(@RequestParam("mediaId") String mediaId) {
        mediaAPI.deleteMedia(mediaId)
        return new ResponseData()
    }

    /**
     * 获取永久素材数量统计
     * @return
     */
    @GetMapping("count")
    ResponseData getMediaCount() {
        Map<String, Integer> counts = mediaAPI.getMediaCount()
        return new ResponseData(data: counts)
    }

    /**
     * 获取指定类型永久素材列表 分页
     * @param type
     * @param offset
     * @param count
     * @return
     */
    @GetMapping("list")
    ResponseData getMediaList(String type, Integer offset, Integer count) {
        MediaOtherPage a = mediaAPI.getMediaOtherList(MediaType.get(type), offset, count)
        return new ResponseData(data: a)
    }

    /**
     * 获取图文类型永久素材列表 分页
     * @param offset
     * @param count
     * @return
     */
    @GetMapping("news_list")
    ResponseData getNewsMediaList(Integer offset, Integer count) {
        MediaNewsPage a = mediaAPI.getMediaNewsList(offset, count)
        return new ResponseData(data: a)
    }
}

```

# 待续

 感谢看到这里,过程中我的其他体会在[Hoody's blog](http://hoody.vip)