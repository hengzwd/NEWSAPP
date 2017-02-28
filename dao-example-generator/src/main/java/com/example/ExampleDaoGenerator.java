package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;



public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "newsemc.com.awit.news.newsemcapp.dao");
        addImageInfo(schema);
        addOASysInfo(schema);
        addDetailInfo(schema);
        addProjectInfo(schema);
        addProjectTtrendsInfo(schema);
//        addMeasureInfo(schema);
        addMeasureDetailInfo(schema);
        addDealInfo(schema);

        addPlanInfo(schema);
        addProblemInfo(schema);
        addPreceptInfo(schema);
        addWorkpieceInfo(schema);
        addScienceInfo(schema);
        addAuditInfo(schema);
        addCraftInfo(schema);
        addSedimentationInfo(schema);
        addProjectImpsInfo(schema);
        addLxlBxoInfo(schema);
        addDiaoDuInfo(schema);
        addLabInfo(schema);
        addBhzInfo(schema);
        addZdgcInfo(schema);

        addPersonDepartments(schema);
        addLoginDepartments(schema);
        addPersonDeparts(schema);
        addCompanyInfo(schema);
        addIconInfo(schema);
        addDanDianInfo(schema);
        addAlterPwdInfo(schema);
        addPushInfo(schema);
        addApkUpdateInfo(schema);
        addSpecialAccountInfo(schema);
        addCompanyLogoInfo(schema);
        addFirstMenuInfo(schema);
        addSecondMenuInfo(schema);
        addProjectInfoDetail(schema);
        addProjectInfoDetailPic(schema);
        addProjectInfoDetailPeriod(schema);
        addProjectInfoDetailMainItem(schema);
