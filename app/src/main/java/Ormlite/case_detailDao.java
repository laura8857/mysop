package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class case_detailDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, case_detailVo case_detailVo)
	{
		try
		{
			Dao<case_detailVo, Integer> case_detailDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			if (exist(connectionSource, case_detailVo))
			{
				return 0;
			}
			return case_detailDao.create(case_detailVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, case_detailVo case_detailVo)
	{
		try
		{
			Dao<case_detailVo, Integer> case_detailDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			QueryBuilder<case_detailVo, Integer> queryBuilder = case_detailDao.queryBuilder();

			boolean ret=false;
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(case_detailVo.FIELD_Case_number,case_detailVo.getCase_number());
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
	public static int update(ConnectionSource connectionSource, case_detailVo case_detailVo)
	{
		try
		{
			Dao<case_detailVo, Integer> case_detailDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			return case_detailDao.update(case_detailVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, case_detailVo case_detailVo)
	{
		try
		{
			Dao<case_detailVo, Integer> case_detailDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			return case_detailDao.delete(case_detailVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static case_detailVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<case_detailVo, Integer> case_detailDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			return case_detailDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<case_detailVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<case_detailVo, String> case_detailDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			QueryBuilder<case_detailVo, String> queryBuilder = case_detailDao.queryBuilder();
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
	public static case_detailVo getcase_detailVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<case_detailVo, Integer> Case_numberDao = DaoManager.createDao(connectionSource, case_detailVo.class);
			List<case_detailVo> data = Case_numberDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// ���蕭�嚙踝嚙踝蕭
				// Case_numberVo aCase_numberVo = new Case_numberVo();
				// Case_numberDao.insert(databaseHelper, aCase_numberVo);
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