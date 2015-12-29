package Ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by laura on 15/12/29.
 */
public class memoDao {
    /* insert */
    public static int insert(DatabaseHelper databaseHelper, memoVo memoVo) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper.getMemoDao();
        if (exist(databaseHelper, memoVo)) {
            return 0;
        }
        return memoDao.create(memoVo);
    }

    /* exist */
    public static boolean exist(DatabaseHelper databaseHelper, memoVo memoVo) {
        RuntimeExceptionDao<memoVo, Integer>memoDao = databaseHelper
                .getMemoDao();
        QueryBuilder<memoVo, Integer> queryBuilder = memoDao
                .queryBuilder();
        try {
            queryBuilder.where()
                    .eq(memoVo.FIELD_Id, String.valueOf(memoVo.getId()));
            //	.and()
            //	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
            return queryBuilder.query().size() > 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* exist */
    public static boolean exist(DatabaseHelper databaseHelper, system_messageVo system_messageVo) {
        RuntimeExceptionDao<system_messageVo, Integer>system_messageDao = databaseHelper
                .getSystem_messageDao();
        QueryBuilder<system_messageVo, Integer> queryBuilder = system_messageDao
                .queryBuilder();
        try {
            queryBuilder.where()
                    .eq(system_messageVo.FIELD_Id, String.valueOf(system_messageVo.getId()));
            //	.and()
            //	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
            return queryBuilder.query().size() > 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /* update */
    public static int update(DatabaseHelper databaseHelper, String columnvalue, String originalvalue, String column, String value) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper
                .getMemoDao();
        UpdateBuilder<memoVo, Integer> updateBuilder = memoDao.updateBuilder();
        try {
            //判斷式 哪一欄 = 值
            updateBuilder.where().eq(columnvalue, originalvalue);
            // update the value of your field(s)
            updateBuilder.updateColumnValue(column, value);

            return updateBuilder.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static int delete(DatabaseHelper databaseHelper, String columnvalue, String originalvalue) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper
                .getMemoDao();
        DeleteBuilder<memoVo,Integer> deleteBuilder = memoDao.deleteBuilder();
        try {
            //判斷式 哪一欄 = 值
            deleteBuilder.where().eq(columnvalue, originalvalue);

            return deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* select by id */
    public static memoVo select(DatabaseHelper databaseHelper, int id) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper
                .getMemoDao();
        try {
            return memoDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static  memoVo getMemoVo(DatabaseHelper databaseHelper) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper
                .getMemoDao();
        QueryBuilder<memoVo, Integer> queryBuilder = memoDao
                .queryBuilder();
        try {

            List<memoVo> data = queryBuilder.where().raw("1=1").query();
            if (data.size() > 0) {
                return data.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static List<memoVo> selectRaw(DatabaseHelper databaseHelper,
                                                   String rawWhere) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper
                .getMemoDao();
        QueryBuilder<memoVo, Integer> queryBuilder = memoDao
                .queryBuilder();
        try {
            queryBuilder.where().raw(rawWhere);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static List<memoVo> selectRaw2(DatabaseHelper databaseHelper) {
        RuntimeExceptionDao<memoVo, Integer> memoDao = databaseHelper
                .getMemoDao();
        QueryBuilder<memoVo, Integer> queryBuilder = memoDao
                .queryBuilder();
        try {

            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
