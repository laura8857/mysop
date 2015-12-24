package Ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kelly on 2015/12/24.
 */
public class system_messageDao {
    /* insert */
    public static int insert(DatabaseHelper databaseHelper, system_messageVo system_messageVo) {
        RuntimeExceptionDao<system_messageVo, Integer> system_messageDao = databaseHelper.getSystem_messageDao();
        if (exist(databaseHelper, system_messageVo)) {
            return 0;
        }
        return system_messageDao.create(system_messageVo);
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
    public static int update(DatabaseHelper databaseHelper, system_messageVo system_messageVo) {
        RuntimeExceptionDao<system_messageVo, Integer> system_messageDao = databaseHelper
                .getSystem_messageDao();
        try {
            return system_messageDao.update(system_messageVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* delete */
    public static int delete(DatabaseHelper databaseHelper, system_messageVo system_messageVo) {
        RuntimeExceptionDao<system_messageVo, Integer> system_messageDao = databaseHelper
                .getSystem_messageDao();
        try {
            return system_messageDao.delete(system_messageVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* select by id */
    public static system_messageVo select(DatabaseHelper databaseHelper, int id) {
        RuntimeExceptionDao<system_messageVo, Integer> system_messageDao = databaseHelper
                .getSystem_messageDao();
        try {
            return system_messageDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static  system_messageVo getSystem_messageVo(DatabaseHelper databaseHelper) {
        RuntimeExceptionDao<system_messageVo, Integer> system_messageDao = databaseHelper
                .getSystem_messageDao();
        QueryBuilder<system_messageVo, Integer> queryBuilder = system_messageDao
                .queryBuilder();
        try {

            List<system_messageVo> data = queryBuilder.where().raw("1=1").query();
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
    public static List<system_messageVo> selectRaw(DatabaseHelper databaseHelper,
                                                String rawWhere) {
        RuntimeExceptionDao<system_messageVo, Integer> system_messageDao = databaseHelper
                .getSystem_messageDao();
        QueryBuilder<system_messageVo, Integer> queryBuilder = system_messageDao
                .queryBuilder();
        try {
            queryBuilder.where().raw(rawWhere);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
