package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

/**
 * Created by leguang on 2016/9/22 0022.
 * 联系邮箱:langmanleguang@qq.com
 */
public class LoginUserBean {

    /**
     * DT : 2016-09-22 09:43:38
     * Description : Success
     * Result : 0
     * Token : 81583BFC11F1EE480A8604A4EF355D1CAE8DE5768DD90D6BD56BE1B971861876B3A25A8C1EEA959B
     */

    private String DT;
    private String Description;
    private int Result;
    private String Token;

    public void setDT(String DT) {
        this.DT = DT;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getDT() {
        return DT;
    }

    public String getDescription() {
        return Description;
    }

    public int getResult() {
        return Result;
    }

    public String getToken() {
        return Token;
    }
}
