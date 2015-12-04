package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//【Sop_number】
@DatabaseTable(tableName = "sop_master")
public class sop_masterVo extends MDVo implements Serializable
{
	public static final String FIELD_Sop_number ="Sop_number";
	public static final String FIELD_Sop_version ="Sop_version";
	public static final String FIELD_Sop_name ="Sop_name";
	public static final String FIELD_Sop_graph_src ="Sop_graph_src";
	public static final String FIELD_Sop_intro ="Sop_intro";
	public static final String FIELD_Sop_detail ="Sop_detail";
	public static final String FIELD_Create_time ="Create_time";
	public static final String FIELD_Effect_time ="Effect_time";
	public static final String FIELD_Stop_time ="Stop_time";
	public static final String FIELD_Account ="Account";
	public static final String FIELD_Start_rule ="Start_rule";
	public static final String FIELD_Step_account ="Step_account";
	public static final String FIELD_App_graph_src1 ="App_graph_src1";
	public static final String FIELD_App_graph_src2 ="App_graph_src2";
	public static final String FIELD_App_graph_src3 ="App_graph_src3";
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
	@DatabaseField(columnName = FIELD_Sop_name, canBeNull = true)
	private String Sop_name;
	public String getSop_name() {
		return Sop_name;
	}
	public void setSop_name(String Sop_name) {
		this.Sop_name = Sop_name;
	}
	@DatabaseField(columnName = FIELD_Sop_graph_src, canBeNull = true)
	private String Sop_graph_src;//Email
	public String getSop_graph_src()
	{
		return Sop_graph_src;
	}
	public void setSop_graph_src(String Sop_graph_src)
	{
		this.Sop_graph_src = Sop_graph_src;
	}
	@DatabaseField(columnName = FIELD_Sop_intro, canBeNull = true)
	private String Sop_intro;
	public String getSop_intro()
	{
		return Sop_intro;
	}
	public void setSop_intro(String Sop_intro)
	{
		this.Sop_intro = Sop_intro;
	}
	@DatabaseField(columnName = FIELD_Create_time, canBeNull = true)
	private String Create_time;
	public String getCreate_time()
	{
		return Create_time;
	}
	public void setCreate_time(String Create_time)
	{
		this.Create_time = Create_time;
	}
	@DatabaseField(columnName = FIELD_Sop_detail, canBeNull = true)
	private String Sop_detail;
	public String getSop_detail()
	{
		return Sop_detail;
	}
	public void setSop_detail(String Sop_detail)
	{
		this.Sop_detail = Sop_detail;
	}
	@DatabaseField(columnName = FIELD_Effect_time, canBeNull = true)
	private String Effect_time;
	public String getEffect_time()
	{
		return Effect_time;
	}
	public void setEffect_time(String Effect_time)
	{
		this.Effect_time = Effect_time;
	}
	@DatabaseField(columnName = FIELD_Stop_time, canBeNull = true)
	private String Stop_time;
	public String getStop_time()
	{
		return Stop_time;
	}
	public void setStop_time(String Stop_time)
	{
		this.Stop_time = Stop_time;
	}
	@DatabaseField(columnName = FIELD_Account, canBeNull = true)
	private String Account;
	public String getAccount()
	{
		return Account;
	}
	public void setAccount(String account)
	{
		this.Account = account;
	}
	@DatabaseField(columnName = FIELD_Start_rule, canBeNull = true)
	private String Start_rule;
	public String getStart_rule()
	{
		return Start_rule;
	}
	public void setStart_rule(String Start_rule)
	{
		this.Start_rule = Start_rule;
	}
	@DatabaseField(columnName = FIELD_Step_account, canBeNull = true)
	private String Step_account;
	public String getStep_account()
	{
		return Step_account;
	}
	public void setStep_account(String Step_account)
	{
		this.Step_account = Step_account;
	}
	@DatabaseField(columnName = FIELD_App_graph_src1, canBeNull = true)
	private String App_graph_src1;
	public String getApp_graph_src1()
	{
		return App_graph_src1;
	}
	public void setApp_graph_src1(String App_graph_src1)
	{
		this.App_graph_src1 = App_graph_src1;
	}
	@DatabaseField(columnName = FIELD_App_graph_src2, canBeNull = true)
	private String App_graph_src2;
	public String getApp_graph_src2()
	{
		return App_graph_src2;
	}
	public void setApp_graph_src2(String App_graph_src2)
	{
		this.App_graph_src2 = App_graph_src2;
	}
	@DatabaseField(columnName = FIELD_App_graph_src3, canBeNull = true)
	private String App_graph_src3;
	public String getApp_graph_src3()
	{
		return App_graph_src3;
	}
	public void setApp_graph_src3(String App_graph_src3)
	{
		this.App_graph_src3 = App_graph_src3;
	}
}