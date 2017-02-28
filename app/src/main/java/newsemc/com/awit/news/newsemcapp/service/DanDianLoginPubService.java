package newsemc.com.awit.news.newsemcapp.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.internal.DaoConfig;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPub;
import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPubDao;
import newsemc.com.awit.news.newsemcapp.dao.DaoSession;
import newsemc.com.awit.news.newsemcapp.dao.IconInfo;
import newsemc.com.awit.news.newsemcapp.dao.IconInfoDao;

/**
 * Created by Administrator on 15-10-29.
 */
public class DanDianLoginPubService {
    private static DanDianLoginPubService danDianLoginPubService;
    private static Context appcontext;
    private DaoConfig daoConfig;
    private DaoSession mdaoSession;
    private DanDianLoginPubDao danDianLoginPubDao;
    private static final String TAG = DanDianLoginPubService.class.getSimpleName();
    public DanDianLoginPubService(){
        super();

    }

    public static DanDianLoginPubService getInstance(Context context){
        if(danDianLoginPubService == null){
            danDianLoginPubService = new DanDianLoginPubService();
            if(context == null){
                appcontext = context.getApplicationContext();
            }
            danDianLoginPubService.mdaoSession = NewsEMCAppllication.getDaoSession(context);
            danDianLoginPubService.danDianLoginPubDao = danDianLoginPubService.mdaoSession.getDanDianLoginPubDao();
        }
        return danDianLoginPubService;
    }


    public List<DanDianLoginPub> loadAllDanDianLoginPub(){
        return danDianLoginPubDao.loadAll();
    }



    public List<DanDianLoginPub> queryDanDianLoginPubDao(String where , String... params){
        return danDianLoginPubDao.queryRaw(where, params);
    }
    public List<DanDianLoginPub> queryDanDianLoginPubDaotype(String name){
        return danDianLoginPubDao.queryBuilder()
                .where(DanDianLoginPubDao.Properties.Name.eq(name))
                .orderAsc(DanDianLoginPubDao.Properties.Name)
                .list();
    }
    public List<DanDianLoginPub> queryDanDianLoginPubDaopage(String page){
        return danDianLoginPubDao.queryBuilder()
                .where(DanDianLoginPubDao.Properties.Pageno.eq(page))
                .orderAsc(DanDianLoginPubDao.Properties.Pageno)
                .list();
    }
    public long saveDanDianLoginPubDao(DanDianLoginPub danDianLoginPub){
        return danDianLoginPubDao.insertOrReplace(danDianLoginPub);
    }

    public void saveDanDianLoginPubLists(final List<DanDianLoginPub> list){
        if(list == null || list.isEmpty()){
            return;
        }
        danDianLoginPubDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    DanDianLoginPub danDianLoginPub = list.get(i);
                    danDianLoginPubDao.insertOrReplace(danDianLoginPub);
                }
            }
        });
    }

    public void deleteAllDanDianLoginPub(){
        danDianLoginPubDao.deleteAll();
    }


    public void deleteDanDianLoginPub(DanDianLoginPub danDianLoginPub){
        danDianLoginPubDao.delete(danDianLoginPub);
    }

}
