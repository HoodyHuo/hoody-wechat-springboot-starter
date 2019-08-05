package vip.hoody.wechat

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import vip.hoody.wechat.bean.event.EventBaseMsg
import vip.hoody.wechat.bean.received.*
import vip.hoody.wechat.bean.reply.ReplyTextMsg
import vip.hoody.wechat.config.WechatConfig
import vip.hoody.wechat.exception.WechatException
import vip.hoody.wechat.exception.WechatParseXmlException

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class WeChatFactory {
    private static final Logger log = LoggerFactory.getLogger(this.class)

    static ReceivedBaseMsg getWechatReceivedMsg(InputStream xml) {
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
                EventBaseMsg msg = new EventBaseMsg(
                        params.ToUserName,
                        params.FromUserName,
                        params.CreateTime,
                        params.MsgType,
                        params.Event,
                )
                return msg
                break
            default:
                throw new WechatException("未知类型消息:${params.MsgType} 消息原文:${xml}")
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
