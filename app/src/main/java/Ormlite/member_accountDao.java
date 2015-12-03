package Ormlite;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//�ccount Dao��
public class member_accountDao {


	/* insert */
	public static int insert(DatabaseHelper databaseHelper, member_accountVo member_accountVo) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper.getMember_accountDao();
		if (exist(databaseHelper, member_accountVo)) {
			return 0;
		}
		return accountDao.create(member_accountVo);
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, member_accountVo member_accountVo) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
				.getMember_accountDao();
		QueryBuilder<member_accountVo, Integer> queryBuilder = accountDao
				.queryBuilder();
		try {
			queryBuilder.where()
					.eq(member_accountVo.FIELD_Account, member_accountVo.getAccount());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* update */
/*	public static int update(DatabaseHelper databaseHelper, member_accountVo member_accountVo) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
				.getMember_accountDao();
		try {
			return accountDao.update(member_accountVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}*/
    public static int update(DatabaseHelper databaseHelper, String columnvalue,String originalvalue, String column, String value) {
        RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
                .getMember_accountDao();
        UpdateBuilder<member_accountVo, Integer> updateBuilder = accountDao.updateBuilder();
        try {
            //判斷式 哪一欄 = 值
            updateBuilder.where().eq(columnvalue,originalvalue);
            // update the value of your field(s)
            updateBuilder.updateColumnValue(column,value);

            return updateBuilder.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
	/* delete */
	public static int delete(DatabaseHelper databaseHelper, member_accountVo member_accountVo) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
				.getMember_accountDao();
		try {
			return accountDao.delete(member_accountVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* selectRaw */
	public static member_accountVo getMember_accountVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
				.getMember_accountDao();
		QueryBuilder<member_accountVo, Integer> queryBuilder = accountDao
				.queryBuilder();
		try {

			List<member_accountVo> data = queryBuilder.where().raw("1=1").query();
			if (data.size() > 0) {
				return data.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* select by id */
	public static member_accountVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
				.getMember_accountDao();
		try {
			return accountDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/* selectRaw */
	public static List<member_accountVo> selectRaw(DatabaseHelper databaseHelper,
											String rawWhere) {
		RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
				.getMember_accountDao();
		QueryBuilder<member_accountVo, Integer> queryBuilder = accountDao
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
    public static List<member_accountVo> selectRawByNest(DatabaseHelper databaseHelper,String column1,String value1,String column2) {
        RuntimeExceptionDao<member_accountVo, Integer> accountDao = databaseHelper
                .getMember_accountDao();
        RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
                .getCase_masterDao();
        QueryBuilder<case_masterVo, Integer> subqueryBuilder = case_masterDao
                .queryBuilder();
        QueryBuilder<member_accountVo, Integer> queryBuilder = accountDao
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


}
