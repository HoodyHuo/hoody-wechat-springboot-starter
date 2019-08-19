package vip.hoody.wechat.api.menu

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import vip.hoody.wechat.api.IMenuApi
import vip.hoody.wechat.api.WeChatApi
import vip.hoody.wechat.domain.menu.Menu
import vip.hoody.wechat.exception.WechatException;
import vip.hoody.wechat.utils.HttpUtil;

/**
 * @author Hoody* @since 2019年8月9日14:31:39
 * 自定义菜单接口
 * TODO 验证
 */
@Component
public class CustomMenuAPI implements IMenuApi {

    private static final String MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu"

    private RestTemplate restTemplate
    private WeChatApi weChatApi

    @Autowired
    CustomMenuAPI(RestTemplate restTemplate, WeChatApi weChatApi) {
        this.weChatApi = weChatApi
        this.restTemplate = restTemplate
    }

    /**
     * 提交自定义菜单到微信服务器
     *
     * @param menu 自定义菜单
     * @return request body
     */
    void createMenu(Menu menu) {
        String url = "${MENU_URL}/create?access_token=${weChatApi.accessToken}"
        String paramJSON = menu.toParam()
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
        HttpEntity<String> entity = new HttpEntity<String>(paramJSON, headers)
        Map<String, Object> result = restTemplate.postForObject(url, entity, Map.class)

        if (result?.errcode && result.errcode != 0) {
            throw new WechatException("create menu fail :${result.toString()}")
        }
    }

    /**
     * 自定义菜单查询接口
     *
     * @return 返回的菜单对象
     */
    public Menu getMenu() {
        String url = "${MENU_URL}/get?access_token=${weChatApi.accessToken}"
        Map<String, Object> result = restTemplate.getForObject(url, Map.class)

        if (result.errcode && result.errcode != 0) {
            throw new WechatException("get Menu fail :${result?.errmsg}")
        }
        Menu menu = Menu.parse(result.menu)
        return menu
    }

    /**
     * 自定义菜单删除接口
     * @return isDelete
     */
    public void removeMenu() {
        String url = "${MENU_URL}/delete?access_token=${weChatApi.accessToken}"
        Map<String, Object> result = restTemplate.getForObject(url, Map.class)
        if (result.errcode != 0) {
            throw new WechatException("delete Menu fail :${result?.errmsg}")
        }
    }
}
