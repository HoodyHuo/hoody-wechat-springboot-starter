package vip.hoody.wechat.domain.user

/**
 * 用户信息展示对象
 * 通过微信接口获取用户详情时返回
 */
class UserInfo {
    /**
     * 用户是否订阅该公众号
     * 户没有关注该公众号，拉取不到其余信息。
     */
    private boolean subscribe
    /**
     * 	用户的标识，对当前公众号唯一
     */
    private String openId
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionId
    /**
     * 用户的昵称
     */
    private String nickName
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language
    /**
     * 用户所在城市
     */
    private String city
    /**
     * 用户所在省份
     */
    private String province
    /**
     * 用户所在国家
     */
    private String country

    /**
     * 用户头像，最后一个数值代表正方形头像大小
     * （有0、46、64、96、132数值可选，0代表640*640正方形头像），
     * 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String headimgurl

    /**
     * 用户关注时间，
     * 如果用户曾多次关注，则取最后关注时间
     */
    private Date subscribeTime

    /**
     * 公众号运营者对粉丝的备注，
     * 公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    private String remark

    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    private String group
    /**
     * 用户被打上的标签ID列表
     */
    private List<String> tagIdList

    /**
     * 返回用户关注的渠道来源，
     * ADD_SCENE_SEARCH 公众号搜索，
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
     * ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，
     * ADD_SCENEPROFILE LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
     * ADD_SCENE_PAID 支付后关注，
     * ADD_SCENE_OTHERS 其他
     */
    private String subscribeScene

    /**
     * 二维码扫码场景（开发者自定义）
     */
    private String qrScene
    /**
     * 二维码扫码场景描述（开发者自定义）
     */
    private String qrSceneStr

    /**
     *
     * @param subscribe 用户是否订阅该公众号
     * @param openId 用户的标识，对当前公众号唯一
     * @param unionId 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
     * @param nickName 用户的昵称
     * @param sex 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     * @param language 用户的语言，简体中文为zh _CN
     * @param city 用户所在城市
     * @param province 用户所在省份
     * @param country 用户所在国家
     * @param headimgurl 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     * @param subscribeTime 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     * @param remark 公众号运营者对粉丝的备注
     * @param group 用户所在的分组ID（兼容旧的用户分组接口）
     * @param tagIdList 用户被打上的标签ID列表
     * @param subscribeScene 返回用户关注的渠道来源
     * @param qrScene 二维码扫码场景（开发者自定
     * @param qrSceneStr 二维码扫码场景描述（开发者自定义）
     */
    UserInfo(boolean subscribe, String openId, String unionId, String nickName, String sex, String language, String city, String province, String country, String headimgurl, Date subscribeTime, String remark, String group, List<String> tagIdList, String subscribeScene, String qrScene, String qrSceneStr) {
        this.subscribe = subscribe
        this.openId = openId
        this.unionId = unionId
        this.nickName = nickName
        this.sex = sex
        this.language = language
        this.city = city
        this.province = province
        this.country = country
        this.headimgurl = headimgurl
        this.subscribeTime = subscribeTime
        this.remark = remark
        this.group = group
        this.tagIdList = tagIdList
        this.subscribeScene = subscribeScene
        this.qrScene = qrScene
        this.qrSceneStr = qrSceneStr
    }


    boolean getSubscribe() {
        return subscribe
    }

    void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe
    }

    String getOpenId() {
        return openId
    }

    void setOpenId(String openId) {
        this.openId = openId
    }

    String getUnionId() {
        return unionId
    }

    void setUnionId(String unionId) {
        this.unionId = unionId
    }

    String getNickName() {
        return nickName
    }

    void setNickName(String nickName) {
        this.nickName = nickName
    }

    String getSex() {
        return sex
    }

    void setSex(String sex) {
        this.sex = sex
    }

    String getLanguage() {
        return language
    }

    void setLanguage(String language) {
        this.language = language
    }

    String getCity() {
        return city
    }

    void setCity(String city) {
        this.city = city
    }

    String getProvince() {
        return province
    }

    void setProvince(String province) {
        this.province = province
    }

    String getCountry() {
        return country
    }

    void setCountry(String country) {
        this.country = country
    }

    String getHeadimgurl() {
        return headimgurl
    }

    void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl
    }

    Date getSubscribeTime() {
        return subscribeTime
    }

    void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime
    }

    String getRemark() {
        return remark
    }

    void setRemark(String remark) {
        this.remark = remark
    }

    String getGroup() {
        return group
    }

    void setGroup(String group) {
        this.group = group
    }

    List<String> getTagIdList() {
        return tagIdList
    }

    void setTagIdList(List<String> tagIdList) {
        this.tagIdList = tagIdList
    }

    String getSubscribeScene() {
        return subscribeScene
    }

    void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene
    }

    String getQrScene() {
        return qrScene
    }

    void setQrScene(String qrScene) {
        this.qrScene = qrScene
    }

    String getQrSceneStr() {
        return qrSceneStr
    }

    void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "subscribe=" + subscribe +
                ", openId='" + openId + '\'' +
                ", unionId='" + unionId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", remark='" + remark + '\'' +
                ", group='" + group + '\'' +
                ", tagIdList=" + tagIdList +
                ", subscribeScene='" + subscribeScene + '\'' +
                ", qrScene='" + qrScene + '\'' +
                ", qrSceneStr='" + qrSceneStr + '\'' +
                '}';
    }
}
