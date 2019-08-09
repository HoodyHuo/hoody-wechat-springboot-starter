package vip.hoody.wechat.domain.menu.button;

/**
 * 网页按钮,点击后打开url指定页面
 */
public class ViewButton extends BaseButton {
    /**
     * 网页 链接，用户点击菜单可打开链接，不超过1024字节。
     */
    private String url;

    public ViewButton(String name, String url) {
        super(name, ButtonType.VIEW);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url
    }

    @Override
    String toParam() {
        return """
        {
            "name": "${this.name}",
            "type":"${this.type}",
            "url" : "${this.url}"
        }
        """
    }
}
