package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//�ccount Dao��
public class member_accountDao
{
	/* insert */
	public static int insert(ConnectionSource connectionSource, member_accountVo member_accountVo)
	{
		try
		{
			Dao<member_accountVo, Integer> member_accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			if (exist(connectionSource, member_accountVo))
			{
				return 0;
			}
			return member_accountDao.create(member_accountVo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* exist */
	public static boolean exist(ConnectionSource connectionSource, member_accountVo member_accountVo)
	{
		try
		{
			Dao<member_accountVo, Integer> member_accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			QueryBuilder<member_accountVo, Integer> queryBuilder = member_accountDao.queryBuilder();

			boolean ret=false;
			//�撣唾����
			queryBuilder.where().eq(member_accountVo.FIELD_Account,member_accountVo.getAccount());
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
	public static int update(ConnectionSource connectionSource, member_accountVo member_accountVo)
	{
		try
		{
			Dao<member_accountVo, Integer> member_accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			return member_accountDao.update(member_accountVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* delete */
	public static int delete(ConnectionSource connectionSource, member_accountVo member_accountVo)
	{
		try
		{
			Dao<member_accountVo, Integer> member_accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			return member_accountDao.delete(member_accountVo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/* select by id */
	public static member_accountVo select(ConnectionSource connectionSource, int id)
	{
		try
		{
			Dao<member_accountVo, Integer> member_accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			return member_accountDao.queryForId(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* selectRaw */
	public static List<member_accountVo> selectRaw(ConnectionSource connectionSource, String rawWhere)
	{
		try
		{
			Dao<member_accountVo, String> member_accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			QueryBuilder<member_accountVo, String> queryBuilder = member_accountDao.queryBuilder();
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
	public static member_accountVo getMember_accountVo(ConnectionSource connectionSource)
	{
		try
		{
			Dao<member_accountVo, Integer> accountDao = DaoManager.createDao(connectionSource, member_accountVo.class);
			List<member_accountVo> data = accountDao.queryForAll();

			if (data.size() > 0)
			{
				return data.get(0);
			}
			else
			{
				// 瘝����
				// AccountVo aAccountVo = new AccountVo();
				// AccountDao.insert(databaseHelper, aAccountVo);
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