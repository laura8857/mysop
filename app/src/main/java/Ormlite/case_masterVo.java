package Ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
//�op_number��
@DatabaseTable(tableName = "case_master")
public class case_masterVo extends MDVo implements Serializable
{
	public static final String FIELD_Case_number ="Case_number";
	public static final String FIELD_Account ="Account";
	public static final String FIELD_Rule_number ="Rule_number";
	public static final String FIELD_Sop_number ="Sop_number";
	public static final String FIELD_Sop_version ="Sop_version";
	public static final String FIELD_Step_number ="Step_number";
	public static final String FIELD_Case_start_time ="Case_start_time";
	public static final String FIELD_Case_finish_time ="Case_finish_time";
	public static final String FIELD_Case_type ="Case_type";
	public static final String FIELD_Sop_do_result ="Sop_do_result";
	public static final String FIELD_Do_channel ="Do_channel";
	public static final String FIELD_Case_resource ="Case_resource";
	public static final String FIELD_Case_mark ="Case_mark";
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
	@DatabaseField(columnName = FIELD_Case_number, canBeNull = false)
	private String Case_number;
	public String getCase_number()
	{
		return Case_number;
	}
	public void setCase_number(String Case_number)
	{
		this.Case_number = Case_number;
	}
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
	public String getRule_number() {
		return Rule_number;
	}
	public void setRule_number(String Rule_number) {
		this.Rule_number = Rule_number;
	}
	@DatabaseField(columnName = FIELD_Sop_number, canBeNull = true)
	private String Sop_number;//Email
	public String getSop_number()
	{
		return Sop_number;
	}
	public void setSop_number(String Sop_number)
	{
		this.Sop_number = Sop_number;
	}
	@DatabaseField(columnName = FIELD_Sop_version, canBeNull = true)
	private String Sop_version;
	public String getSop_version()
	{
		return Sop_version;
	}
	public void setSop_version(String Sop_version)
	{
		this.Sop_version = Sop_version;
	}
	@DatabaseField(columnName = FIELD_Case_start_time, canBeNull = true)
	private String Case_start_time;
	public String getCase_start_time()
	{
		return Case_start_time;
	}
	public void setCase_start_time(String Case_start_time)
	{
		this.Case_start_time = Case_start_time;
	}
	@DatabaseField(columnName = FIELD_Step_number, canBeNull = true)
	private String Step_number;
	public String getStep_number()
	{
		return Step_number;
	}
	public void setStep_number(String Step_number)

	{
		this.Step_number = Step_number;
	}
	@DatabaseField(columnName = FIELD_Case_finish_time, canBeNull = true)
	private String Case_finish_time;
	public String getCase_finish_time()
	{
		return Case_finish_time;
	}
	public void setCase_finish_time(String Case_finish_time)
	{
		this.Case_finish_time = Case_finish_time;
	}
	@DatabaseField(columnName = FIELD_Case_type, canBeNull = true)
	private String Case_type;
	public String getCase_type()
	{
		return Case_type;
	}
	public void setCase_type(String Case_type)
	{
		this.Case_type = Case_type;
	}
	
	@DatabaseField(columnName = FIELD_Sop_do_result, canBeNull = true)
	private String Sop_do_result;
	public String getSop_do_result()
	{
		return Sop_do_result;
	}
	public void setSop_do_result(String Sop_do_result)
	{
		this.Sop_do_result = Sop_do_result;
	}
	@DatabaseField(columnName = FIELD_Do_channel, canBeNull = true)
	private String Do_channel;
	public String getDo_channel()
	{
		return Do_channel;
	}
	public void setDo_channel(String Do_channel)
	{
		this.Do_channel = Do_channel;
	}
	@DatabaseField(columnName = FIELD_Case_resource, canBeNull = true)
	private String Case_resource;
	public String getCase_resource()
	{
		return Case_resource;
	}
	public void setCase_resource(String Case_resource)
	{
		this.Case_resource = Case_resource;
	}
	@DatabaseField(columnName = FIELD_Case_mark, canBeNull = true)
	private String Case_mark;
	public String getCase_mark()
	{
		return Case_mark;
	}
	public void setCase_mark(String Case_mark)
	{
		this.Case_mark = Case_mark;
	}


}