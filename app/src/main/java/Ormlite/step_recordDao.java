package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【Account Dao】
public class step_recordDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, step_recordVo step_recordVo)
	{
		try
		{
			Dao<step_recordVo, Integer> step_recordDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			if (exist(connectionSource, step_recordVo))
			{
				return 0;
			}
			return step_recordDao.create(step_recordVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, step_recordVo step_recordVo)
	{
		try
		{
			Dao<step_recordVo, Integer> step_recordDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			QueryBuilder<step_recordVo, Integer> queryBuilder = step_recordDao.queryBuilder();

			boolean ret=false;
			//查帳號存在性
			queryBuilder.where().eq(step_recordVo.FIELD_Step_number,step_recordVo.getStep_number());
			ret = queryBuilder.query().size() > 0 ? true : false;
			
			return ret;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/* update */
	public static int update(ConnectionSource connectionSource, step_recordVo step_recordVo)
	{
		try
		{
			Dao<step_recordVo, Integer> step_recordDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			return step_recordDao.update(step_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, step_recordVo step_recordVo)
	{
		try
		{
			Dao<step_recordVo, Integer> step_recordDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			return step_recordDao.delete(step_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static step_recordVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<step_recordVo, Integer> step_recordDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			return step_recordDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<step_recordVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<step_recordVo, Integer> step_recordDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			QueryBuilder<step_recordVo, Integer> queryBuilder = step_recordDao.queryBuilder();
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

	// 載入所有特定層級資料
	public static step_recordVo getstep_recordVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<step_recordVo, Integer> accountDao = DaoManager.createDao(connectionSource, step_recordVo.class);
			List<step_recordVo> data = accountDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 沒有資料
				// AccountVo aAccountVo = new AccountVo();
				// AccountDao.insert(databaseHelper, aAccountVo);
				return null;
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}