package newsemc.com.awit.news.newsemcapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPub;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DAN_DIAN_LOGIN_PUB.
*/
public class DanDianLoginPubDao extends AbstractDao<DanDianLoginPub, String> {

    public static final String TABLENAME = "DAN_DIAN_LOGIN_PUB";

    /**
     * Properties of entity DanDianLoginPub.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", true, "NAME");
        public final static Property Status = new Property(1, String.class, "status", false, "STATUS");
        public final static Property Num = new Property(2, String.class, "num", false, "NUM");
        public final static Property Pageno = new Property(3, String.class, "pageno", false, "PAGENO");
    };


    public DanDianLoginPubDao(DaoConfig config) {
        super(config);
    }
    
    public DanDianLoginPubDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DAN_DIAN_LOGIN_PUB' (" + //
                "'NAME' TEXT PRIMARY KEY NOT NULL ," + // 0: name
                "'STATUS' TEXT," + // 1: status
                "'NUM' TEXT," + // 2: num
                "'PAGENO' TEXT);"); // 3: pageno
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DAN_DIAN_LOGIN_PUB'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DanDianLoginPub entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(2, status);
        }
 
        String num = entity.getNum();
        if (num != null) {
            stmt.bindString(3, num);
        }
 
        String pageno = entity.getPageno();
        if (pageno != null) {
            stmt.bindString(4, pageno);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DanDianLoginPub readEntity(Cursor cursor, int offset) {
        DanDianLoginPub entity = new DanDianLoginPub( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // status
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // num
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // pageno
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DanDianLoginPub entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setStatus(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNum(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPageno(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(DanDianLoginPub entity, long rowId) {
        return entity.getName();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(DanDianLoginPub entity) {
        if(entity != null) {
            return entity.getName();
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