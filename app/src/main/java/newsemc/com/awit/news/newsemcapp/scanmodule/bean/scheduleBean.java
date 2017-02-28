package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.util.List;

/**
 * Created by hengzwd on 2016/10/19.
 */
public class scheduleBean {

    /**
     * Description : Success
     * Result : 0
     * dailyList : [{"design":"IV","finishDate":"2016-05-23","footage":"0.0","mileStone":"DK407+786.0"},{"design":"IV","finishDate":"2016-05-22","footage":"0.0","mileStone":"DK407+786.0"},{"design":"IV","finishDate":"2016-05-21","footage":"0.0","mileStone":"DK407+786.0"},{"design":"IV","finishDate":"2016-05-20","footage":"0.0","mileStone":"DK407+786.0"},{"design":"IV","finishDate":"2016-05-19","footage":"10.0","mileStone":"DK407+786.0"},{"design":"IV","finishDate":"2016-05-18","footage":"0.0","mileStone":"DK407+776.0"},{"design":"IV","finishDate":"2016-05-17","footage":"0.0","mileStone":"DK407+776.0"},{"design":"IV","finishDate":"2016-05-16","footage":"0.0","mileStone":"DK407+776.0"},{"design":"IV","finishDate":"2016-05-15","footage":"0.0","mileStone":"DK407+776.0"},{"design":"IV","finishDate":"2016-05-14","footage":"0.0","mileStone":"DK407+776.0"}]
     */

    private String Description;
    private int Result;
    /**
     * design : IV
     * finishDate : 2016-05-23
     * footage : 0.0
     * mileStone : DK407+786.0
     */

    private List<DailyListEntity> dailyList;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public List<DailyListEntity> getDailyList() {
        return dailyList;
    }

    public void setDailyList(List<DailyListEntity> dailyList) {
        this.dailyList = dailyList;
    }

    public static class DailyListEntity {
        private String design;
        private String finishDate;
        private String footage;
        private String mileStone;

        public String getDesign() {
            return design;
        }

        public void setDesign(String design) {
            this.design = design;
        }

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

        public String getFootage() {
            return footage;
        }

        public void setFootage(String footage) {
            this.footage = footage;
        }

        public String getMileStone() {
            return mileStone;
        }

        public void setMileStone(String mileStone) {
            this.mileStone = mileStone;
        }
    }
}
