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
public class comment_recordDao
{

	/* insert */
	public static int insert(DatabaseHelper databaseHelper, comment_recordVo comment_recordVo) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper.getComment_recordDao();
		if (exist(databaseHelper, comment_recordVo)) {
			return 0;
		}
		return comment_recordDao.create(comment_recordVo);
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper,  comment_recordVo comment_recordVo) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper
				.getComment_recordDao();
		QueryBuilder<comment_recordVo, Integer> queryBuilder = comment_recordDao
				.queryBuilder();
		try {
			queryBuilder.where()
					.eq(comment_recordVo.FIELD_Comment_number, comment_recordVo.getComment_number());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/* update */
	public static int update(DatabaseHelper databaseHelper, comment_recordVo comment_recordVo) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper
				.getComment_recordDao();
		try {
			return comment_recordDao.update(comment_recordVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	/* delete */
	public static int delete(DatabaseHelper databaseHelper, comment_recordVo comment_recordVo) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper
				.getComment_recordDao();
		try {
			return comment_recordDao.delete(comment_recordVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static comment_recordVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper
				.getComment_recordDao();
		try {
			return comment_recordDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<comment_recordVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<comment_recordVo, String> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			QueryBuilder<comment_recordVo, String> queryBuilder = comment_recordDao.queryBuilder();
			queryBuilder.where().raw(rawWhere);
			String sql = queryBuilder.prepareStatementString();
			return queryBuilder.query();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<comment_recordVo> selectRaw(DatabaseHelper databaseHelper,
												   String rawWhere) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper
				.getComment_recordDao();
		QueryBuilder<comment_recordVo, Integer> queryBuilder = comment_recordDao
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
	public static comment_recordVo getComment_recordVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<comment_recordVo, Integer> comment_recordDao = databaseHelper
				.getComment_recordDao();
		QueryBuilder<comment_recordVo, Integer> queryBuilder = comment_recordDao
				.queryBuilder();
		try {

			List<comment_recordVo> data = queryBuilder.where().raw("1=1").query();
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