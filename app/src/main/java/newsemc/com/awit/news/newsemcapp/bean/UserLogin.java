package newsemc.com.awit.news.newsemcapp.bean;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfo;
import newsemc.com.awit.news.newsemcapp.dao.PersonInfo;


/**
 * Created by Administrator on 2015/7/3.
 */
public class UserLogin {
    private PersonInfo personInfobj;
    private List<DepartmentInfo> departmentInfoList;

    public PersonInfo getPersonInfobj() {
        return personInfobj;
    }

    public void setPersonInfobj(PersonInfo personInfobj) {
        this.personInfobj = personInfobj;
    }

    public List<DepartmentInfo> getDepartmentInfoList() {
        return departmentInfoList;
    }

    public void setDepartmentInfoList(List<DepartmentInfo> departmentInfoList) {
        this.departmentInfoList = departmentInfoList;
    }
}
