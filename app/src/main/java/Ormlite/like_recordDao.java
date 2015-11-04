package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class like_recordDao
{
	/* insert */
	public static int insert(DatabaseHelper databaseHelper, like_recordVo like_recordVo)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, Integer> like_recordDao = databaseHelper.getLike_recordDao();
			if (exist(databaseHelper, like_recordVo))
			{
				return 0;
			}
			return like_recordDao.create(like_recordVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, like_recordVo like_recordVo)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, Integer> like_recordDao = databaseHelper.getLike_recordDao();
			QueryBuilder<like_recordVo, Integer> queryBuilder = like_recordDao.queryBuilder();

			boolean ret=false;
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(like_recordVo.FIELD_Like_number,like_recordVo.getLike_number());
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
	public static int update(DatabaseHelper databaseHelper, like_recordVo like_recordVo)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, Integer> like_recordDao = databaseHelper.getLike_recordDao();
			return like_recordDao.update(like_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(DatabaseHelper databaseHelper, like_recordVo like_recordVo)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, Integer> like_recordDao = databaseHelper.getLike_recordDao();
			return like_recordDao.delete(like_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static like_recordVo select(DatabaseHelper databaseHelper, int id)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, Integer> like_recordDao = databaseHelper.getLike_recordDao();
			return like_recordDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<like_recordVo> selectRaw(DatabaseHelper databaseHelper, String rawWhere)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, String> like_recordDao = databaseHelper.getLike_recordDao();
			QueryBuilder<like_recordVo, String> queryBuilder = like_recordDao.queryBuilder();
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
	public static like_recordVo getlike_recordVo(DatabaseHelper databaseHelper)
	{
		try
		{
			RuntimeExceptionDao<like_recordVo, Integer> Like_numberDao = databaseHelper.getLike_recordDao();
			List<like_recordVo> data = Like_numberDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// ���蕭�嚙踝嚙踝蕭
				// Like_numberVo aLike_numberVo = new Like_numberVo();
				// Like_numberDao.insert(databaseHelper, aLike_numberVo);
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