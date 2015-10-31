package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//�ap_master Dao��
public class case_masterDao
{


	/* insert */
	public static int insert(DatabaseHelper databaseHelper, case_masterVo case_masterVo) {
		RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper.getCase_masterDao();
		if (exist(databaseHelper, case_masterVo)) {
			return 0;
		}
		return case_masterDao.create(case_masterVo);
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, case_masterVo case_masterVo) {
		RuntimeExceptionDao<case_masterVo, Integer>case_masterDao = databaseHelper
				.getCase_masterDao();
		QueryBuilder<case_masterVo, Integer> queryBuilder = case_masterDao
				.queryBuilder();
		try {
			queryBuilder.where()
					.eq(case_masterVo.FIELD_Account, case_masterVo.getAccount());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* update */
	public static int update(DatabaseHelper databaseHelper, case_masterVo case_masterVo) {
		RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
				.getCase_masterDao();
		try {
			return case_masterDao.update(case_masterVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(DatabaseHelper databaseHelper, case_masterVo case_masterVo) {
		RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
				.getCase_masterDao();
		try {
			return case_masterDao.delete(case_masterVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* selectRaw */
	public static case_masterVo getCase_masterVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
				.getCase_masterDao();
		QueryBuilder<case_masterVo, Integer> queryBuilder = case_masterDao
				.queryBuilder();
		try {

			List<case_masterVo> data = queryBuilder.where().raw("1=1").query();
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
	public static case_masterVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
				.getCase_masterDao();
		try {
			return case_masterDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/* selectRaw */
	public static List<case_masterVo> selectRaw(DatabaseHelper databaseHelper,
												   String rawWhere) {
		RuntimeExceptionDao<case_masterVo, Integer> case_masterDao = databaseHelper
				.getCase_masterDao();
		QueryBuilder<case_masterVo, Integer> queryBuilder = case_masterDao
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