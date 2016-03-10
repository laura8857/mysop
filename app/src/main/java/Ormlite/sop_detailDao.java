package Ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
//import com.ck.ap.DatabaseHelper;

//【sop_detail Dao】
public class sop_detailDao
{
    /* insert */
    public static int insert(DatabaseHelper databaseHelper, sop_detailVo sop_detailVo) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper.getSop_detailDao();
        if (exist(databaseHelper, sop_detailVo)) {
            return 0;
        }
        return sop_detailDao.create(sop_detailVo);
    }

    /* exist */
    public static boolean exist(DatabaseHelper databaseHelper, sop_detailVo sop_detailVo) {
        RuntimeExceptionDao<sop_detailVo, Integer>sop_detailDao = databaseHelper
                .getSop_detailDao();
        QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao
                .queryBuilder();
        try {
            queryBuilder.where()
                    .eq(sop_detailVo.FIELD_Step_number, sop_detailVo.getStep_number());
            //	.and()
            //	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
            return queryBuilder.query().size() > 0 ? true : false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* update */
    public static int update(DatabaseHelper databaseHelper, sop_detailVo sop_detailVo) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        try {
            return sop_detailDao.update(sop_detailVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//    /* delete */
//    public static int delete(DatabaseHelper databaseHelper, sop_detailVo sop_detailVo) {
//        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
//                .getSop_detailDao();
//        try {
//            return sop_detailDao.delete(sop_detailVo);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public static int delete(DatabaseHelper databaseHelper, String columnvalue, String originalvalue) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        DeleteBuilder<sop_detailVo,Integer> deleteBuilder = sop_detailDao.deleteBuilder();
        try {
            //判斷式 哪一欄 = 值
            deleteBuilder.where().eq(columnvalue, originalvalue);

            return deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

	//* select by id */
    public static sop_detailVo select(DatabaseHelper databaseHelper, int id) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        try {
            return sop_detailDao.queryForId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw */
    public static List<sop_detailVo> selectRaw(DatabaseHelper databaseHelper,
                                                String rawWhere) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao
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
    public static List<sop_detailVo> selectRawByNest(DatabaseHelper databaseHelper,String column1,String value1,String column2) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        QueryBuilder<case_masterVo, Integer> subqueryBuilder = case_masterDao
                .queryBuilder();
        QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao
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

    /* selectRawByNest */
    public static List<sop_detailVo> selectRawByNest2(DatabaseHelper databaseHelper,String column1,String value1,String column2,String column3,String value3) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        QueryBuilder<case_masterVo, Integer> subqueryBuilder = case_masterDao
                .queryBuilder();
        QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao
                .queryBuilder();
        try {
            subqueryBuilder.where().eq(column1,value1);
            // in using the sub-query
            subqueryBuilder.selectColumns(column2);
            queryBuilder.where().in(column2, subqueryBuilder).and().in(column3,value3);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<sop_detailVo> selectRawByNest3(DatabaseHelper databaseHelper,String column1,String value1,String column2,String column3,String value3) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        QueryBuilder<case_masterVo, Integer> subqueryBuilder = case_masterDao
                .queryBuilder();
        QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao
                .queryBuilder();
        try {
            subqueryBuilder.where().eq(column1,value1);
            //Log.d("TEST NEST",subqueryBuilder.query().get(0).getAccount());
            // in using the sub-query
            subqueryBuilder.selectColumns(column2);
            queryBuilder.where().in(column2, subqueryBuilder);
            queryBuilder.where().eq(column3, value3);

            //Log.d("TEST NEST",queryBuilder.query().get(0).getAccount());
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* selectRaw2 */
    public static List<sop_detailVo> selectRaw2(DatabaseHelper databaseHelper,
                                                 String rawWhere ,String rawWhere2,String rawWhere3) {
        RuntimeExceptionDao<sop_detailVo, Integer> sop_detailDao = databaseHelper
                .getSop_detailDao();
        QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao
                .queryBuilder();
        try {
            queryBuilder.where().raw(rawWhere).and().raw(rawWhere2).and().raw(rawWhere3);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}