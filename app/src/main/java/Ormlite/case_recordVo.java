package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//嚙踐�count嚙踝蕭
@DatabaseTable(tableName = "case_record")
public class case_recordVo extends MDVo implements Serializable
{
	public static final String FIELD_Case_number ="Case_number";
	public static final String FIELD_Step_order ="Step_order";
	public static final String FIELD_Record_order ="Record_order";
	public static final String FIELD_Record_value ="Record_value";
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
	@DatabaseField(columnName = FIELD_Record_order, canBeNull = false)
	private String Record_order;
	public String getRecord_order() {
		return Record_order;
	}
	public void setRecord_order(String Record_order) {
		this.Record_order = Record_order;
	}
	@DatabaseField(columnName = FIELD_Record_value, canBeNull = true)
	private String Record_value;//Email
	public String getRecord_value()
	{
		return Record_value;
	}
	public void setRecord_value(String Record_value)
	{
		this.Record_value = Record_value;
	}
}