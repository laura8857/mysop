package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//【Sop_number】
@DatabaseTable(tableName = "Step_purpose")
public class sop_detailVo extends MDVo implements Serializable
{
	public static final String FIELD_Sop_number ="Sop_number";
	public static final String FIELD_Sop_version ="Sop_version";
	public static final String FIELD_Step_order ="Step_order";
	public static final String FIELD_Step_number ="Step_number";
	public static final String FIELD_Step_name ="Step_name";
	public static final String FIELD_Step_purpose ="Step_purpose";
	public static final String FIELD_Step_intro ="Step_intro";
	public static final String FIELD_Start_time ="Start_time";
	public static final String FIELD_Finish_time ="Finish_time";
	public static final String FIELD_Start_rule ="Start_rule";
	public static final String FIELD_Start_value1 ="Start_value1";
	public static final String FIELD_Start_value2 ="Start_value2";
	public static final String FIELD_Start_message ="Start_message";
	public static final String FIELD_Start_remind ="Start_remind";
	public static final String FIELD_Step_remind ="Step_remind";
	public static final String FIELD_Step_text_content ="Step_text_content";
	public static final String FIELD_Step_graph_path ="Step_graph_path";
	public static final String FIELD_Step_file_path ="Step_file_path";
	public static final String FIELD_Finish_rule ="Finish_rule";
	public static final String FIELD_Finish_value1 ="Finish_value1";
	public static final String FIELD_Finish_value2 ="Finish_value2";
	public static final String FIELD_Next_step_rule ="Next_step_rule";
	public static final String FIELD_Next_step_number ="Next_step_number";
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
	@DatabaseField(columnName = FIELD_Step_order, canBeNull = false)
	private String Step_order;
	public String getStep_order() {
		return Step_order;
	}
	public void setStep_order(String Step_order) {
		this.Step_order = Step_order;
	}
	@DatabaseField(columnName = FIELD_Step_number, canBeNull = true)
	private String Step_number;//Email
	public String getStep_number()
	{
		return Step_number;
	}
	public void setStep_number(String Step_number)
	{
		this.Step_number = Step_number;
	}
	@DatabaseField(columnName = FIELD_Step_name, canBeNull = true)
	private String Step_name;
	public String getStep_name()
	{
		return Step_name;
	}
	public void setStep_name(String Step_name)
	{
		this.Step_name = Step_name;
	}
	@DatabaseField(columnName = FIELD_Step_intro, canBeNull = true)
	private String Step_intro;
	public String getStep_intro()
	{
		return Step_intro;
	}
	public void setStep_intro(String Step_intro)
	{
		this.Step_intro = Step_intro;
	}
	@DatabaseField(columnName = FIELD_Step_purpose, canBeNull = true)
	private String Step_purpose;
	public String getStep_purpose()
	{
		return Step_name;
	}
	public void setStep_purpose(String Step_purpose)
	{
		this.Step_purpose = Step_purpose;
	}
	@DatabaseField(columnName = FIELD_Start_time, canBeNull = true)
	private String Start_time;
	public String getStart_time()
	{
		return Start_time;
	}
	public void setStart_time(String Start_time)
	{
		this.Start_time = Start_time;
	}
	@DatabaseField(columnName = FIELD_Finish_time, canBeNull = true)
	private String Finish_time;
	public String getFinish_time()
	{
		return Finish_time;
	}
	public void setFinish_time(String Finish_time)
	{
		this.Finish_time = Finish_time;
	}
	@DatabaseField(columnName = FIELD_Start_rule, canBeNull = false)
	private String Start_rule;
	public String getStart_rule()
	{
		return Start_rule;
	}
	public void setStart_rule(String Start_rule)
	{
		this.Start_rule = Start_rule;
	}
	@DatabaseField(columnName = FIELD_Start_value1, canBeNull = true)
	private String Start_value1;
	public String getStart_value1()
	{
		return Start_value1;
	}
	public void setStart_value1(String Start_value1)
	{
		this.Start_value1 = Start_value1;
	}
	@DatabaseField(columnName = FIELD_Start_value2, canBeNull = true)
	private String Start_value2;
	public String getStart_value2()
	{
		return Start_value2;
	}
	public void setStart_value2(String Start_value2)
	{
		this.Start_value2 = Start_value2;
	}
	@DatabaseField(columnName = FIELD_Start_message, canBeNull = true)
	private String Start_message;
	public String getStart_message()
	{
		return Start_message;
	}
	public void setStart_message(String Start_message)
	{
		this.Start_message = Start_message;
	}
	@DatabaseField(columnName = FIELD_Start_remind, canBeNull = true)
	private String Start_remind;
	public String getStart_remind()
	{
		return Start_remind;
	}
	public void setStart_remind(String Start_remind)
	{
		this.Start_remind = Start_remind;
	}
	@DatabaseField(columnName = FIELD_Step_remind, canBeNull = true)
	private String Step_remind;
	public String getStep_remind()
	{
		return Step_remind;
	}
	public void setStep_remind(String Step_remind)
	{
		this.Step_remind = Step_remind;
	}
	@DatabaseField(columnName = FIELD_Step_text_content, canBeNull = false)
	private String Step_text_content;
	public String getStep_text_content()
	{
		return Step_text_content;
	}
	public void setStep_text_content(String Step_text_content)
	{
		this.Step_text_content = Step_text_content;
	}
	@DatabaseField(columnName = FIELD_Step_graph_path, canBeNull = false)
	private String Step_graph_path;
	public String getStep_graph_path()
	{
		return Step_graph_path;
	}
	public void setStep_graph_path(String Step_graph_path)
	{
		this.Step_graph_path = Step_graph_path;
	}
	@DatabaseField(columnName = FIELD_Step_file_path, canBeNull = false)
	private String Step_file_path;
	public String getStep_file_path() {
		return Step_file_path;
	}
	public void setStep_file_path(String Step_file_path) {
		this.Step_file_path = Step_file_path;
	}
	@DatabaseField(columnName = FIELD_Finish_rule, canBeNull = true)
	private String Finish_rule;//Email
	public String getFinish_rule()
	{
		return Finish_rule;
	}
	public void setFinish_rule(String Finish_rule)
	{
		this.Finish_rule = Finish_rule;
	}
	@DatabaseField(columnName = FIELD_Finish_value1, canBeNull = true)
	private String Finish_value1;
	public String getFinish_value1()
	{
		return Finish_value1;
	}
	public void setFinish_value1(String Finish_value1)
	{
		this.Finish_value1 = Finish_value1;
	}
	@DatabaseField(columnName = FIELD_Finish_value2, canBeNull = true)
	private String Finish_value2;
	public String getFinish_value2()
	{
		return Finish_value2;
	}
	public void setFinish_value2(String Finish_value2)
	{
		this.Finish_value2 = Finish_value2;
	}
	@DatabaseField(columnName = FIELD_Next_step_rule, canBeNull = true)
	private String Next_step_rule;
	public String getNext_step_rule()
	{
		return Next_step_rule;
	}
	public void setNext_step_rule(String Next_step_rule)
	{
		this.Next_step_rule = Next_step_rule;
	}
	@DatabaseField(columnName = FIELD_Next_step_number, canBeNull = true)
	private String Next_step_number;
	public String getNext_step_number()
	{
		return Next_step_number;
	}
	public void setNext_step_number(String Next_step_number)
	{
		this.Next_step_number = Next_step_number;
	}
}