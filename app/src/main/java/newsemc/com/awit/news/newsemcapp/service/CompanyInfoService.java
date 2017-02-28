package newsemc.com.awit.news.newsemcapp.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.internal.DaoConfig;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.DaoSession;

/**
 * Created by Administrator on 15-10-27.
 */
public class CompanyInfoService {
     private static CompanyInfoService companyInfoService;
    private static Context appcontext;
    private DaoConfig daoConfig;
    private DaoSession mdaoSession;
    private CompanyInfoDao companyInfoDao;
    private static final String TAG = CompanyInfoService.class.getSimpleName();

    private CompanyInfoService(){
        super();
    }
    public static CompanyInfoService getInstance(Context context){
        if(companyInfoService == null){
            companyInfoService = new CompanyInfoService();
            if(context == null){
                appcontext = context.getApplicationContext();
            }
            companyInfoService.mdaoSession = NewsEMCAppllication.getDaoSession(context);
            companyInfoService.companyInfoDao = companyInfoService.mdaoSession.getCompanyInfoDao();
        }
        return companyInfoService;
    }

    public CompanyInfo loadCompanyInfo(String imag_id){
        return companyInfoDao.load(imag_id);
    }

    public List<CompanyInfo> loadAllCompanyInfo(){
        return companyInfoDao.loadAll();
    }

    public  List<CompanyInfo> queryCompanyInfopageno(String pageno){
        return companyInfoDao.queryBuilder()
                .where(CompanyInfoDao.Properties.Pageno.eq(pageno))
                .orderDesc(CompanyInfoDao.Properties.Pageno)
                .list();
    }

    public  List<CompanyInfo> queryCompanyInfo_infotype(String infotype){
        return companyInfoDao.queryBuilder()
                .where(CompanyInfoDao.Properties.Infotype.eq(infotype))
                .orderDesc(CompanyInfoDao.Properties.Infotype)
                .list();
    }



    public List<CompanyInfo> queryCompanyInfo(String where , String... params){
        return companyInfoDao.queryRaw(where, params);
    }
    public List<CompanyInfo> queryCompanyInfotype_pageno(String infotype,String pageno){
        return companyInfoDao.queryBuilder()
                .where(CompanyInfoDao.Properties.Infotype.eq(infotype),
                        CompanyInfoDao.Properties.Pageno.eq(pageno))
                .orderAsc(CompanyInfoDao.Properties.Pageno)
                .list();
    }
    public long saveCompanyInfo(CompanyInfo companyInfo){
        return companyInfoDao.insertOrReplace(companyInfo);
    }

    public void saveCompanyInfoLists(final List<CompanyInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        companyInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i<list.size(); i++){
                    CompanyInfo companyInfo = list.get(i);
                    companyInfoDao.insertOrReplace(companyInfo);
                }
            }
        });
    }

    public void deleteAllCompanyInfo(){
        companyInfoDao.deleteAll();
    }

    public void deleteImageInfo(String image_id){
        companyInfoDao.deleteByKey(image_id);
        Log.i(TAG, "delete");

    }
    public void deleteCompanyInfo(CompanyInfo companyInfo){
        companyInfoDao.delete(companyInfo);
    }
}
