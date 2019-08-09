package vip.hoody.wechat.domain.reply

class ReplyNewsMsg extends ReplyBaseMsg {
    private List<ArticleItem> Articles

    /**
     *
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param fromUserName 开发者微信号
     * @param createTime 消息创建时间 （整型）
     * @param msgType 消息类型，图文为news
     * @param articles 当用户发送文本、图片、视频、图文、地理位置这五种消息时，
     *                  开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
     */
    ReplyNewsMsg(String toUserName, String fromUserName, String createTime, String msgType, List<ArticleItem> articles) {
        super(toUserName, fromUserName, createTime, msgType)
        Articles = articles
    }

    boolean addArticleItem(ArticleItem item) {
        return this.Articles.add(item)
    }

    boolean addAllArticleItem(List<ArticleItem> items) {
        return this.Articles.addAll(items)
    }

    private getArticlesXmlStr() {
        StringBuffer buffer = new StringBuffer()
        this.Articles.each {
            buffer.append(it.toXml())
        }
        return buffer.toString()
    }

    @Override
    String toXml() {
        return """
<xml>
  <ToUserName>${this.toUserName}</ToUserName>
  <FromUserName>${this.fromUserName}</FromUserName>
  <CreateTime>${this.createTime}</CreateTime>
  <MsgType>${this.msgType}</MsgType>
  <ArticleCount>${this.Articles.size()}</ArticleCount>
  <Articles>
  ${this.getArticlesXmlStr()}
  </Articles>
</xml>
"""
    }
}