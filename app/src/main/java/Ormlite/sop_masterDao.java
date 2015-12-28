package Ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
//import com.ck.ap.DatabaseHelper;

//【sop_master Dao】
public class sop_masterDao
{
    /* insert */
    public static int insert(DatabaseHelper databaseHelper, sop_masterVo sop_masterVo) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper.getSop_masterDao();
        if (exist(databaseHelper, sop_masterVo)) {
            return 0;
        }
        return sop_masterDao.create(sop_masterVo);
    }

	/* exist */
    public static boolean exist(DatabaseHelper databaseHelper, sop_masterVo sop_masterVo) {
        RuntimeExceptionDao<sop_masterVo, Integer>sop_masterDao = databaseHelper
                .getSop_masterDao();
        QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao
                .queryBuilder();
        try {
            queryBuilder.where()
                    .eq(sop_masterVo.FIELD_Sop_number, sop_masterVo.getSop_number());
            //	.and()
            //	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
            return queryBuilder.query().size() > 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* update */
    public static int update(DatabaseHelper databaseHelper, sop_masterVo sop_masterVo) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        try {
            return sop_masterDao.update(sop_masterVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int delete(DatabaseHelper databaseHelper, String columnvalue, String originalvalue) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterVoDao = databaseHelper
                .getSop_masterDao();
        DeleteBuilder<sop_masterVo,Integer> deleteBuilder = sop_masterVoDao.deleteBuilder();
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
    public static sop_masterVo select(DatabaseHelper databaseHelper, int id) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        try {
            return sop_masterDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static sop_masterVo getSop_masterVo(DatabaseHelper databaseHelper) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao
                .queryBuilder();
        try {

            List<sop_masterVo> data = queryBuilder.where().raw("1=1").query();
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
    public static List<sop_masterVo> selectRaw(DatabaseHelper databaseHelper,
                                                   String rawWhere) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao
                .queryBuilder();
        try {
            queryBuilder.where().raw(rawWhere);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRawByNest */
    public static List<sop_masterVo> selectRawByNest(DatabaseHelper databaseHelper,String column1,String value1,String column2) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        QueryBuilder<case_masterVo, Integer> subqueryBuilder = case_masterDao
                .queryBuilder();
        QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao
                .queryBuilder();
        try {
            subqueryBuilder.where().eq(column1,value1);
            //Log.d("TEST NEST",subqueryBuilder.query().get(0).getAccount());
            // in using the sub-query
            subqueryBuilder.selectColumns(column2);
            queryBuilder.where().in(column2, subqueryBuilder);
            //Log.d("TEST NEST",queryBuilder.query().get(0).getAccount());
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<sop_masterVo> join(DatabaseHelper databaseHelper, String rawWhere)  {
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        QueryBuilder<case_masterVo, Integer> queryBuilder1 = case_masterDao
                .queryBuilder();
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        QueryBuilder<sop_masterVo, Integer> queryBuilder2 = sop_masterDao
                .queryBuilder();
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        QueryBuilder<sop_detailVo, Integer> queryBuilder3 =sop_detailDao
                .queryBuilder();

        try {
            queryBuilder1.join(queryBuilder3);
            queryBuilder2.join(queryBuilder1);
            queryBuilder2.where().raw(rawWhere);
            return queryBuilder2.query();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<sop_masterVo> selectRawByNest1(DatabaseHelper databaseHelper,String column1,String value1,String column2, String column3, String value3) {
        RuntimeExceptionDao<sop_masterVo, Integer> sop_masterDao = databaseHelper
                .getSop_masterDao();
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        QueryBuilder<case_masterVo, Integer> subqueryBuilder = case_masterDao
                .queryBuilder();
        QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao
                .queryBuilder();
        QueryBuilder<sop_detailVo, Integer> subqueryBuilder2 = sop_detailDao.queryBuilder();
        try {
            subqueryBuilder.where().eq(column1, value1);
            //Log.d("TEST NEST",subqueryBuilder.query().get(0).getAccount());
            // in using the sub-query
            subqueryBuilder2.where().eq(column3, value3);
            subqueryBuilder2.selectColumns(column2);
            subqueryBuilder.selectColumns(column2);
            queryBuilder.where().in(column2, subqueryBuilder ).in(column2, subqueryBuilder2);
            //Log.d("TEST NEST",queryBuilder.query().get(0).getAccount());
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}