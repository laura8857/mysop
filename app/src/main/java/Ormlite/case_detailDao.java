package Ormlite;

import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class case_detailDao
{
	/* insert */
	public static int insert(DatabaseHelper databaseHelper, case_detailVo case_detailVo) {
		RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper.getCase_detailDao();
		if (exist(databaseHelper,  case_detailVo)) {
			return 0;
		}
		return case_detailDao.create( case_detailVo);
	}

	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, case_detailVo case_detailVo)
	{
		RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper
				.getCase_detailDao();
		QueryBuilder<case_detailVo, Integer> queryBuilder = case_detailDao
				.queryBuilder();
		try
		{
			//嚙踐��嚙踐�蕭謢塗祗嚙踝蕭
			queryBuilder.where().eq(case_detailVo.FIELD_Case_number,case_detailVo.getCase_number()).and().eq(case_detailVo.FIELD_Step_order,case_detailVo.getStep_order());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/* update */
    public static int update(DatabaseHelper databaseHelper, String columnvalue,String originalvalue,String columnvalue1,String originalvalue1,String column,String value) {
        RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper
                .getCase_detailDao();
        UpdateBuilder<case_detailVo, Integer> updateBuilder = case_detailDao.updateBuilder();
        try {
            //判斷式 哪一欄 = 值
            updateBuilder.where().eq(columnvalue, originalvalue).and().eq(columnvalue1,originalvalue1);
            // update the value of your field(s)
            updateBuilder.updateColumnValue(column, value);

            return updateBuilder.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

	/* delete */
    public static int delete(DatabaseHelper databaseHelper, String columnvalue, String originalvalue) {
        RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper
                .getCase_detailDao();
        DeleteBuilder<case_detailVo,Integer> deleteBuilder = case_detailDao.deleteBuilder();
        try {
            //判斷式 哪一欄 = 值
            deleteBuilder.where().eq(columnvalue, originalvalue);

            return deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

	/* select by id */
	public static case_detailVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper
				.getCase_detailDao();
		try {
			return case_detailDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /* selectRaw */
    public static List<case_detailVo> selectRaw(DatabaseHelper databaseHelper,
                                                String rawWhere) {
        RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper
                .getCase_detailDao();
        QueryBuilder<case_detailVo, Integer> queryBuilder = case_detailDao
                .queryBuilder();
        try {
            queryBuilder.where().raw(rawWhere);
            return queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	// ���嚙踝蕭嚙踝蕭���謍唳��奕嚙踝嚙踝蕭
	public static case_detailVo getCase_detailVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<case_detailVo, Integer> case_detailDao = databaseHelper
				.getCase_detailDao();
		QueryBuilder<case_detailVo, Integer> queryBuilder = case_detailDao
				.queryBuilder();
		try {

			List<case_detailVo> data =  queryBuilder.where().raw("1=1").query();
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