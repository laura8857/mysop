package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class comment_recordDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, comment_recordVo comment_recordVo)
	{
		try
		{
			Dao<comment_recordVo, Integer> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			if (exist(connectionSource, comment_recordVo))
			{
				return 0;
			}
			return comment_recordDao.create(comment_recordVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, comment_recordVo comment_recordVo)
	{
		try
		{
			Dao<comment_recordVo, Integer> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			QueryBuilder<comment_recordVo, Integer> queryBuilder = comment_recordDao.queryBuilder();

			boolean ret=false;
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(comment_recordVo.FIELD_Comment_number,comment_recordVo.getComment_number());
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
	public static int update(ConnectionSource connectionSource, comment_recordVo comment_recordVo)
	{
		try
		{
			Dao<comment_recordVo, Integer> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			return comment_recordDao.update(comment_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, comment_recordVo comment_recordVo)
	{
		try
		{
			Dao<comment_recordVo, Integer> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			return comment_recordDao.delete(comment_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static comment_recordVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<comment_recordVo, Integer> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			return comment_recordDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<comment_recordVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<comment_recordVo, String> comment_recordDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			QueryBuilder<comment_recordVo, String> queryBuilder = comment_recordDao.queryBuilder();
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
	public static comment_recordVo getcomment_recordVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<comment_recordVo, Integer> Comment_numberDao = DaoManager.createDao(connectionSource, comment_recordVo.class);
			List<comment_recordVo> data = Comment_numberDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// ���蕭�嚙踝嚙踝蕭
				// Comment_numberVo aComment_numberVo = new Comment_numberVo();
				// Comment_numberDao.insert(databaseHelper, aComment_numberVo);
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