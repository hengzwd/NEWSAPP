package newsemc.com.awit.news.newsemcapp.bean;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.dao.DepartsInfo;
import newsemc.com.awit.news.newsemcapp.dao.LoginInfo;


/**
 * Created by Administrator on 2015/7/3.
 */
public class UserList {
    private List<LoginInfo> loginInfoList;
    private List<DepartsInfo> departsInfoList;

    public List<LoginInfo> getLoginInfoList() {
        return loginInfoList;
    }

    public void setLoginInfoList(List<LoginInfo> loginInfoList) {
        this.loginInfoList = loginInfoList;
    }

    public List<DepartsInfo> getDepartsInfoList() {
        return departsInfoList;
    }

    public void setDepartsInfoList(List<DepartsInfo> departsInfoList) {
        this.departsInfoList = departsInfoList;
    }
}
