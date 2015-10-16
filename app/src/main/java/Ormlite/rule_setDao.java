package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【rule_set Dao】
public class rule_setDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, rule_setVo rule_setVo)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			if (exist(connectionSource, rule_setVo))
			{
				return 0;
			}
			return rule_setDao.create(rule_setVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, rule_setVo rule_setVo)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			QueryBuilder<rule_setVo, Integer> queryBuilder = rule_setDao.queryBuilder();

			boolean ret=false;
			//查帳號存在性
			queryBuilder.where().eq(rule_setVo.FIELD_Rule_number,rule_setVo.getRule_number());
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
	public static int update(ConnectionSource connectionSource, rule_setVo rule_setVo)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			return rule_setDao.update(rule_setVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, rule_setVo rule_setVo)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			return rule_setDao.delete(rule_setVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static rule_setVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			return rule_setDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<rule_setVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			QueryBuilder<rule_setVo, Integer> queryBuilder = rule_setDao.queryBuilder();
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
	public static rule_setVo getrule_setVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<rule_setVo, Integer> rule_setDao = DaoManager.createDao(connectionSource, rule_setVo.class);
			List<rule_setVo> data = rule_setDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 沒有資料
				// rule_setVo arule_setVo = new rule_setVo();
				// rule_setDao.insert(databaseHelper, arule_setVo);
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