package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//【Step_number】
@DatabaseTable(tableName = "step_record")
public class step_recordVo extends MDVo implements Serializable
{
    public static final String FIELD_Id ="id";
    public static final String FIELD_Step_number ="Step_number";
	public static final String FIELD_Record_order ="Record_order";
	public static final String FIELD_Record_text ="Record_text";
	public static final String FIELD_Record_type ="Record_type";
	public static final String FIELD_Record_unit ="Record_unit";
	public static final String FIELD_Record_max ="Record_max";
	public static final String FIELD_Record_min ="Record_min";
	public static final String FIELD_Record_standard ="Record_standard";
	@DatabaseField(columnName = FIELD_Id, canBeNull = false)
	private int id;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	@DatabaseField(columnName = FIELD_Step_number, canBeNull = false)
	private String Step_number;
	public String getStep_number()
	{
		return Step_number;
	}
	public void setStep_number(String Step_number)
	{
		this.Step_number = Step_number;
	}
	@DatabaseField(columnName = FIELD_Record_order, canBeNull = false)
	private String Record_order;
	public String getRecord_order()
	{
		return Record_order;
	}
	public void setRecord_order(String Record_order)
	{
		this.Record_order = Record_order;
	}
	@DatabaseField(columnName = FIELD_Record_text, canBeNull = false)
	private String Record_text;
	public String getRecord_text() {
		return Record_text;
	}
	public void setRecord_text(String Record_text) {
		this.Record_text = Record_text;
	}
	@DatabaseField(columnName = FIELD_Record_type, canBeNull = true)
	private String Record_type;//Email
	public String getRecord_type()
	{
		return Record_type;
	}
	public void setRecord_type(String Record_type)
	{
		this.Record_type = Record_type;
	}
	@DatabaseField(columnName = FIELD_Record_unit, canBeNull = true)
	private String Record_unit;
	public String getRecord_unit()
	{
		return Record_unit;
	}
	public void setRecord_unit(String Record_unit)
	{
		this.Record_unit = Record_unit;
	}
	@DatabaseField(columnName = FIELD_Record_max, canBeNull = true)
	private String Record_max;
	public String getRecord_max()
	{
		return Record_max;
	}
	public void setRecord_max(String Record_max)
	{
		this.Record_max = Record_max;
	}
	@DatabaseField(columnName = FIELD_Record_min, canBeNull = true)
	private String Record_min;
	public String getRecord_min()
	{
		return Record_min;
	}
	public void setRecord_min(String Record_min)
	{
		this.Record_min = Record_min;
	}
	@DatabaseField(columnName = FIELD_Record_standard, canBeNull = true)
	private String Record_standard;
	public String getRecord_standard()
	{
		return Record_standard;
	}
	public void setRecord_standard(String Record_standard)
	{
		this.Record_standard = Record_standard;
	}
}