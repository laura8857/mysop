package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【Account Dao】
public class step_recordDao
{
    /* insert */
    public static int insert(DatabaseHelper databaseHelper, step_recordVo step_recordVo) {
        RuntimeExceptionDao<step_recordVo, Integer> step_recordDao = databaseHelper.getStep_recordDao();
        if (exist(databaseHelper, step_recordVo)) {
            return 0;
        }
        return step_recordDao.create(step_recordVo);
    }

    /* exist */
    public static boolean exist(DatabaseHelper databaseHelper, step_recordVo step_recordVo) {
        RuntimeExceptionDao<step_recordVo, Integer>step_recordDao = databaseHelper
                .getStep_recordDao();
        QueryBuilder<step_recordVo, Integer> queryBuilder = step_recordDao
                .queryBuilder();
        try {
            queryBuilder.where()
                    .eq(step_recordVo.FIELD_Step_number, step_recordVo.getStep_number());
            //	.and()
            //	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
            return queryBuilder.query().size() > 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /* update */
    public static int update(DatabaseHelper databaseHelper, step_recordVo step_recordVo) {
        RuntimeExceptionDao<step_recordVo, Integer> step_recordDao = databaseHelper
                .getStep_recordDao();
        try {
            return step_recordDao.update(step_recordVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* delete */
    public static int delete(DatabaseHelper databaseHelper, step_recordVo step_recordVo) {
        RuntimeExceptionDao<step_recordVo, Integer> step_recordDao = databaseHelper
                .getStep_recordDao();
        try {
            return step_recordDao.delete(step_recordVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* select by id */
    public static step_recordVo select(DatabaseHelper databaseHelper, int id) {
        RuntimeExceptionDao<step_recordVo, Integer> step_recordDao = databaseHelper
                .getStep_recordDao();
        try {
            return step_recordDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static  step_recordVo getStep_recordVo(DatabaseHelper databaseHelper) {
        RuntimeExceptionDao<step_recordVo, Integer> step_recordDao = databaseHelper
                .getStep_recordDao();
        QueryBuilder<step_recordVo, Integer> queryBuilder = step_recordDao
                .queryBuilder();
        try {

            List<step_recordVo> data = queryBuilder.where().raw("1=1").query();
            if (data.size() > 0) {
                return data.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}