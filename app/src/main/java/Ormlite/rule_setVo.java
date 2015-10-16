package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//【Sop_number】
@DatabaseTable(tableName = "rule_set")
public class rule_setVo extends MDVo implements Serializable
{
	public static final String FIELD_Account ="Account";
	public static final String FIELD_Rule_number ="Rule_number";
	public static final String FIELD_Sop_number ="Sop_number";
	public static final String FIELD_Sop_version ="Sop_version";
	public static final String FIELD_Rule_create_date ="Rule_create_date";
	public static final String FIELD_Set_start_time ="Set_start_time";
	public static final String FIELD_Set_finish_time ="Set_finish_time";
	public static final String FIELD_Date_type ="Date_type";
	public static final String FIELD_Do_date ="Do_date";
	public static final String FIELD_Do_time ="Do_time";
	/*@DatabaseField(generatedId = true)
	private int id;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}*/
	@DatabaseField(columnName = FIELD_Account, canBeNull = false)
	private String Account;
	public String getAccount()
	{
		return Account;
	}
	public void setAccount(String Account)
	{
		this.Account = Account;
	}
	@DatabaseField(columnName = FIELD_Rule_number, canBeNull = true)
	private String Rule_number;
	public String getRule_number()
	{
		return Rule_number;
	}
	public void setRule_number(String Rule_number)
	{
		this.Rule_number = Rule_number;
	}
	@DatabaseField(columnName = FIELD_Sop_number, canBeNull = false)
	private String Sop_number;
	public String getSop_number()
	{
		return Sop_number;
	}
	public void setSop_number(String Sop_number)
	{
		this.Sop_number = Sop_number;
	}
	@DatabaseField(columnName = FIELD_Sop_version, canBeNull = false)
	private String Sop_version;
	public String getSop_version()
	{
		return Sop_version;
	}
	public void setSop_version(String Sop_version)
	{
		this.Sop_version = Sop_version;
	}
	@DatabaseField(columnName = FIELD_Rule_create_date, canBeNull = false)
	private String Rule_create_date;
	public String getRule_create_date() {
		return Rule_create_date;
	}
	public void setRule_create_date(String Rule_create_date) {
		this.Rule_create_date = Rule_create_date;
	}
	@DatabaseField(columnName = FIELD_Set_start_time, canBeNull = true)
	private String Set_start_time;//Email
	public String getSet_start_time()
	{
		return Set_start_time;
	}
	public void setSet_start_time(String Set_start_time)
	{
		this.Set_start_time = Set_start_time;
	}
	@DatabaseField(columnName = FIELD_Set_finish_time, canBeNull = true)
	private String Set_finish_time;
	public String getSet_finish_time()
	{
		return Set_finish_time;
	}
	public void setSet_finish_time(String Set_finish_time)
	{
		this.Set_finish_time = Set_finish_time;
	}
	@DatabaseField(columnName = FIELD_Do_date, canBeNull = true)
	private String Do_date;
	public String getDo_date()
	{
		return Do_date;
	}
	public void setDo_date(String Do_date)
	{
		this.Do_date = Do_date;
	}
	@DatabaseField(columnName = FIELD_Date_type, canBeNull = true)
	private String Date_type;
	public String getDate_type()
	{
		return Set_finish_time;
	}
	public void setDate_type(String Date_type)
	{
		this.Date_type = Date_type;
	}
	@DatabaseField(columnName = FIELD_Do_time, canBeNull = true)
	private String Do_time;
	public String getDo_time()
	{
		return Do_time;
	}
	public void setDo_time(String Do_time)
	{
		this.Do_time = Do_time;
	}
}