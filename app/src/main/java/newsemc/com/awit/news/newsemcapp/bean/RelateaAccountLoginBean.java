package newsemc.com.awit.news.newsemcapp.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/2/13.
 * Email：hengzwdhengzwd@qq.com
 */

public class RelateaAccountLoginBean {


    /**
     * data : {"list":[{"account":"shjnadd","compid":"14091","compname":"宁安铁路有限责任公司","contact":"1333333333","departments":[{"duty":"宁安铁路有限责任公司","id":14091,"name":"宁安铁路有限责任公司"}],"idNo":"","name":"宁安铁路有限责任公司调度","relateAccount":"nagsgcbxm,cky","sex":"男","ssid":"APP-ec1de907-93bb-472c-adbb-fd6e5ed0639f","switchers":[{"account":"nagsgcbxm","deptname":"京沈铁路客运专线辽宁有限责任公司-->工程管理部","username":"徐 敏"},{"account":"cky","deptname":"宁安铁路有限责任公司-->工程调度","username":"陈坤友"}],"userId":1553}]}
     * msg : 成功
     * status : 0
     */

    private DataEntity data;
    private String msg;
    private String status;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class DataEntity {
        private List<ListEntity> list;

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity {
            /**
             * account : shjnadd
             * compid : 14091
             * compname : 宁安铁路有限责任公司
             * contact : 1333333333
             * departments : [{"duty":"宁安铁路有限责任公司","id":14091,"name":"宁安铁路有限责任公司"}]
             * idNo :
             * name : 宁安铁路有限责任公司调度
             * relateAccount : nagsgcbxm,cky
             * sex : 男
             * ssid : APP-ec1de907-93bb-472c-adbb-fd6e5ed0639f
             * switchers : [{"account":"nagsgcbxm","deptname":"京沈铁路客运专线辽宁有限责任公司-->工程管理部","username":"徐 敏"},{"account":"cky","deptname":"宁安铁路有限责任公司-->工程调度","username":"陈坤友"}]
             * userId : 1553
             */

            private String account;
            private String compid;
            private String compname;
            private String contact;
            private String idNo;
            private String name;
            private String relateAccount;
            private String sex;
            private String ssid;
            private int userId;
            private List<DepartmentsEntity> departments;
            private List<SwitchersEntity> switchers;

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getCompid() {
                return compid;
            }

            public void setCompid(String compid) {
                this.compid = compid;
            }

            public String getCompname() {
                return compname;
            }

            public void setCompname(String compname) {
                this.compname = compname;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRelateAccount() {
                return relateAccount;
            }

            public void setRelateAccount(String relateAccount) {
                this.relateAccount = relateAccount;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getSsid() {
                return ssid;
            }

            public void setSsid(String ssid) {
                this.ssid = ssid;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public List<DepartmentsEntity> getDepartments() {
                return departments;
            }

            public void setDepartments(List<DepartmentsEntity> departments) {
                this.departments = departments;
            }

            public List<SwitchersEntity> getSwitchers() {
                return switchers;
            }

            public void setSwitchers(List<SwitchersEntity> switchers) {
                this.switchers = switchers;
            }

            public static class DepartmentsEntity {
                /**
                 * duty : 宁安铁路有限责任公司
                 * id : 14091
                 * name : 宁安铁路有限责任公司
                 */

                private String duty;
                private int id;
                private String name;

                public String getDuty() {
                    return duty;
                }

                public void setDuty(String duty) {
                    this.duty = duty;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class SwitchersEntity {
                /**
                 * account : nagsgcbxm
                 * deptname : 京沈铁路客运专线辽宁有限责任公司-->工程管理部
                 * username : 徐 敏
                 */

                private String account;
                private String deptname;
                private String username;

                public String getAccount() {
                    return account;
                }

                public void setAccount(String account) {
                    this.account = account;
                }

                public String getDeptname() {
                    return deptname;
                }

                public void setDeptname(String deptname) {
                    this.deptname = deptname;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
            }
        }
    }
}
