package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class login_recordDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, login_recordVo login_recordVo)
	{
		try
		{
			Dao<login_recordVo, Integer> login_recordDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			if (exist(connectionSource, login_recordVo))
			{
				return 0;
			}
			return login_recordDao.create(login_recordVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, login_recordVo login_recordVo)
	{
		try
		{
			Dao<login_recordVo, Integer> login_recordDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			QueryBuilder<login_recordVo, Integer> queryBuilder = login_recordDao.queryBuilder();

			boolean ret=false;
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(login_recordVo.FIELD_Login_number,login_recordVo.getLogin_number());
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
	public static int update(ConnectionSource connectionSource, login_recordVo login_recordVo)
	{
		try
		{
			Dao<login_recordVo, Integer> login_recordDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			return login_recordDao.update(login_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, login_recordVo login_recordVo)
	{
		try
		{
			Dao<login_recordVo, Integer> login_recordDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			return login_recordDao.delete(login_recordVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static login_recordVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<login_recordVo, Integer> login_recordDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			return login_recordDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<login_recordVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<login_recordVo, String> login_recordDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			QueryBuilder<login_recordVo, String> queryBuilder = login_recordDao.queryBuilder();
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
	public static login_recordVo getlogin_recordVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<login_recordVo, Integer> Login_numberDao = DaoManager.createDao(connectionSource, login_recordVo.class);
			List<login_recordVo> data = Login_numberDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// ���蕭�嚙踝嚙踝蕭
				// Login_numberVo aLogin_numberVo = new Login_numberVo();
				// Login_numberDao.insert(databaseHelper, aLogin_numberVo);
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