package newsemc.com.awit.news.newsemcapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.ApkUpdateInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table APK_UPDATE_INFO.
*/
public class ApkUpdateInfoDao extends AbstractDao<ApkUpdateInfo, String> {

    public static final String TABLENAME = "APK_UPDATE_INFO";

    /**
     * Properties of entity ApkUpdateInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property AppUrl = new Property(1, String.class, "appUrl", false, "APP_URL");
        public final static Property ApkName = new Property(2, String.class, "apkName", false, "APK_NAME");
        public final static Property AppId = new Property(3, String.class, "appId", false, "APP_ID");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property UseFlag = new Property(6, String.class, "useFlag", false, "USE_FLAG");
        public final static Property VersionCode = new Property(7, String.class, "versionCode", false, "VERSION_CODE");
        public final static Property VersionName = new Property(8, String.class, "versionName", false, "VERSION_NAME");
        public final static Property UploadTime = new Property(9, String.class, "uploadTime", false, "UPLOAD_TIME");
        public final static Property PackName = new Property(10, String.class, "packName", false, "PACK_NAME");
        public final static Property ApkSize = new Property(11, String.class, "apkSize", false, "APK_SIZE");
    };


    public ApkUpdateInfoDao(DaoConfig config) {
        super(config);
    }
    
    public ApkUpdateInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'APK_UPDATE_INFO' (" + //
                "'ID' TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "'APP_URL' TEXT," + // 1: appUrl
                "'APK_NAME' TEXT," + // 2: apkName
                "'APP_ID' TEXT," + // 3: appId
                "'DESCRIPTION' TEXT," + // 4: description
                "'NAME' TEXT," + // 5: name
                "'USE_FLAG' TEXT," + // 6: useFlag
                "'VERSION_CODE' TEXT," + // 7: versionCode
                "'VERSION_NAME' TEXT," + // 8: versionName
                "'UPLOAD_TIME' TEXT," + // 9: uploadTime
                "'PACK_NAME' TEXT," + // 10: packName
                "'APK_SIZE' TEXT);"); // 11: apkSize
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'APK_UPDATE_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ApkUpdateInfo entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String appUrl = entity.getAppUrl();
        if (appUrl != null) {
            stmt.bindString(2, appUrl);
        }
 
        String apkName = entity.getApkName();
        if (apkName != null) {
            stmt.bindString(3, apkName);
        }
 
        String appId = entity.getAppId();
        if (appId != null) {
            stmt.bindString(4, appId);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String useFlag = entity.getUseFlag();
        if (useFlag != null) {
            stmt.bindString(7, useFlag);
        }
 
        String versionCode = entity.getVersionCode();
        if (versionCode != null) {
            stmt.bindString(8, versionCode);
        }
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(9, versionName);
        }
 
        String uploadTime = entity.getUploadTime();
        if (uploadTime != null) {
            stmt.bindString(10, uploadTime);
        }
 
        String packName = entity.getPackName();
        if (packName != null) {
            stmt.bindString(11, packName);
        }
 
        String apkSize = entity.getApkSize();
        if (apkSize != null) {
            stmt.bindString(12, apkSize);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ApkUpdateInfo readEntity(Cursor cursor, int offset) {
        ApkUpdateInfo entity = new ApkUpdateInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // appUrl
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // apkName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // appId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // description
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // useFlag
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // versionCode
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // versionName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // uploadTime
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // packName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // apkSize
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ApkUpdateInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAppUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setApkName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAppId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUseFlag(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setVersionCode(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setVersionName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setUploadTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPackName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setApkSize(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(ApkUpdateInfo entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(ApkUpdateInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
