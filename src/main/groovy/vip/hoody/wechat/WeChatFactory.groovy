package vip.hoody.wechat

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import vip.hoody.wechat.domain.event.BaseEvent
import vip.hoody.wechat.domain.event.LocationEvent
import vip.hoody.wechat.domain.event.menu.ClickEvent
import vip.hoody.wechat.domain.event.menu.LocationSelectEvent
import vip.hoody.wechat.domain.event.menu.PicPhotoOrAlbumEvent
import vip.hoody.wechat.domain.event.menu.PicSysPhotoEvent
import vip.hoody.wechat.domain.event.menu.PicWeixinEvent
import vip.hoody.wechat.domain.event.menu.ScanCodePushEvent
import vip.hoody.wechat.domain.event.menu.ScanCodeWaitEvent
import vip.hoody.wechat.domain.event.ScanQREvent
import vip.hoody.wechat.domain.event.ScanQRUnSubEvent
import vip.hoody.wechat.domain.event.SubscribeEvent
import vip.hoody.wechat.domain.event.menu.ViewEvent
import vip.hoody.wechat.domain.received.*
import vip.hoody.wechat.domain.reply.ReplyTextMsg
import vip.hoody.wechat.config.WechatConfig
import vip.hoody.wechat.exception.WechatException
import vip.hoody.wechat.exception.WechatParseXmlException

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class WeChatFactory {
    private static final Logger log = LoggerFactory.getLogger(this.class)

    static Object getWechatReceivedMsg(InputStream xml) {
        Map<String, String> params = parseXml(xml)
        if (params.get("MsgType") == null) {
            throw new WechatParseXmlException("xml错误:${xml} \n 转换结果: ${params.toString()}")
        }
        switch (params.MsgType) {
        /** 文字消息 */
            case WechatConfig.REQ_MESSAGE_TYPE_TEXT:
                ReceivedTextMsg msg = new ReceivedTextMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.MsgId,
                        params.Content,
                )
                return msg
                break
        /** 图片信息 */
            case WechatConfig.REQ_MESSAGE_TYPE_IMAGE:
                ReceivedImageMsg msg = new ReceivedImageMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.MsgId,
                        params.PicUrl,
                        params.MediaId,
                )
                return msg
                break
        /** 语音消息 */
            case WechatConfig.REQ_MESSAGE_TYPE_VOICE:
                ReceivedVoiceMsg msg = new ReceivedVoiceMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.MsgId,
                        params.MediaId,
                        params.Format,
                )
                return msg
                break
        /** 视频消息 */
            case WechatConfig.REQ_MESSAGE_TYPE_VIDEO:
                ReceivedVideoMsg msg = new ReceivedVideoMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.MsgId,
                        params.MediaId,
                        params.ThumbMediaId,
                )
                return msg
                break
        /** 理位置消息*/
            case WechatConfig.REQ_MESSAGE_TYPE_LOCATION:
                ReceivedLocationMsg msg = new ReceivedLocationMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.MsgId,
                        params.Location_X,
                        params.Location_Y,
                        params.Scale,
                        params.Label,
                )
                return msg
                break
        /** 链接消息 */
            case WechatConfig.REQ_MESSAGE_TYPE_LINK:
                ReceivedLinkMsg msg = new ReceivedLinkMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.MsgId,
                        params.Title,
                        params.Description,
                        params.Url,
                )
                return msg
                break
        /** 事件推送 */
            case WechatConfig.REQ_MESSAGE_TYPE_EVENT:
                return eventDispatch(params)
                break
            default:
                throw new WechatException("未知类型消息:${params.MsgType} 消息原文:${params}")
        }
    }

    /**
     * 根据Event 类型创建Event 对象
     * @param params
     * @return
     */
    static BaseEvent eventDispatch(Map<String, String> params) {
        switch (params.Event) {
        /** 订阅事件 */
            case WechatConfig.EVENT_TYPE_SUBSCRIBE:
                /** 未关注扫描也会先触发订阅事件 */
                if (params.Ticket != null) {
                    return new ScanQRUnSubEvent(
                            params.ToUserName,
                            params.FromUserName,
                            params.CreateTime,
                            params.MsgType,
                            params.Event,
                            params.EventKey,
                            params.Ticket
                    )
                }
                /** 订阅事件 */
                else {
                    return new SubscribeEvent(
                            params.ToUserName,
                            params.FromUserName,
                            params.CreateTime,
                            params.MsgType,
                            params.Event
                    )
                }
                break
        /** 取消订阅事件 */
            case WechatConfig.EVENT_TYPE_UNSUBSCRIBE:
                return new ScanQRUnSubEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey,
                        params.Ticket
                )
                break
        /** 已订阅用户扫码事件 */
            case WechatConfig.EVENT_TYPE_SCAN:
                return new ScanQREvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey,
                        params.Ticket
                )
                break
        /** 用户上报定位事件 */
            case WechatConfig.EVENT_TYPE_LOCATION:
                return new LocationEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.Latitude,
                        params.Longitude,
                        params.Precision
                )
                break
        /** 用户菜单点击事件 */
            case WechatConfig.EVENT_TYPE_CLICK:
                return new ClickEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
                break
        /** 用户菜单网页进入事件 */
            case WechatConfig.EVENT_TYPE_VIEW:
                return new ViewEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
                break
        /**
         * 扫码推事件且弹出“消息接收中”提示框用户点击按钮后，
         * 微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，
         * 同时收起扫一扫工具，然后弹出“消息接收中”提示框，
         * 随后可能会收到开发者下发的消息。*/
            case WechatConfig.EVENT_TYPE_SCAN_CODE_PUSH:
                return new ScanCodePushEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
                break
        /**
         * 扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，
         * 完成扫码操作后显示扫描结果（如果是URL，将进入URL），
         * 且会将扫码的结果传给开发者，开发者可以下发消息
         */
            case WechatConfig.EVENT_TYPE_SCAN_CODE_WAIT:
                return new ScanCodeWaitEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
                break
        /**
         * 弹出地理位置选择器用户点击按钮后，
         * 微信客户端将调起地理位置选择工具，完成选择操作后，
         * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，
         * 随后可能会收到开发者下发的消息
         */
            case WechatConfig.EVENT_TYPE_LOCATION_SELECT:
                return new LocationSelectEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
            case WechatConfig.EVENT_TYPE_PIC_SYS_PHOTO:
                return new PicSysPhotoEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
            case WechatConfig.EVENT_TYPE_PIC_WEIXIN:
                return new PicWeixinEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
            case WechatConfig.EVENT_TYPE_PIC_SYS_OR_ALBUM:
                return new PicPhotoOrAlbumEvent(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                        params.EventKey
                )
            default:
                throw new WechatException("未知类型Event消息:${params.Event} 消息原文:${params}")
        }
    }

    static Map parseXml(InputStream xmlInStream) {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>()
        def parser = new XmlParser()
        // 读取
        Node doc = parser.parse(xmlInStream)
        // 遍历所有子节点
        doc.each { Node node ->
            map.put(node.name(), node.value()[0])
        }
        // 释放资源
        return map
    }


    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String accessToken, String signature, String timestamp, String nonce) {
        String[] arr = [accessToken, timestamp, nonce]
        // 将token、timestamp、nonce三个参数进行字典序排序
        // Arrays.sort(arr)
        sort(arr)
        StringBuilder content = new StringBuilder()
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i])
        }
        MessageDigest md = null
        String tmpStr = null

        try {
            md = MessageDigest.getInstance("SHA-1")
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes())
            tmpStr = byteToStr(digest)
        } catch (NoSuchAlgorithmException e) {
            throw new WechatException("验证微信返回签名异常", e)
            e.printStackTrace()
        }
        content = null
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = ""
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i])
        }
        return strDigest
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = [
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        ]
        char[] tempArr = new char[2]
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F]
        tempArr[1] = Digit[mByte & 0X0F]

        String s = new String(tempArr)
        return s
    }

    private static void sort(String[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i]
                    a[i] = a[j]
                    a[j] = temp
                }
            }
        }
    }

    /**
     * 创建回复的文本信息
     * @param msg
     * @param content
     * @return
     */
    static ReplyTextMsg createTextReply(ReceivedBaseMsg msg, String content) {
        return new ReplyTextMsg(msg.fromUserName, msg.toUserName, new Date().getTime().toString(), content)
    }
}
