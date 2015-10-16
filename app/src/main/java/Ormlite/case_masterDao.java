package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//�ap_master Dao��
public class case_masterDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, case_masterVo case_masterVo)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			if (exist(connectionSource, case_masterVo))
			{
				return 0;
			}
			return case_masterDao.create(case_masterVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, case_masterVo case_masterVo)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			QueryBuilder<case_masterVo, Integer> queryBuilder = case_masterDao.queryBuilder();

			boolean ret=false;
			//�撣唾����
			queryBuilder.where().eq(case_masterVo.FIELD_Case_number,case_masterVo.getCase_number());
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
	public static int update(ConnectionSource connectionSource, case_masterVo case_masterVo)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			return case_masterDao.update(case_masterVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, case_masterVo case_masterVo)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			return case_masterDao.delete(case_masterVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static case_masterVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			return case_masterDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<case_masterVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			QueryBuilder<case_masterVo, Integer> queryBuilder = case_masterDao.queryBuilder();
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
	public static case_masterVo getcase_masterVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<case_masterVo, Integer> case_masterDao = DaoManager.createDao(connectionSource, case_masterVo.class);
			List<case_masterVo> data = case_masterDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 瘝����
				// case_masterVo acase_masterVo = new case_masterVo();
				// case_masterDao.insert(databaseHelper, acase_masterVo);
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