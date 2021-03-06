package newsemc.com.awit.news.newsemcapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import newsemc.com.awit.news.newsemcapp.dao.BhzInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BHZ_INFO.
*/
public class BhzInfoDao extends AbstractDao<BhzInfo, String> {

    public static final String TABLENAME = "BHZ_INFO";

    /**
     * Properties of entity BhzInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Sectionname = new Property(0, String.class, "sectionname", true, "SECTIONNAME");
        public final static Property Bhjtotal = new Property(1, String.class, "bhjtotal", false, "BHJTOTAL");
        public final static Property Bhjusenum = new Property(2, String.class, "bhjusenum", false, "BHJUSENUM");
        public final static Property Volume = new Property(3, String.class, "volume", false, "VOLUME");
        public final static Property Pannum = new Property(4, String.class, "pannum", false, "PANNUM");
        public final static Property Mixwarnnum = new Property(5, String.class, "mixwarnnum", false, "MIXWARNNUM");
        public final static Property Mixratio = new Property(6, String.class, "mixratio", false, "MIXRATIO");
        public final static Property Matlwarnnum = new Property(7, String.class, "matlwarnnum", false, "MATLWARNNUM");
        public final static Property Matlratio = new Property(8, String.class, "matlratio", false, "MATLRATIO");
        public final static Property Maltdisratio = new Property(9, String.class, "maltdisratio", false, "MALTDISRATIO");
    };


    public BhzInfoDao(DaoConfig config) {
        super(config);
    }
    
    public BhzInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BHZ_INFO' (" + //
                "'SECTIONNAME' TEXT PRIMARY KEY NOT NULL ," + // 0: sectionname
                "'BHJTOTAL' TEXT," + // 1: bhjtotal
                "'BHJUSENUM' TEXT," + // 2: bhjusenum
                "'VOLUME' TEXT," + // 3: volume
                "'PANNUM' TEXT," + // 4: pannum
                "'MIXWARNNUM' TEXT," + // 5: mixwarnnum
                "'MIXRATIO' TEXT," + // 6: mixratio
                "'MATLWARNNUM' TEXT," + // 7: matlwarnnum
                "'MATLRATIO' TEXT," + // 8: matlratio
                "'MALTDISRATIO' TEXT);"); // 9: maltdisratio
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BHZ_INFO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BhzInfo entity) {
        stmt.clearBindings();
 
        String sectionname = entity.getSectionname();
        if (sectionname != null) {
            stmt.bindString(1, sectionname);
        }
 
        String bhjtotal = entity.getBhjtotal();
        if (bhjtotal != null) {
            stmt.bindString(2, bhjtotal);
        }
 
        String bhjusenum = entity.getBhjusenum();
        if (bhjusenum != null) {
            stmt.bindString(3, bhjusenum);
        }
 
        String volume = entity.getVolume();
        if (volume != null) {
            stmt.bindString(4, volume);
        }
 
        String pannum = entity.getPannum();
        if (pannum != null) {
            stmt.bindString(5, pannum);
        }
 
        String mixwarnnum = entity.getMixwarnnum();
        if (mixwarnnum != null) {
            stmt.bindString(6, mixwarnnum);
        }
 
        String mixratio = entity.getMixratio();
        if (mixratio != null) {
            stmt.bindString(7, mixratio);
        }
 
        String matlwarnnum = entity.getMatlwarnnum();
        if (matlwarnnum != null) {
            stmt.bindString(8, matlwarnnum);
        }
 
        String matlratio = entity.getMatlratio();
        if (matlratio != null) {
            stmt.bindString(9, matlratio);
        }
 
        String maltdisratio = entity.getMaltdisratio();
        if (maltdisratio != null) {
            stmt.bindString(10, maltdisratio);
        }
    }

    /** @inheritdoc */
    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BhzInfo readEntity(Cursor cursor, int offset) {
        BhzInfo entity = new BhzInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sectionname
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // bhjtotal
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bhjusenum
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // volume
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // pannum
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mixwarnnum
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mixratio
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // matlwarnnum
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // matlratio
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // maltdisratio
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BhzInfo entity, int offset) {
        entity.setSectionname(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setBhjtotal(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBhjusenum(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setVolume(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPannum(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMixwarnnum(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMixratio(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMatlwarnnum(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMatlratio(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMaltdisratio(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected String updateKeyAfterInsert(BhzInfo entity, long rowId) {
        return entity.getSectionname();
    }
    
    /** @inheritdoc */
    @Override
    public String getKey(BhzInfo entity) {
        if(entity != null) {
            return entity.getSectionname();
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
