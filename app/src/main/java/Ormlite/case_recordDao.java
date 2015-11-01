package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class case_recordDao
{

	/* insert */
	public static int insert(DatabaseHelper databaseHelper, case_recordVo case_recordVo) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper.getCase_recordDao();
		if (exist(databaseHelper, case_recordVo)) {
			return 0;
		}
		return case_recordDao.create(case_recordVo);
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, case_recordVo case_recordVo) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao
				.queryBuilder();
		try {
			queryBuilder.where()
					.eq(case_recordVo.FIELD_Case_number, case_recordVo.getCase_number());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* update */
	public static int update(DatabaseHelper databaseHelper,case_recordVo case_recordVo) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		try {
			return case_recordDao.update(case_recordVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(DatabaseHelper databaseHelper, case_recordVo case_recordVo) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		try {
			return case_recordDao.delete(case_recordVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* selectRaw */
	public static case_recordVo getCase_recordVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao
				.queryBuilder();
		try {

			List<case_recordVo> data = queryBuilder.where().raw("1=1").query();
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
	public static case_recordVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		try {
			return case_recordDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/* selectRaw */
	public static List<case_recordVo> selectRaw(DatabaseHelper databaseHelper,
												   String rawWhere) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao
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