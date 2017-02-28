package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.io.Serializable;

/**
 * Created by gesangdianzi on 2016/11/18.
 */
public class phoneInfo implements Serializable {
    //电话号码
    private  String 电话号码;
    //手机唯一码IMEI
    private  String 手机标识;
    //手机品牌
    private  String 手机品牌;
    //手机系统版本
    private  String 系统版本;
    //手机型号
    private  String 手机型号;
    //联系人姓名
    private  String 手机名称;

    public String get电话号码() {
        return 电话号码;
    }

    public void set电话号码(String 电话号码) {
        this.电话号码 = 电话号码;
    }

    public String get手机标识() {
        return 手机标识;
    }

    public void set手机标识(String 手机标识) {
        this.手机标识 = 手机标识;
    }

    public String get手机品牌() {
        return 手机品牌;
    }

    public void set手机品牌(String 手机品牌) {
        this.手机品牌 = 手机品牌;
    }

    public String get系统版本() {
        return 系统版本;
    }

    public void set系统版本(String 系统版本) {
        this.系统版本 = 系统版本;
    }

    public String get手机型号() {
        return 手机型号;
    }

    public void set手机型号(String 手机型号) {
        this.手机型号 = 手机型号;
    }

    public String get手机名称() {
        return 手机名称;
    }

    public void set手机名称(String 手机名称) {
        this.手机名称 = 手机名称;
    }



}
