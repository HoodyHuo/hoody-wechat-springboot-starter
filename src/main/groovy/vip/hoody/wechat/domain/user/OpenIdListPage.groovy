package vip.hoody.wechat.domain.user

import com.alibaba.fastjson.annotation.JSONField

/**
 * 用户列表和黑名单的返回值
 */
class OpenIdListPage {
    /**
     * 总计数
     */
    private Long total
    /**
     * 本次拉取数量
     */
    private Long count
    /**
     * 最后一个ID值,用于多次拉取时,下一次拉取的参数
     */
    private String nextOpenId
    /**
     * 用户ID列表
     */
    private List<String> openIdList

    Long getTotal() {
        return total
    }

    void setTotal(Long total) {
        this.total = total
    }

    Long getCount() {
        return count
    }

    void setCount(Long count) {
        this.count = count
    }

    String getNextOpenId() {
        return nextOpenId
    }

    void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId
    }

    List<String> getOpenIdList() {
        if (openIdList == null) {
            openIdList = new ArrayList<String>()
        }
        return openIdList
    }

    @JSONField(name = "data")
    void setJsonOpenList(Map<String, List> openIdList) {
        this.openIdList = openIdList.openid
    }

    void setOpenIdList(List<String> openIdList) {
        this.openIdList = openIdList
    }


    @Override
    public String toString() {
        return "OpenIdListPage{" +
                "total=" + total +
                ", count=" + count +
                ", nextOpenId='" + nextOpenId + '\'' +
                ", openIdList=" + openIdList +
                '}';
    }
}
