package vip.hoody.wechat.api

import groovy.json.JsonSlurper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import vip.hoody.wechat.domain.menu.Menu
import vip.hoody.wechat.exception.WechatException;
import vip.hoody.wechat.utils.HttpUtil;

/**
 * @author Hoody* @since 2019年8月9日14:31:39
 * 自定义菜单接口
 */
@Component
public class CustomMenuAPI {

    private static final Logger log = LoggerFactory.getLogger(CustomMenuAPI.class)
    private static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu"

    private HttpUtil httpUtil
    private WeChatApi weChatApi
    private JsonSlurper jsonSlurper

    @Autowired
    CustomMenuAPI(HttpUtil httpUtil, WeChatApi weChatApi) {
        this.weChatApi = weChatApi
        this.httpUtil = httpUtil
        this.jsonSlurper = new JsonSlurper()
    }

    /**
     * 提交自定义菜单到微信服务器
     *
     * @param menu 自定义菜单
     * @return request body
     */
    public String createMenu(Menu menu) {
        String paramJSON = menu.toParam()
        Map result = httpUtil.doPostRequestWithJson(
                "${MENU_URL}/create?access_token=${weChatApi.accessToken}",
                paramJSON)
        if (result?.errcode && result.errcode != 0) {
            throw new WechatException("create menu fail :${result.toString()}")
        }
        return paramJSON
    }

    /**
     * 自定义菜单查询接口
     *
     * @return 返回的菜单对象
     */
    public Menu getMenu() {
        String result = httpUtil.doGetRequest(
                "${MENU_URL}/get?access_token=${weChatApi.accessToken}",
                null
        )
        Menu menu = Menu.parse(result)
        return menu
    }

    /**
     * 自定义菜单删除接口
     * @return isDelete
     */
    public void deletMenu() {
        String result = httpUtil.doGetRequest(
                "${MENU_URL}/delete?access_token=${weChatApi.accessToken}",
                null
        )
        def data = jsonSlurper.parseText(result)
        if (data.errcode != 0) {
            throw new WechatException("delete Menu fail :${data?.errmsg}")
        }
    }
}