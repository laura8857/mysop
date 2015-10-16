package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//�ap_location Dao��
public class map_locationDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, map_locationVo map_locationVo)
	{
		try
		{
			Dao<map_locationVo, Integer> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			if (exist(connectionSource, map_locationVo))
			{
				return 0;
			}
			return map_locationDao.create(map_locationVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, map_locationVo map_locationVo)
	{
		try
		{
			Dao<map_locationVo, Integer> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			QueryBuilder<map_locationVo, Integer> queryBuilder = map_locationDao.queryBuilder();

			boolean ret=false;
			//�撣唾����
			queryBuilder.where().eq(map_locationVo.FIELD_Step_number,map_locationVo.getStep_number());
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
	public static int update(ConnectionSource connectionSource, map_locationVo map_locationVo)
	{
		try
		{
			Dao<map_locationVo, Integer> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			return map_locationDao.update(map_locationVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, map_locationVo map_locationVo)
	{
		try
		{
			Dao<map_locationVo, Integer> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			return map_locationDao.delete(map_locationVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static map_locationVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<map_locationVo, Integer> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			return map_locationDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<map_locationVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<map_locationVo, String> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			QueryBuilder<map_locationVo, String> queryBuilder = map_locationDao.queryBuilder();
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

	// 頛����摰惜蝝���
	public static map_locationVo getmap_locationVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<map_locationVo, Integer> map_locationDao = DaoManager.createDao(connectionSource, map_locationVo.class);
			List<map_locationVo> data = map_locationDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 瘝����
				// map_locationVo amap_locationVo = new map_locationVo();
				// map_locationDao.insert(databaseHelper, amap_locationVo);
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