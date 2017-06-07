package xin.fallen.ProjUsedVeh.domain;

/**
 * Author: Fallen
 * Date: 2017/6/5
 * Time: 11:08
 * Usage:预上传
 */
public class Seller {
    private String xszbh;
    private String type;
    private String url_wan;
    private String url_lan;
    private String disk_path;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getXszbh() {
        return xszbh;
    }

    public void setXszbh(String xszbh) {
        this.xszbh = xszbh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl_wan() {
        return url_wan;
    }

    public void setUrl_wan(String url_wan) {
        this.url_wan = url_wan;
    }

    public String getUrl_lan() {
        return url_lan;
    }

    public void setUrl_lan(String url_lan) {
        this.url_lan = url_lan;
    }

    public String getDisk_path() {
        return disk_path;
    }

    public void setDisk_path(String disk_path) {
        this.disk_path = disk_path;
    }
}