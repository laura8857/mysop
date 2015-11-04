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
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.createTable(connectionSource, member_accountVo.class);
            TableUtils.createTable(connectionSource, rule_setVo.class);
            TableUtils.createTable(connectionSource, case_masterVo.class);
            TableUtils.createTable(connectionSource, case_recordVo.class);
            TableUtils.createTable(connectionSource, comment_recordVo.class);
            TableUtils.createTable(connectionSource, like_recordVo.class);
            TableUtils.createTable(connectionSource, login_recordVo.class);
            // TableUtils.dropTable(connectionSource, B2BMVo.class, true);
            // after we drop the old databases, we create the new ones
            // onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }




    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, member_accountVo.class);
            TableUtils.createTable(connectionSource, rule_setVo.class);
            TableUtils.createTable(connectionSource, case_masterVo.class);
            TableUtils.createTable(connectionSource, case_recordVo.class);
            TableUtils.createTable(connectionSource, comment_recordVo.class);
            TableUtils.createTable(connectionSource, like_recordVo.class);
            TableUtils.createTable(connectionSource, login_recordVo.class);

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

    //【rule_set Dao】
    private RuntimeExceptionDao<rule_setVo, Integer> rule_setRuntimeDao = null;
    public RuntimeExceptionDao<rule_setVo, Integer> getRule_setDao() {
        if ( rule_setRuntimeDao== null) {
            rule_setRuntimeDao= getRuntimeExceptionDao(rule_setVo.class);
        }
        return  rule_setRuntimeDao;
    }

    //【case_masterDao】
    private RuntimeExceptionDao<case_masterVo, Integer> case_masterRuntimeDao = null;
    public RuntimeExceptionDao<case_masterVo, Integer> getCase_masterDao() {
        if ( case_masterRuntimeDao == null) {
            case_masterRuntimeDao = getRuntimeExceptionDao(case_masterVo.class);
        }
        return  case_masterRuntimeDao;
    }

    //【case_recordDao】
    private RuntimeExceptionDao<case_recordVo, Integer> case_recordRuntimeDao = null;
    public RuntimeExceptionDao<case_recordVo, Integer> getCase_recordDao() {
        if ( case_recordRuntimeDao == null) {
            case_recordRuntimeDao = getRuntimeExceptionDao(case_recordVo.class);
        }
        return  case_recordRuntimeDao;
    }

    //【comment_recordDao】
    private RuntimeExceptionDao<comment_recordVo, Integer> comment_recordRuntimeDao = null;
    public RuntimeExceptionDao<comment_recordVo, Integer> getComment_recordDao() {
        if ( comment_recordRuntimeDao == null) {
            comment_recordRuntimeDao = getRuntimeExceptionDao(comment_recordVo.class);
        }
        return comment_recordRuntimeDao;
    }

    //【like_recordDao】
    private RuntimeExceptionDao<like_recordVo, Integer> like_recordRuntimeDao = null;
    public RuntimeExceptionDao<like_recordVo, Integer> getLike_recordDao() {
        if ( like_recordRuntimeDao == null) {
            like_recordRuntimeDao = getRuntimeExceptionDao(like_recordVo.class);
        }
        return like_recordRuntimeDao;
    }

    //【login_recordDao】
    private RuntimeExceptionDao<login_recordVo, Integer> login_recordRuntimeDao = null;
    public RuntimeExceptionDao<login_recordVo, Integer> getLogin_recordDao() {
        if ( login_recordRuntimeDao == null) {
            login_recordRuntimeDao = getRuntimeExceptionDao(login_recordVo.class);
        }
        return login_recordRuntimeDao;
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
        case_masterRuntimeDao = null;
        case_recordRuntimeDao = null ;
        comment_recordRuntimeDao = null;
        like_recordRuntimeDao = null;
        login_recordRuntimeDao = null;
    }


}
