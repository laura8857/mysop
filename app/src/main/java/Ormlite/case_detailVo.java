package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//嚙踐�count嚙踝蕭
@DatabaseTable(tableName = "case_detail")
public class case_detailVo extends MDVo implements Serializable
{
	public static final String FIELD_Case_number ="Case_number";
	public static final String FIELD_Step_order ="Step_order";
	public static final String FIELD_Step_start_time ="Step_start_time";
	public static final String FIELD_Step_finish_time ="Step_finish_time";
	public static final String FIELD_Step_do_result ="Step_do_result";
	public static final String FIELD_Do_loc_lat ="Do_loc_lat";
	public static final String FIELD_Do_loc_lng ="Do_loc_lng";
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
	@DatabaseField(columnName = FIELD_Step_order, canBeNull = false)
	private String Step_order;
	public String getStep_order()
	{
		return Step_order;
	}
	public void setStep_order(String Step_order)
	{
		this.Step_order = Step_order;
	}
	@DatabaseField(columnName = FIELD_Step_start_time, canBeNull = false)
	private String Step_start_time;
	public String getStep_start_time() {
		return Step_start_time;
	}
	public void setStep_start_time(String Step_start_time) {
		this.Step_start_time = Step_start_time;
	}
	@DatabaseField(columnName = FIELD_Step_finish_time, canBeNull = true)
	private String Step_finish_time;//Email
	public String getStep_finish_time()
	{
		return Step_finish_time;
	}
	public void setStep_finish_time(String Step_finish_time)
	{
		this.Step_finish_time = Step_finish_time;
	}
	@DatabaseField(columnName = FIELD_Step_do_result, canBeNull = true)
	private String Step_do_result;
	public String getStep_do_result()
	{
		return Step_do_result;
	}
	public void setStep_do_result(String Step_do_result)
	{
		this.Step_do_result = Step_do_result;
	}
	@DatabaseField(columnName = FIELD_Do_loc_lat, canBeNull = true)
	private String Do_loc_lat;
	public String getDo_loc_lat()
	{
		return Do_loc_lat;
	}
	public void setDo_loc_lat(String Do_loc_lat)
	{
		this.Do_loc_lat = Do_loc_lat;
	}
	@DatabaseField(columnName = FIELD_Do_loc_lng, canBeNull = true)
	private String Do_loc_lng;
	public String getDo_loc_lng()
	{
		return Do_loc_lng;
	}
	public void setDo_loc_lng(String Do_loc_lng)
	{
		this.Do_loc_lng = Do_loc_lng;
	}
}