package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【map_master Dao】
public class map_masterDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, map_masterVo map_masterVo)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			if (exist(connectionSource, map_masterVo))
			{
				return 0;
			}
			return map_masterDao.create(map_masterVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, map_masterVo map_masterVo)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			QueryBuilder<map_masterVo, Integer> queryBuilder = map_masterDao.queryBuilder();

			boolean ret=false;
			//查帳號存在性
			queryBuilder.where().eq(map_masterVo.FIELD_Sop_number,map_masterVo.getSop_number());
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
	public static int update(ConnectionSource connectionSource, map_masterVo map_masterVo)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			return map_masterDao.update(map_masterVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, map_masterVo map_masterVo)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			return map_masterDao.delete(map_masterVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static map_masterVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			return map_masterDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<map_masterVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			QueryBuilder<map_masterVo, Integer> queryBuilder = map_masterDao.queryBuilder();
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
	public static map_masterVo getmap_masterVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<map_masterVo, Integer> map_masterDao = DaoManager.createDao(connectionSource, map_masterVo.class);
			List<map_masterVo> data = map_masterDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 沒有資料
				// map_masterVo amap_masterVo = new map_masterVo();
				// map_masterDao.insert(databaseHelper, amap_masterVo);
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