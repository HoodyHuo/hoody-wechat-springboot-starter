package vip.hoody.wechat.bean.reply

/**
 * 图文信息Item
 */
class ArticleItem {
    private String Title
    private String Description
    private String PicUrl
    private String Url

    /**
     *
     * @param title 图文消息标题
     * @param description 图文消息描述
     * @param picUrl 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
     * @param url 点击图文消息跳转链接
     */
    ArticleItem(String title, String description, String picUrl, String url) {
        Title = title
        Description = description
        PicUrl = picUrl
        Url = url
    }

    String toXml() {
        return """
<item>
  <Title>${this.Title}</Title>
  <Description>${this.Description}</Description>
  <PicUrl>${this.PicUrl}</PicUrl>
  <Url>${this.Url}</Url>
</item>
"""
    }
}
