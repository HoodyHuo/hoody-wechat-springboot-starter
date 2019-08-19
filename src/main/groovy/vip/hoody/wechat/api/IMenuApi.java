package vip.hoody.wechat.api;

import vip.hoody.wechat.domain.menu.Menu;
import vip.hoody.wechat.exception.WechatMenuException;

/**
 * wechat custom menu api
 */
public interface IMenuApi {
    /**
     * create menu
     * 提交自定义菜单到微信服务器
     *
     * @param menu new menu
     * @throws WechatMenuException api error
     */
    void createMenu(Menu menu) throws WechatMenuException;

    /**
     * get menu structure
     * 自定义菜单查询接口
     *
     * @return menu structure
     * @throws WechatMenuException api error
     */
    Menu getMenu() throws WechatMenuException;

    /**
     * remove menu
     * 移除自定义菜单
     *
     * @throws WechatMenuException api error
     */
    void removeMenu() throws WechatMenuException;

}
