package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【sop_master Dao】
public class sop_masterDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, sop_masterVo sop_masterVo)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			if (exist(connectionSource, sop_masterVo))
			{
				return 0;
			}
			return sop_masterDao.create(sop_masterVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, sop_masterVo sop_masterVo)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao.queryBuilder();

			boolean ret=false;
			//查帳號存在性
			queryBuilder.where().eq(sop_masterVo.FIELD_Sop_number,sop_masterVo.getSop_number());
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
	public static int update(ConnectionSource connectionSource, sop_masterVo sop_masterVo)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			return sop_masterDao.update(sop_masterVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, sop_masterVo sop_masterVo)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			return sop_masterDao.delete(sop_masterVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static sop_masterVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			return sop_masterDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<sop_masterVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			QueryBuilder<sop_masterVo, Integer> queryBuilder = sop_masterDao.queryBuilder();
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
	public static sop_masterVo getsop_masterVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<sop_masterVo, Integer> sop_masterDao = DaoManager.createDao(connectionSource, sop_masterVo.class);
			List<sop_masterVo> data = sop_masterDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 沒有資料
				// sop_masterVo asop_masterVo = new sop_masterVo();
				// sop_masterDao.insert(databaseHelper, asop_masterVo);
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