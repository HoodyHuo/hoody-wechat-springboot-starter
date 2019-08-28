package vip.hoody.wechat.domain.menu.button;

public class MiniProgrammaButton extends BaseButton {
    /**
     * 不支持小程序的老版本客户端将打开本url
     * 长度不超过1024字节。
     */
    private String url;

    /**
     * 小程序的appid（仅认证公众号可配置）
     */
    private String appid;

    /**
     * 小程序的页面路径小程序的页面路径
     */
    private String pagepath;

    public MiniProgrammaButton(String name, String url, String appid, String pagepath) {
        super(name, ButtonType.MINI_PROGRAM);
        this.url = url;
        this.appid = appid;
        this.pagepath = pagepath;
    }

    @Override
    String toParam() {
        return """
        {
             "type":"miniprogram",
             "name":"${this.name}",
             "url":"${this.url}",
             "appid":"${this.appid}",
             "pagepath":"${this.pagepath}"
        }
        """
    }

    @Override
    public String toString() {
        return "MiniProgrammaButton{" +
                "url='" + url + '\'' +
                ", appid='" + appid + '\'' +
                ", pagepath='" + pagepath + '\'' +
                super.toString() +
                "} "
    }
}
