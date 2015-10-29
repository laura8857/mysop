package Ormlite;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.android.apptools.OpenHelperManager;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Demo";
    private static DatabaseHelper instance;


    // the DAO object we use to access the SimpleData table
    private Dao<User, Integer> simpleDao = null;
    private RuntimeExceptionDao<User, Integer> simpleRuntimeDao = null;



    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    class MyDbErrorHandler implements DatabaseErrorHandler {
//
//        @Override
//        public void onCorruption(SQLiteDatabase dbObj) {
//            // TODO Auto-generated method stub
//            // Back up the db or do some other stuff
//        }
//    }
public static DatabaseHelper databaseHelper;//

    public static DatabaseHelper getHelper(Context context) {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,
                    DatabaseHelper.class);
        }
        return databaseHelper;
    }
    /**
     * This is called when your application is upgraded and it has a higher
     * version number. This allows you to adjust the various data to match the
     * new version number.
     */


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);
            //TableUtils.dropTable(connectionSource, Group.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, member_accountVo.class);
            //TableUtils.createTable(connectionSource, Group.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long millis = System.currentTimeMillis();

        Log.i(DatabaseHelper.class.getName(),
                "created new entries in onCreate: " + millis);

    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */

    //【member_account Dao】
    private RuntimeExceptionDao<member_accountVo, Integer> menber_accountRuntimeDao = null;
    public RuntimeExceptionDao<member_accountVo, Integer> getMember_accountDao() {
        if ( menber_accountRuntimeDao == null) {
            menber_accountRuntimeDao = getRuntimeExceptionDao(member_accountVo.class);
        }
        return  menber_accountRuntimeDao;
    }

    //【rule_set】
    private RuntimeExceptionDao<rule_setVo, Integer> rule_setRuntimeDao = null;
    public RuntimeExceptionDao<rule_setVo, Integer> getRule_setDao() {
        if ( rule_setRuntimeDao== null) {
            rule_setRuntimeDao= getRuntimeExceptionDao(rule_setVo.class);
        }
        return  rule_setRuntimeDao;
    }

    public Dao<User, Integer> getDao() throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(User.class);
        }
        return simpleDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<User, Integer> getSimpleDataDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(User.class);
        }
        return simpleRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        simpleDao = null;
        simpleRuntimeDao = null;

        menber_accountRuntimeDao = null;
        rule_setRuntimeDao = null ;
    }


}
