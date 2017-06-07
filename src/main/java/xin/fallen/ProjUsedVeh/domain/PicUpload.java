package xin.fallen.ProjUsedVeh.domain;

/**
 * Author: PicUpload
 * Date: 2017/4/5
 * Time: 14:15
 * Usage:
 */
public class PicUpload {

    private String jylsh;
    private String type;
    private String token;
    private String pic;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getJylsh() {
        return jylsh;
    }

    public void setJylsh(String jylsh) {
        this.jylsh = jylsh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
