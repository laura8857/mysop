package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【rule_set Dao】
public class rule_setDao
{
	/* insert */
	public static int insert(DatabaseHelper databaseHelper, rule_setVo rule_setVo) {
		RuntimeExceptionDao<rule_setVo, Integer> rulesetDao = databaseHelper.getRule_setDao();
		if (exist(databaseHelper, rule_setVo)) {
			return 0;
		}
		return rulesetDao.create(rule_setVo);
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, rule_setVo rule_setVo ) {
		RuntimeExceptionDao<rule_setVo , Integer> rulestDao = databaseHelper
				.getRule_setDao();
		QueryBuilder<rule_setVo, Integer> queryBuilder = rulestDao
				.queryBuilder();
		try {
			queryBuilder.where()
					.eq(rule_setVo.FIELD_Rule_number, rule_setVo.getRule_number());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* update */
	public static int update(DatabaseHelper databaseHelper, rule_setVo rule_setVo) {
		RuntimeExceptionDao<rule_setVo, Integer> rulesetDao = databaseHelper
				.getRule_setDao();
		try {
			return rulesetDao.update(rule_setVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(DatabaseHelper databaseHelper, rule_setVo rule_setVo) {
		RuntimeExceptionDao<rule_setVo, Integer> rulesetDao = databaseHelper
				.getRule_setDao();
		try {
			return rulesetDao.delete(rule_setVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* selectRaw */
	public static rule_setVo getRule_setVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<rule_setVo, Integer> rulesetDao = databaseHelper
				.getRule_setDao();
		QueryBuilder<rule_setVo, Integer> queryBuilder = rulesetDao
				.queryBuilder();
		try {

			List<rule_setVo> data = queryBuilder.where().raw("1=1").query();
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
	public static rule_setVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<rule_setVo, Integer> rulesetDao = databaseHelper
				.getRule_setDao();
		try {
			return rulesetDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<rule_setVo> selectRaw(DatabaseHelper databaseHelper,
											String rawWhere) {
		RuntimeExceptionDao<rule_setVo, Integer> rulesetDao = databaseHelper
				.getRule_setDao();
		QueryBuilder<rule_setVo, Integer> queryBuilder = rulesetDao
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