package vip.hoody.wechat.api;

import vip.hoody.wechat.domain.user.OpenIdListPage;
import vip.hoody.wechat.domain.user.UserInfo;
import vip.hoody.wechat.exception.user.WechatUserInfoException;
import vip.hoody.wechat.utils.Lang;

import java.util.List;

/**
 * 在关注者与公众号产生消息交互后，
 * 公众号可获得关注者的OpenID
 * （加密后的微信号，每个用户对每个公众号的OpenID是唯一的。
 * 对于不同公众号，同一用户的openid不同）。
 * 公众号可通过本接口来根据OpenID获取用户基本信息，
 * 包括昵称、头像、性别、所在城市、语言和关注时间。
 * <p>
 * UnionID机制说明：
 * <p>
 * 需要前往微信开放平台（open.weixin.qq.com）绑定公众号后，才可利用UnionID机制
 * 特别需要注意的是，如果开发者拥有多个移动应用、网站应用和公众帐号
 * ，可通过获取用户基本信息中的unionid来区分用户的唯一性，
 * 因为只要是同一个微信开放平台帐号下的移动应用、网站应用和公众帐号，
 * 用户的unionid是唯一的。
 * 换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。
 */
public interface IUserInfoApi {

    /**
     * 设置用户别名
     *
     * @param userOpenId 用户OpenID
     * @param aliasName  新别名
     * @throws WechatUserInfoException
     */
    void setAliasName(String userOpenId, String aliasName) throws WechatUserInfoException;

    /**
     * 获取用户基本信息
     *
     * @param openId 用户ID
     * @param lang   返回语言
     * @return
     * @throws WechatUserInfoException
     */
    UserInfo getUserInfo(String openId, Lang lang) throws WechatUserInfoException;

    /**
     * 获取用户列表
     * 一次拉取调用最多拉取10000个关注者的OpenID，
     * 可以通过多次拉取的方式来满足需求。
     *
     * @return
     * @throws WechatUserInfoException
     */
    OpenIdListPage getUserList() throws WechatUserInfoException;

    /**
     * 获取用户列表
     *
     * @param nextOpenId 从这个用户开始获取
     * @return
     * @throws WechatUserInfoException
     */
    OpenIdListPage getUserList(String nextOpenId) throws WechatUserInfoException;


    /**
     * 获取用户基本信息
     *
     * @param openId 用户ID
     * @return
     * @throws WechatUserInfoException
     */
    UserInfo getUserInfo(String openId) throws WechatUserInfoException;

    /**
     * 批量获取用户基本信息
     *
     * @param openIds 用户ID
     * @return
     * @throws WechatUserInfoException
     */
    List<UserInfo> getUserInfoList(List<String> openIds) throws WechatUserInfoException;

    /**
     * 批量获取用户基本信息
     *
     * @param openIds 用户ID
     * @param lang    信息语言
     * @return
     * @throws WechatUserInfoException
     */
    List<UserInfo> getUserInfoList(List<String> openIds, Lang lang) throws WechatUserInfoException;

    /**
     * 拉黑用户
     *
     * @param openIds 用户id 最多20个
     * @throws WechatUserInfoException
     */
    void addBlacklist(List<String> openIds) throws WechatUserInfoException;

    /**
     * 取消拉黑
     *
     * @param openIds 用户id 最多20个
     * @throws WechatUserInfoException
     */
    void removeBlacklist(List<String> openIds) throws WechatUserInfoException;


    /**
     * 获取黑名单
     * 该接口每次调用最多可拉取 10000 个OpenID
     *
     * @return
     * @throws WechatUserInfoException
     */
    OpenIdListPage getBlacklist() throws WechatUserInfoException;

    /**
     * 获取黑名单
     * 该接口每次调用最多可拉取 10000 个OpenID
     *
     * @param nextOpenid 从这个id 开始拉取
     * @return
     * @throws WechatUserInfoException
     */
    OpenIdListPage getBlacklist(String nextOpenid) throws WechatUserInfoException;
}
