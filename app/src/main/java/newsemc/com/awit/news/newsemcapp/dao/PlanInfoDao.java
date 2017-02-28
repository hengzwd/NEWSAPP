package newsemc.com.awit.news.newsemcapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.PlanInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLAN_INFO.
*/
public class PlanInfoDao extends AbstractDao<PlanInfo, String> {

    public static final String TABLENAME = "PLAN_INFO";

    /**
     * Properties of entity PlanInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Projectnam = new Property(0, String.class, "projectnam", true, "PROJECTNAM");
        public final static Property Yongkaiper = new Property(1, String.class, "yongkaiper", false, "YONGKAIPER");
        public final static Property Linkaiper = new Property(2, String.class, "linkaiper", false, "LINKAIPER");
        public final static Property Chaikaiper = new Property(3, String.class, "chaikaiper", false, "CHAIKAIPER");
        public final static Property Man = new Property(4, String.class, "man", false, "MAN");
        public final static Property Tel = new Property(5, String.class, "tel", false, "TEL");
    };


    public PlanInfoDao(DaoConfig config) {
        super(config);
    }
    
    public PlanInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLAN_INFO' (" + //
                "'PROJECTNAM' TEXT PRIMARY KEY NOT NULL ," + // 0: projectnam
                "'YONGKAIPER' TEXT," + // 1: yongkaiper
                "'LINKAIPER' TEXT," + // 2: linkaiper
                "'CHAIKAIPER' TEXT," + // 3: chaikaiper
                "'MAN' TEXT," + // 4: man
                "'TEL' TEXT);"); // 5: tel
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLAN_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PlanInfo entity) {
        stmt.clearBindings();
 
        String projectnam = entity.getProjectnam();
        if (projectnam != null) {
            stmt.bindString(1, projectnam);
        }
 
        String yongkaiper = entity.getYongkaiper();
        if (yongkaiper != null) {
            stmt.bindString(2, yongkaiper);
        }
 
        String linkaiper = entity.getLinkaiper();
        if (linkaiper != null) {
            stmt.bindString(3, linkaiper);
        }
 
        String chaikaiper = entity.getChaikaiper();
        if (chaikaiper != null) {
            stmt.bindString(4, chaikaiper);
        }
 
        String man = entity.getMan();
        if (man != null) {
            stmt.bindString(5, man);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(6, tel);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PlanInfo readEntity(Cursor cursor, int offset) {
        PlanInfo entity = new PlanInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // projectnam
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // yongkaiper
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // linkaiper
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // chaikaiper
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // man
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // tel
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PlanInfo entity, int offset) {
        entity.setProjectnam(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setYongkaiper(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLinkaiper(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setChaikaiper(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMan(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTel(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(PlanInfo entity, long rowId) {
        return entity.getProjectnam();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(PlanInfo entity) {
        if(entity != null) {
            return entity.getProjectnam();
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