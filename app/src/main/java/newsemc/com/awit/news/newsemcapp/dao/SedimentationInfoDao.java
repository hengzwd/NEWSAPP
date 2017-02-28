package newsemc.com.awit.news.newsemcapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.SedimentationInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table SEDIMENTATION_INFO.
*/
public class SedimentationInfoDao extends AbstractDao<SedimentationInfo, String> {

    public static final String TABLENAME = "SEDIMENTATION_INFO";

    /**
     * Properties of entity SedimentationInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Prosectionid = new Property(0, String.class, "prosectionid", true, "PROSECTIONID");
        public final static Property Prosectionname = new Property(1, String.class, "prosectionname", false, "PROSECTIONNAME");
        public final static Property Prositename = new Property(2, String.class, "prositename", false, "PROSITENAME");
        public final static Property Pointname = new Property(3, String.class, "pointname", false, "POINTNAME");
        public final static Property Workinfo = new Property(4, String.class, "workinfo", false, "WORKINFO");
        public final static Property Msginfo = new Property(5, String.class, "msginfo", false, "MSGINFO");
        public final static Property Username = new Property(6, String.class, "username", false, "USERNAME");
    };


    public SedimentationInfoDao(DaoConfig config) {
        super(config);
    }
    
    public SedimentationInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'SEDIMENTATION_INFO' (" + //
                "'PROSECTIONID' TEXT PRIMARY KEY NOT NULL ," + // 0: prosectionid
                "'PROSECTIONNAME' TEXT," + // 1: prosectionname
                "'PROSITENAME' TEXT," + // 2: prositename
                "'POINTNAME' TEXT," + // 3: pointname
                "'WORKINFO' TEXT," + // 4: workinfo
                "'MSGINFO' TEXT," + // 5: msginfo
                "'USERNAME' TEXT);"); // 6: username
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'SEDIMENTATION_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SedimentationInfo entity) {
        stmt.clearBindings();
 
        String prosectionid = entity.getProsectionid();
        if (prosectionid != null) {
            stmt.bindString(1, prosectionid);
        }
 
        String prosectionname = entity.getProsectionname();
        if (prosectionname != null) {
            stmt.bindString(2, prosectionname);
        }
 
        String prositename = entity.getPrositename();
        if (prositename != null) {
            stmt.bindString(3, prositename);
        }
 
        String pointname = entity.getPointname();
        if (pointname != null) {
            stmt.bindString(4, pointname);
        }
 
        String workinfo = entity.getWorkinfo();
        if (workinfo != null) {
            stmt.bindString(5, workinfo);
        }
 
        String msginfo = entity.getMsginfo();
        if (msginfo != null) {
            stmt.bindString(6, msginfo);
        }
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(7, username);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SedimentationInfo readEntity(Cursor cursor, int offset) {
        SedimentationInfo entity = new SedimentationInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // prosectionid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // prosectionname
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // prositename
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // pointname
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // workinfo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // msginfo
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // username
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SedimentationInfo entity, int offset) {
        entity.setProsectionid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setProsectionname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPrositename(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPointname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setWorkinfo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMsginfo(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUsername(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(SedimentationInfo entity, long rowId) {
        return entity.getProsectionid();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(SedimentationInfo entity) {
        if(entity != null) {
            return entity.getProsectionid();
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
