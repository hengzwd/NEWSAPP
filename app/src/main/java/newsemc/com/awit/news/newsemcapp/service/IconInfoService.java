package newsemc.com.awit.news.newsemcapp.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.internal.DaoConfig;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dao.DaoSession;
import newsemc.com.awit.news.newsemcapp.dao.IconInfo;
import newsemc.com.awit.news.newsemcapp.dao.IconInfoDao;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfoDao;

/**
 * Created by Administrator on 15-10-27.
 */
public class IconInfoService {
    private static IconInfoService iconInfoService;
    private static Context appcontext;
    private DaoConfig daoConfig;
    private DaoSession mdaoSession;
    private IconInfoDao iconInfoDao;
    private static final String TAG = IconInfoService.class.getSimpleName();
    public IconInfoService(){
        super();

    }

    public static IconInfoService getInstance(Context context){
        if(iconInfoService == null){
            iconInfoService = new IconInfoService();
            if(context == null){
                appcontext = context.getApplicationContext();
            }
            iconInfoService.mdaoSession = NewsEMCAppllication.getDaoSession(context);
            iconInfoService.iconInfoDao = iconInfoService.mdaoSession.getIconInfoDao();
        }
        return iconInfoService;
    }

    public IconInfo loadIconInfo(Integer imag_id){
        return iconInfoDao.load(imag_id);
    }

    public List<IconInfo> loadAllIconInfo(){
        return iconInfoDao.loadAll();
    }

    public  List<IconInfo> queryIconInfopageno(String pageno){
        return iconInfoDao.queryBuilder()
                .where(IconInfoDao.Properties.Pageno.eq(pageno))
                .orderDesc(IconInfoDao.Properties.Pageno)
                .list();
    }
    public  List<IconInfo> queryIconInfo_infotype(String infotype){
        return iconInfoDao.queryBuilder()
                .where(IconInfoDao.Properties.Id.eq(infotype))
                .orderDesc(IconInfoDao.Properties.Id)
                .list();
    }

    public List<IconInfo> queryIconInfo(String where , String... params){
        return iconInfoDao.queryRaw(where, params);
    }
    public List<IconInfo> queryIconInfotype_pageno(String infotype,String pageno){
        return iconInfoDao.queryBuilder()
                .where(IconInfoDao.Properties.Id.eq(infotype),
                        IconInfoDao.Properties.Pageno.eq(pageno))
                .orderAsc(IconInfoDao.Properties.Pageno)
                .list();
    }
    public long saveIconInfo(IconInfo imageInfo){
        return iconInfoDao.insertOrReplace(imageInfo);
    }

    public void saveIconInfoLists(final List<IconInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        iconInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i<list.size(); i++){
                    IconInfo iconInfo = list.get(i);
                    iconInfoDao.insertOrReplace(iconInfo);
                }
            }
        });
    }

    public void deleteAllIconInfo(){
        iconInfoDao.deleteAll();
    }

    public void deleteIconInfo(Integer image_id){
        iconInfoDao.deleteByKey(image_id);
        Log.i(TAG, "delete");
    }
    public void deleteIconInfo(IconInfo imageinfo){
        iconInfoDao.delete(imageinfo);
    }

}
