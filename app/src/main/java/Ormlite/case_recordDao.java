package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class case_recordDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, case_recordVo case_recordVo)
	{
		try
		{
			Dao<case_recordVo, Integer> case_recordDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			if (exist(connectionSource, case_recordVo))
			{
				return 0;
			}
			return case_recordDao.create(case_recordVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, case_recordVo case_recordVo)
	{
		try
		{
			Dao<case_recordVo, Integer> case_recordDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao.queryBuilder();

			boolean ret=false;
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(case_recordVo.FIELD_Case_number,case_recordVo.getCase_number());
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
	public static int update(ConnectionSource connectionSource, case_recordVo case_recordVo)
	{
		try
		{
			Dao<case_recordVo, Integer> case_recordDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			return case_recordDao.update(case_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, case_recordVo case_recordVo)
	{
		try
		{
			Dao<case_recordVo, Integer> case_recordDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			return case_recordDao.delete(case_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static case_recordVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<case_recordVo, Integer> case_recordDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			return case_recordDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<case_recordVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<case_recordVo, String> case_recordDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			QueryBuilder<case_recordVo, String> queryBuilder = case_recordDao.queryBuilder();
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
	public static case_recordVo getcase_recordVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<case_recordVo, Integer> Case_numberDao = DaoManager.createDao(connectionSource, case_recordVo.class);
			List<case_recordVo> data = Case_numberDao.queryForAll();

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