package newsemc.com.awit.news.newsemcapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.IconInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ICON_INFO.
*/
public class IconInfoDao extends AbstractDao<IconInfo, Integer> {

    public static final String TABLENAME = "ICON_INFO";

    /**
     * Properties of entity IconInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "ID");
        public final static Property Status = new Property(1, String.class, "status", false, "STATUS");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Pageno = new Property(4, String.class, "pageno", false, "PAGENO");
    };


    public IconInfoDao(DaoConfig config) {
        super(config);
    }
    
    public IconInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ICON_INFO' (" + //
                "'ID' INTEGER PRIMARY KEY ," + // 0: id
                "'STATUS' TEXT," + // 1: status
                "'NAME' TEXT," + // 2: name
                "'TYPE' TEXT," + // 3: type
                "'PAGENO' TEXT);"); // 4: pageno
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ICON_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, IconInfo entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(2, status);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
 
        String pageno = entity.getPageno();
        if (pageno != null) {
            stmt.bindString(5, pageno);
        }
    }

    /** @inheritdoc */
    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public IconInfo readEntity(Cursor cursor, int offset) {
        IconInfo entity = new IconInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // status
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // pageno
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, IconInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setStatus(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPageno(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Integer updateKeyAfterInsert(IconInfo entity, long rowId) {
        return entity.getId();
    }
    
    /** @inheritdoc */
    @Override
    public Integer getKey(IconInfo entity) {
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
