package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class map_do_locationDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, map_do_locationVo map_do_locationVo)
	{
		try
		{
			Dao<map_do_locationVo, Integer> map_do_locationDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			if (exist(connectionSource, map_do_locationVo))
			{
				return 0;
			}
			return map_do_locationDao.create(map_do_locationVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, map_do_locationVo map_do_locationVo)
	{
		try
		{
			Dao<map_do_locationVo, Integer> map_do_locationDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			QueryBuilder<map_do_locationVo, Integer> queryBuilder = map_do_locationDao.queryBuilder();

			boolean ret=false;
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(map_do_locationVo.FIELD_Case_number,map_do_locationVo.getCase_number());
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
	public static int update(ConnectionSource connectionSource, map_do_locationVo map_do_locationVo)
	{
		try
		{
			Dao<map_do_locationVo, Integer> map_do_locationDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			return map_do_locationDao.update(map_do_locationVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, map_do_locationVo map_do_locationVo)
	{
		try
		{
			Dao<map_do_locationVo, Integer> map_do_locationDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			return map_do_locationDao.delete(map_do_locationVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static map_do_locationVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<map_do_locationVo, Integer> map_do_locationDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			return map_do_locationDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<map_do_locationVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<map_do_locationVo, String> map_do_locationDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			QueryBuilder<map_do_locationVo, String> queryBuilder = map_do_locationDao.queryBuilder();
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

	// ���嚙踝蕭嚙踝蕭���謍唳��奕嚙踝嚙踝蕭
	public static map_do_locationVo getmap_do_locationVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<map_do_locationVo, Integer> accountDao = DaoManager.createDao(connectionSource, map_do_locationVo.class);
			List<map_do_locationVo> data = accountDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// ���蕭�嚙踝嚙踝蕭
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