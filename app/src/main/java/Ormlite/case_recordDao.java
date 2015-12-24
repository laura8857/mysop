package Ormlite;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;
//import com.ck.ap.DatabaseHelper;

//嚙踐�count Dao嚙踝蕭
public class case_recordDao
{

	/* insert */
	public static int insert(DatabaseHelper databaseHelper, case_recordVo case_recordVo) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper.getCase_recordDao();
		if (exist(databaseHelper, case_recordVo)) {
			return 0;
		}
		return case_recordDao.create(case_recordVo);
	}


	/* exist */
	public static boolean exist(DatabaseHelper databaseHelper, case_recordVo case_recordVo) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao
				.queryBuilder();
		try {
			queryBuilder.where()
					.eq(case_recordVo.FIELD_Id, case_recordVo.getId());
			//	.and()
			//	.eq(AccountVo.FIELD_Device, aAccountVo.getDevice());
			return queryBuilder.query().size() > 0 ? true : false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
//
//	/* update */
//	public static int update(DatabaseHelper databaseHelper,case_recordVo case_recordVo) {
//		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
//				.getCase_recordDao();
//		try {
//			return case_recordDao.update(case_recordVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
    public static int update(DatabaseHelper databaseHelper, String columnvalue, String originalvalue,String columnvalue1, String originalvalue1, String column, String value) {
        RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
                .getCase_recordDao();
        UpdateBuilder<case_recordVo, Integer> updateBuilder = case_recordDao.updateBuilder();
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

    public static int update_record(DatabaseHelper databaseHelper, String columnvalue, String originalvalue,String columnvalue1, String originalvalue1,String columnvalue2, String originalvalue2, String column, String value) {
        RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
                .getCase_recordDao();
        UpdateBuilder<case_recordVo, Integer> updateBuilder = case_recordDao.updateBuilder();
        try {
            //判斷式 哪一欄 = 值
            updateBuilder.where().eq(columnvalue, originalvalue).and().eq(columnvalue1,originalvalue1).and().eq(columnvalue2,originalvalue2);
            // update the value of your field(s)
            updateBuilder.updateColumnValue(column, value);

            return updateBuilder.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//	/* delete */
//	public static int delete(DatabaseHelper databaseHelper, case_recordVo case_recordVo) {
//		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
//				.getCase_recordDao();
//		try {
//			return case_recordDao.delete(case_recordVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
    public static int delete(DatabaseHelper databaseHelper, String columnvalue, String originalvalue) {
        RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
                .getCase_recordDao();
        DeleteBuilder<case_recordVo,Integer> deleteBuilder = case_recordDao.deleteBuilder();
        try {
            //判斷式 哪一欄 = 值
            deleteBuilder.where().eq(columnvalue, originalvalue);

            return deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

	/* selectRaw */
	public static case_recordVo getCase_recordVo(DatabaseHelper databaseHelper) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao
				.queryBuilder();
		try {

			List<case_recordVo> data = queryBuilder.where().raw("1=1").query();
			if (data.size() > 0) {
				return data.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* select by id */
	public static case_recordVo select(DatabaseHelper databaseHelper, int id) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		try {
			return case_recordDao.queryForId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/* selectRaw */
	public static List<case_recordVo> selectRaw(DatabaseHelper databaseHelper,
												   String rawWhere) {
		RuntimeExceptionDao<case_recordVo, Integer> case_recordDao = databaseHelper
				.getCase_recordDao();
		QueryBuilder<case_recordVo, Integer> queryBuilder = case_recordDao
				.queryBuilder();
		try {
			queryBuilder.where().raw(rawWhere);
			return queryBuilder.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}