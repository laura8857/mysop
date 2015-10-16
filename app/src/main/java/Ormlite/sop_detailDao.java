package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//【sop_detail Dao】
public class sop_detailDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, sop_detailVo sop_detailVo)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			if (exist(connectionSource, sop_detailVo))
			{
				return 0;
			}
			return sop_detailDao.create(sop_detailVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, sop_detailVo sop_detailVo)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao.queryBuilder();

			boolean ret=false;
			//查帳號存在性
			queryBuilder.where().eq(sop_detailVo.FIELD_Sop_number,sop_detailVo.getSop_number());
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
	public static int update(ConnectionSource connectionSource, sop_detailVo sop_detailVo)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			return sop_detailDao.update(sop_detailVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, sop_detailVo sop_detailVo)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			return sop_detailDao.delete(sop_detailVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static sop_detailVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			return sop_detailDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<sop_detailVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			QueryBuilder<sop_detailVo, Integer> queryBuilder = sop_detailDao.queryBuilder();
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
	public static sop_detailVo getsop_detailVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<sop_detailVo, Integer> sop_detailDao = DaoManager.createDao(connectionSource, sop_detailVo.class);
			List<sop_detailVo> data = sop_detailDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 沒有資料
				// sop_detailVo asop_detailVo = new sop_detailVo();
				// sop_detailDao.insert(databaseHelper, asop_detailVo);
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