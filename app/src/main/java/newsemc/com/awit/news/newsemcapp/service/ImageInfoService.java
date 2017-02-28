package newsemc.com.awit.news.newsemcapp.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.internal.DaoConfig;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dao.DaoSession;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfoDao;

/**
 * Created by Administrator on 15-10-27.
 */
public class ImageInfoService {
    private static ImageInfoService imagInfoService;
    private static Context appcontext;
    private DaoConfig daoConfig;
    private DaoSession mdaoSession;
    private ImageInfoDao imageInfoDao ;
    private static final String TAG = ImageInfoService.class.getSimpleName();

    public ImageInfoService(){
        super();

    }

    public static ImageInfoService getInstance(Context context){
        if(imagInfoService == null){
            imagInfoService = new ImageInfoService();
            if(context == null){
                appcontext = context.getApplicationContext();
            }
            imagInfoService.mdaoSession = NewsEMCAppllication.getDaoSession(context);
            imagInfoService.imageInfoDao = imagInfoService.mdaoSession.getImageInfoDao();
        }
        return imagInfoService;
    }

    public ImageInfo loadImageInfo(String imag_id){
        return imageInfoDao.load(imag_id);
    }

    public List<ImageInfo> loadAllImageInfo(){
        return imageInfoDao.loadAll();
    }

    public  List<ImageInfo> queryImageInfopageno(String pageno){
        return imageInfoDao.queryBuilder()
                .where(ImageInfoDao.Properties.Pageno.eq(pageno))
                .orderDesc(ImageInfoDao.Properties.Pageno)
                .list();
    }
    public  List<ImageInfo> queryImageInfo_infotype(String infotype){
        return imageInfoDao.queryBuilder()
                .where(ImageInfoDao.Properties.Infotype.eq(infotype))
                .orderDesc(ImageInfoDao.Properties.Infotype)
                .list();
    }

    public List<ImageInfo> queryImageInfo(String where , String... params){
        return imageInfoDao.queryRaw(where, params);
    }
    public List<ImageInfo> queryImageinfotype_pageno(String infotype,String pageno){
        return imageInfoDao.queryBuilder()
                .where(ImageInfoDao.Properties.Infotype.eq(infotype),
                        ImageInfoDao.Properties.Pageno.eq(pageno))
                .orderAsc(ImageInfoDao.Properties.Pageno)
                .list();
    }
    public long saveImageInfo(ImageInfo imageInfo){
        return imageInfoDao.insertOrReplace(imageInfo);
    }

    public void saveImageInfoLists(final List<ImageInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        imageInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i<list.size(); i++){
                    ImageInfo imageInfo = list.get(i);
                    imageInfoDao.insertOrReplace(imageInfo);
                }
            }
        });
    }

    public void deleteAllImageInfo(){
        imageInfoDao.deleteAll();
    }

    public void deleteImageInfo(String image_id){
        imageInfoDao.deleteByKey(image_id);
        Log.i(TAG, "delete");

    }
    public void deleteImageInfo(ImageInfo imageinfo){
        imageInfoDao.delete(imageinfo);
    }
}
