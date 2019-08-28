package vip.hoody.wechat.api;

import vip.hoody.wechat.domain.user.OpenIdListPage;
import vip.hoody.wechat.domain.user.Tag;
import vip.hoody.wechat.exception.user.WechatUserTagException;

import java.util.List;

/**
 * @author Hoody
 */
public interface IUserTagApi {

    /**
     * 创建标签
     *
     * @return new tag
     * @throws WechatUserTagException
     */
    Tag tagCreate(String tagName) throws WechatUserTagException;

    /**
     * 编辑标签
     *
     * @param tagId   标签ID
     * @param tagName 标签新名称
     * @return new tag
     * @throws WechatUserTagException
     */
    void tagUpdate(String tagId, String tagName) throws WechatUserTagException;

    /**
     * 获取公众号已创建的标签
     *
     * @return
     * @throws WechatUserTagException
     */
    List<Tag> tagList() throws WechatUserTagException;

    /**
     * 删除标签
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。
     * 此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，
     * 直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param tagId 标签ID
     * @throws WechatUserTagException
     */
    void tagDelete(String tagId) throws WechatUserTagException;

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId      标签ID
     * @param nextOpenId 起始用户ID
     * @return
     * @throws WechatUserTagException
     */
    OpenIdListPage getUserListByTag(String tagId, String nextOpenId) throws WechatUserTagException;

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId 标签ID
     * @return
     * @throws WechatUserTagException
     */
    OpenIdListPage getUserListByTag(String tagId) throws WechatUserTagException;

    /**
     * 批量为用户打标签
     *
     * @param tagId       标签ID
     * @param userOpenIds 用户openID 列表
     * @throws WechatUserTagException
     */
    void tagUsers(String tagId, List<String> userOpenIds) throws WechatUserTagException;

    /**
     * 为用户打标签
     *
     * @param tagId      标签ID
     * @param userOpenId 用户openID
     * @throws WechatUserTagException
     */
    void tagUsers(String tagId, String userOpenId) throws WechatUserTagException;

    /**
     * 批量为用户取消标签
     *
     * @param tagId       标签ID
     * @param userOpenIds 用户openID 列表
     * @throws WechatUserTagException
     */
    void unTagUsers(String tagId, List<String> userOpenIds) throws WechatUserTagException;

    /**
     * 为用户取消标签
     *
     * @param tagId      标签ID
     * @param userOpenId 用户openID
     * @throws WechatUserTagException
     */
    void unTagUsers(String tagId, String userOpenId) throws WechatUserTagException;

    /**
     * 获取用户身上的标签列表
     *
     * @param userOpenId 用户OpenID
     * @return tag id list
     * @throws WechatUserTagException
     */
    List<String> getTagsByUser(String userOpenId) throws WechatUserTagException;
}