//        addUserInfo(schema);
//        addEquipInfo(schema);
//        addNodeInfo(schema);
//        addDelayAlarmInfo(schema);
//        addAlarmInfo(schema);
//        addAlarmListInfo(schema);
//        addBanhezhanSituation(schema);
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }
    //图片锟斤拷锟斤拷
    private  static  void addImageInfo(Schema schema){
        Entity mixingInfo=schema.addEntity("ImageInfo");
        mixingInfo.addStringProperty("infoid").primaryKey();
        mixingInfo.addStringProperty("infotype");
        mixingInfo.addStringProperty("infoname");
        mixingInfo.addStringProperty("infoimg");
        mixingInfo.addStringProperty("infourl");
        mixingInfo.addStringProperty("infocontent");
        mixingInfo.addStringProperty("num");
        mixingInfo.addStringProperty("pageno");
        mixingInfo.addStringProperty("infodate");
    }

    //公司要闻
    private  static  void addCompanyInfo(Schema schema){
        Entity mixingInfo=schema.addEntity("CompanyInfo");
        mixingInfo.addStringProperty("infoid").primaryKey();
        mixingInfo.addStringProperty("infotype");
        mixingInfo.addStringProperty("infoname");
        mixingInfo.addStringProperty("infoimg");
        mixingInfo.addStringProperty("infourl");
        mixingInfo.addStringProperty("infocontent");
        mixingInfo.addStringProperty("infodate");
        mixingInfo.addStringProperty("num");
        mixingInfo.addStringProperty("pageno");
    }

    //锟斤拷锟斤拷募锟?
    private static void addOASysInfo(Schema schema) {
        Entity loginInfo = schema.addEntity("OASysInfo");
        loginInfo.addStringProperty("fileid").primaryKey();
        loginInfo.addStringProperty("handleid");
        loginInfo.addStringProperty("detail");
        loginInfo.addStringProperty("title");
        loginInfo.addStringProperty("enddate");
    }

    //锟斤拷取锟斤拷锟斤拷锟斤拷息锟接匡拷
    private static void addDetailInfo(Schema schema) {
        Entity loginInfo = schema.addEntity("DetailInfo");
        loginInfo.addStringProperty("infoid").primaryKey();
        loginInfo.addStringProperty("infotype");
        loginInfo.addStringProperty("htmlpath");
        loginInfo.addStringProperty("num");
    }

    //锟斤拷取锟斤拷目锟斤拷锟斤拷息
    private static void addProjectInfo(Schema schema) {
        Entity loginInfo = schema.addEntity("ProjectInfo");
        loginInfo.addStringProperty("id").primaryKey();
        loginInfo.addStringProperty("pname");
        loginInfo.addStringProperty("code");
        loginInfo.addStringProperty("nameabbr");
        loginInfo.addStringProperty("constructiondepId");
        loginInfo.addStringProperty("bname");
        loginInfo.addStringProperty("designCompanyName");
        loginInfo.addStringProperty("examineCompanyName");
        loginInfo.addStringProperty("startdate");
        loginInfo.addStringProperty("rundate");
        loginInfo.addStringProperty("updatedate");
        loginInfo.addStringProperty("description");
    }
    //项目信息详情（2015年12月15日，在ProjectInfo基础上修改）
    private static void addProjectInfoDetail(Schema schema){
        Entity projectInfo = schema.addEntity("ProjectInfoDetail");
        projectInfo.addStringProperty("project_id").primaryKey();
        projectInfo.addStringProperty("projectName");
        projectInfo.addStringProperty("projectType");
        projectInfo.addStringProperty("conxName");
        projectInfo.addStringProperty("projectSource");
        projectInfo.addStringProperty("projectCode");
        projectInfo.addStringProperty("projectLength");
        projectInfo.addStringProperty("startStation");
        projectInfo.addStringProperty("stopStation");
        projectInfo.addStringProperty("startDate");
        projectInfo.addStringProperty("stopDate");
        projectInfo.addStringProperty("stationNum");
        projectInfo.addStringProperty("invest");
        projectInfo.addStringProperty("startMile");
        projectInfo.addStringProperty("stopMile");
        projectInfo.addStringProperty("designCorpName");
        projectInfo.addStringProperty("examineCorpName");
        projectInfo.addStringProperty("proCity");
        projectInfo.addStringProperty("proStation");
        projectInfo.addStringProperty("description");
    }
    //项目信息详情（2015年12月15日，在ProjectInfo基础上修改）
    private static void addProjectInfoDetailPic(Schema schema){
        Entity projectInfoPic = schema.addEntity("ProjectInfoDetailPic");
        projectInfoPic.addStringProperty("projectPicName").primaryKey();
        projectInfoPic.addStringProperty("projectPicUrl");
        projectInfoPic.addStringProperty("projectPicId");
    }
    //项目信息详情（2015年12月15日，在ProjectInfo基础上修改）
    private static void addProjectInfoDetailPeriod(Schema schema){
        Entity projectPeriod = schema.addEntity("ProjectInfoDetailPeriod");
        projectPeriod.addStringProperty("id");
        projectPeriod.addStringProperty("projectInfoId");
        projectPeriod.addStringProperty("jyWenhao");
        projectPeriod.addStringProperty("kyWenhao");
        projectPeriod.addStringProperty("csWenhao");
        projectPeriod.addStringProperty("sgWenhao");
        projectPeriod.addStringProperty("zdWenhao");
        projectPeriod.addStringProperty("sgJishu");
        projectPeriod.addStringProperty("sgTouzi");
        projectPeriod.addStringProperty("sgGongqi");
        projectPeriod.addStringProperty("szJdGongqi");
    }
    //项目信息详情（2015年12月15日，在ProjectInfo基础上修改）
    private static void addProjectInfoDetailMainItem(Schema schema){
        Entity projectMainItem = schema.addEntity("ProjectInfoDetailMainItem");
        projectMainItem.addStringProperty("id").primaryKey();
        projectMainItem.addStringProperty("projectInfoId");
        projectMainItem.addStringProperty("kongzhiSection");
    }

    //锟斤拷取锟斤拷目锟斤拷态锟接匡拷
    private static void addProjectTtrendsInfo(Schema schema) {
        Entity loginInfo = schema.addEntity("ProjectTtrendsInfo");
        loginInfo.addStringProperty("id").primaryKey();
        loginInfo.addStringProperty("name");
        loginInfo.addStringProperty("code");
        loginInfo.addStringProperty("conxname");
        loginInfo.addStringProperty("aptitude");
        loginInfo.addStringProperty("tenders");
        loginInfo.addStringProperty("totallength");
        loginInfo.addStringProperty("constractamount");
        loginInfo.addStringProperty("projectinfoid");
    }

    //锟斤拷取围锟揭观诧拷锟斤拷目锟斤拷萍锟絠d 锟接匡拷
    private static void addMeasureInfo(Schema schema) {
        Entity analyzeInfo = schema.addEntity("MeasureInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addStringProperty("name");
    }

    //围锟斤拷锟斤拷锟斤拷 锟斤拷细锟叫憋拷 锟接匡拷
    private static void addMeasureDetailInfo(Schema schema) {
        Entity loginInfo = schema.addEntity("MeasureDetailInfo");
        loginInfo.addStringProperty("id").primaryKey();
        loginInfo.addStringProperty("name");
        loginInfo.addStringProperty("monitorobjectname");
        loginInfo.addStringProperty("peak");
        loginInfo.addStringProperty("speed");
        loginInfo.addStringProperty("warntime");
        loginInfo.addStringProperty("warnlevel");
        loginInfo.addStringProperty("dealflag");
    }


    //锟斤拷锟斤拷锟斤拷息锟接匡拷
    private static void addDealInfo(Schema schema) {
        Entity analyzeInfo = schema.addEntity("DealInfo");
        analyzeInfo.addStringProperty("handledesc").primaryKey();
        analyzeInfo.addStringProperty("status");
        analyzeInfo.addStringProperty("dealuser");
        analyzeInfo.addStringProperty("warndealtime");
    }

    //锟斤拷锟截诧拷迁锟斤拷冉涌锟?
    private static void addPlanInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("PlanInfo");
        analyzeInfo.addStringProperty("projectnam").primaryKey();
        analyzeInfo.addStringProperty("yongkaiper");
        analyzeInfo.addStringProperty("linkaiper");
        analyzeInfo.addStringProperty("chaikaiper");
        analyzeInfo.addStringProperty("man");
        analyzeInfo.addStringProperty("tel");
    }

    //锟斤拷锟截诧拷迁锟斤拷锟窖碉拷锟斤拷锟斤拷涌锟?
    private static void addProblemInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("ProblemInfo");
        analyzeInfo.addStringProperty("projectnam").primaryKey();
        analyzeInfo.addStringProperty("detail");
        analyzeInfo.addStringProperty("advice");
        analyzeInfo.addStringProperty("createat");
    }
    //锟斤拷锟斤拷锟斤拷证锟接匡拷
    private static void addPreceptInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("PreceptInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addStringProperty("huiyiname");
        analyzeInfo.addStringProperty("danwei");
        analyzeInfo.addStringProperty("jiyao");
        analyzeInfo.addStringProperty("huiyitime");
    }
    //锟阶硷拷锟斤拷锟教接匡拷
    private static void addWorkpieceInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("WorkpieceInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("shidanwei");
        analyzeInfo.addStringProperty("type");
        analyzeInfo.addStringProperty("ctime");
        analyzeInfo.addStringProperty("content");
    }

    //锟斤拷锟斤拷锟斤拷锟阶接匡拷
    private static void addScienceInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("ScienceInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("content");
        analyzeInfo.addStringProperty("ctime");
        analyzeInfo.addStringProperty("shedanwei");
        analyzeInfo.addStringProperty("shidanwei");
    }

    //施锟斤拷图/施锟斤拷图锟斤拷私涌锟?
    private static void addAuditInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("AuditInfo");
        analyzeInfo.addStringProperty("name").primaryKey();
        analyzeInfo.addStringProperty("ziliao");
        analyzeInfo.addStringProperty("counts");
    }

    //锟斤拷锟秸癸拷锟斤拷锟接匡拷
    private static void addCraftInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("CraftInfo");
        analyzeInfo.addStringProperty("infoimg").primaryKey();
    }

    //锟斤拷锟斤拷锟桔诧拷涌锟?
    private static void addSedimentationInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("SedimentationInfo");
        analyzeInfo.addStringProperty("prosectionid").primaryKey();
        analyzeInfo.addStringProperty("prosectionname");
        analyzeInfo.addStringProperty("prositename");
        analyzeInfo.addStringProperty("pointname");
        analyzeInfo.addStringProperty("workinfo");
        analyzeInfo.addStringProperty("msginfo");
        analyzeInfo.addStringProperty("username");
    }

    //锟斤拷锟斤拷锟斤拷
    private static void addLxlBxoInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("LxlBxoInfo");
        analyzeInfo.addStringProperty("prosectionid").primaryKey();
        analyzeInfo.addStringProperty("prosectionname");
        analyzeInfo.addStringProperty("prositename");
        analyzeInfo.addStringProperty("pointname");
        analyzeInfo.addStringProperty("sgjd");
        analyzeInfo.addStringProperty("warnrecord");
        analyzeInfo.addStringProperty("buildname");
        analyzeInfo.addStringProperty("chargename");
        analyzeInfo.addStringProperty("flow");
    }

    //锟斤拷锟窖点工锟斤拷
    private static void addProjectImpsInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("ProjectImpsInfo");
        analyzeInfo.addStringProperty("projectname").primaryKey();
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("lc");
        analyzeInfo.addStringProperty("middl");
        analyzeInfo.addStringProperty("sitelength");
        analyzeInfo.addStringProperty("planopentime");
        analyzeInfo.addStringProperty("planclosetime");
    }

    //锟斤拷锟饺癸拷锟斤拷
    private  static  void addDiaoDuInfo(Schema schema){
        Entity mixingInfo=schema.addEntity("DiaoDuInfo");
        mixingInfo.addStringProperty("docCode").primaryKey();
        mixingInfo.addStringProperty("filetitle");
        mixingInfo.addStringProperty("uploaddate");
        mixingInfo.addStringProperty("num");
    }

    //锟矫伙拷锟斤拷录锟接匡拷
    private static void addPersonDepartments(Schema schema) {
        Entity customer = schema.addEntity("PersonInfo");
        customer.addStringProperty("userId").primaryKey();
        customer.addStringProperty("ssid");
        customer.addStringProperty("name");
        customer.addStringProperty("contact");
        customer.addStringProperty("sex");
        customer.addStringProperty("account");
        customer.addStringProperty("relateAccount");
        customer.addStringProperty("compname");
        customer.addStringProperty("idNo");

        Entity order = schema.addEntity("DepartmentInfo");
        order.addStringProperty("id");
        order.addStringProperty("name");
        order.addStringProperty("duty");
        Property f_userId = order.addStringProperty("f_userId").notNull().getProperty();
        order.addToOne(customer, f_userId);

        ToMany customerToOrders = customer.addToMany(order, f_userId);
    }


    //锟斤拷取锟斤拷员锟接匡拷
    private static void addLoginDepartments(Schema schema) {
        Entity customer = schema.addEntity("LoginInfo");
        customer.addStringProperty("userId").primaryKey();
        customer.addStringProperty("account");
        customer.addStringProperty("name");
        customer.addStringProperty("contact");

        Entity order = schema.addEntity("DepartsInfo");
        order.addStringProperty("id");
        order.addStringProperty("name");
        order.addStringProperty("duty");
        Property f_loginId = order.addStringProperty("f_loginId").notNull().getProperty();
        order.addToOne(customer, f_loginId);

        ToMany customerToOrders = customer.addToMany(order, f_loginId);
    }

    //锟斤拷锟絪sid锟斤拷取锟矫伙拷锟斤拷录锟斤拷息
    private static void addPersonDeparts(Schema schema) {
        Entity customer = schema.addEntity("SsidInfo");
        customer.addStringProperty("userId").primaryKey();
        customer.addStringProperty("ssid");
        customer.addStringProperty("name");
        customer.addStringProperty("contact");
        customer.addStringProperty("sex");
        customer.addStringProperty("account");

        Entity order = schema.addEntity("DeptsInfo");
        order.addStringProperty("id");
        order.addStringProperty("name");
        order.addStringProperty("duty");
        Property f_userId = order.addStringProperty("f_userId").notNull().getProperty();
        order.addToOne(customer, f_userId);

        ToMany customerToOrders = customer.addToMany(order, f_userId);
    }

    //实锟斤拷锟揭接匡拷
    private static void addLabInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("LabInfo");
        analyzeInfo.addStringProperty("sectionname").primaryKey();
        analyzeInfo.addStringProperty("yljtotal");
        analyzeInfo.addStringProperty("yljusenum");
        analyzeInfo.addStringProperty("wnjtotal");
        analyzeInfo.addStringProperty("wnjusenum");
        analyzeInfo.addStringProperty("reporttotal");
        analyzeInfo.addStringProperty("hntinfo");
        analyzeInfo.addStringProperty("gjinfo");
        analyzeInfo.addStringProperty("gjhjinfo");
        analyzeInfo.addStringProperty("gjjxinfo");
    }

    //锟斤拷锟秸撅拷涌锟?
    private static void addBhzInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("BhzInfo");
        analyzeInfo.addStringProperty("sectionname").primaryKey();
        analyzeInfo.addStringProperty("bhjtotal");
        analyzeInfo.addStringProperty("bhjusenum");
        analyzeInfo.addStringProperty("volume");
        analyzeInfo.addStringProperty("pannum");
        analyzeInfo.addStringProperty("mixwarnnum");
        analyzeInfo.addStringProperty("mixratio");
        analyzeInfo.addStringProperty("matlwarnnum");
        analyzeInfo.addStringProperty("matlratio");
        analyzeInfo.addStringProperty("maltdisratio");
    }

    //锟斤拷锟窖点工锟教接匡拷
    private static void addZdgcInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("ZdgcInfo");
        analyzeInfo.addStringProperty("buildname").primaryKey();
        analyzeInfo.addStringProperty("designtype");
        analyzeInfo.addStringProperty("designnum");
        analyzeInfo.addStringProperty("dayfinish");
        analyzeInfo.addStringProperty("mothfinish");
        analyzeInfo.addStringProperty("kailei");
        analyzeInfo.addStringProperty("kaileiratio");
        analyzeInfo.addStringProperty("plandate");
        analyzeInfo.addStringProperty("delaynum");
        analyzeInfo.addStringProperty("delaydays");
    }

    //图标接口
    private static void addIconInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("IconInfo");
        analyzeInfo.addIntProperty("id").primaryKey();
        analyzeInfo.addStringProperty("status");
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("type");
        analyzeInfo.addStringProperty("pageno");
    }


    //home下图标接口
    private static void addDanDianInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("DanDianLoginPub");
        analyzeInfo.addStringProperty("name").primaryKey();
        analyzeInfo.addStringProperty("status");
        analyzeInfo.addStringProperty("num");
        analyzeInfo.addStringProperty("pageno");
    }


    //修改密码
    private static void addAlterPwdInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("AlterPwdInfo");
        analyzeInfo.addStringProperty("userId").primaryKey();
        analyzeInfo.addStringProperty("oldpassword");
        analyzeInfo.addStringProperty("newpassword");
        analyzeInfo.addStringProperty("ssid");
    }
    //消息推送
    private static void addPushInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("PushInfo");
        analyzeInfo.addStringProperty("wylcNum").primaryKey();
        analyzeInfo.addStringProperty("oaNum");
        analyzeInfo.addStringProperty("sum");
    }

    //APK信息及下载更新
    private static void addApkUpdateInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("ApkUpdateInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addStringProperty("appUrl");
        analyzeInfo.addStringProperty("apkName");
        analyzeInfo.addStringProperty("appId");
        analyzeInfo.addStringProperty("description");
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("useFlag");
        analyzeInfo.addStringProperty("versionCode");
        analyzeInfo.addStringProperty("versionName");
        analyzeInfo.addStringProperty("uploadTime");
        analyzeInfo.addStringProperty("packName");
        analyzeInfo.addStringProperty("apkSize");
    }

    //特殊账号登录
    private static void addSpecialAccountInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("SpecialAccountInfo");
        analyzeInfo.addStringProperty("companyid").primaryKey();
        analyzeInfo.addStringProperty("companyname");
        analyzeInfo.addStringProperty("account");
        analyzeInfo.addStringProperty("password");
        analyzeInfo.addStringProperty("imei");
    }

    //公司logo
    private static void addCompanyLogoInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("CompanyLogoInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addStringProperty("logopath");
    }

    //一级菜单
    private static void addFirstMenuInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("FirstMenuInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addIntProperty("level");
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("pid");
        analyzeInfo.addStringProperty("code");
        analyzeInfo.addIntProperty("type");
    }

    //二级菜单
    private static void addSecondMenuInfo(Schema schema){
        Entity analyzeInfo = schema.addEntity("SecondMenuInfo");
        analyzeInfo.addStringProperty("id").primaryKey();
        analyzeInfo.addIntProperty("level");
        analyzeInfo.addStringProperty("name");
        analyzeInfo.addStringProperty("pid");
        analyzeInfo.addStringProperty("code");
        analyzeInfo.addIntProperty("type");
    }
}