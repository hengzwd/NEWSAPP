package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.util.List;

/**
 * Created by leguang on 2016/9/23 0023.
 * 联系邮箱:langmanleguang@qq.com
 */
public class SurveyBean {

    /**
     * Description : Success
     * Result : 0
     * imageUrl1 : http://61.237.239.105:9090/gis/images/green.png
     * infolist : [{"key":"工程名称","value":"八幢山隧道"},{"key":"工程部位","value":"孝山明洞进口洞门"},{"key":"设计里程","value":"DK96+450~DK96+450"},{"key":"建设单位","value":"计财部"},{"key":"设计单位","value":"中铁第四勘察设计院集团有限公司"},{"key":"监理单位","value":"铁科院（北京）工程咨询有限公司"},{"key":"施工单位","value":"中国建筑股份有限公司"},null,{"key":"技术负责人","value":"刘世安"},{"key":"质量负责人","value":"鲍榴"},{"key":"试验负责人","value":"相晓亮"},{"key":"监理工程师","value":"吴明杰"},null,{"key":"计划架梁日期","value":"2017-04-15 (剩余247天)"},{"key":"实际架梁日期","value":"2017-03-05"},{"key":"道床铺设日期","value":"2017-07-01 (剩余324天)"}]
     */

    private String Description;
    private int Result;
    private String imageUrl1;
    private List<InfolistBean> infolist;

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public void setInfolist(List<InfolistBean> infolist) {
        this.infolist = infolist;
    }

    public String getDescription() {
        return Description;
    }

    public int getResult() {
        return Result;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public List<InfolistBean> getInfolist() {
        return infolist;
    }

    public static class InfolistBean {
        /**
         * key : 工程名称
         * value : 八幢山隧道
         */

        private String key;
        private String value;

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
