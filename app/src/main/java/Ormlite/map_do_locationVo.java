package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//�ccount��
@DatabaseTable(tableName = "map_do_location")
public class map_do_locationVo extends MDVo implements Serializable
{
	public static final String FIELD_Case_number ="Case_number";
	public static final String FIELD_Task_order ="Task_order";
	public static final String FIELD_do_loc_lat ="do_loc_lat";
	public static final String FIELD_do_loc_lng ="do_loc_lng";
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
	@DatabaseField(columnName = FIELD_Task_order, canBeNull = false)
	private String Task_order;
	public String getTask_order()
	{
		return Task_order;
	}
	public void setTask_order(String Task_order)
	{
		this.Task_order = Task_order;
	}
	@DatabaseField(columnName = FIELD_do_loc_lat, canBeNull = false)
	private String do_loc_lat;
	public String getdo_loc_lat() {
		return do_loc_lat;
	}
	public void setdo_loc_lat(String do_loc_lat) {
		this.do_loc_lat = do_loc_lat;
	}
	@DatabaseField(columnName = FIELD_do_loc_lng, canBeNull = true)
	private String do_loc_lng;//Email
	public String getdo_loc_lng()
	{
		return do_loc_lng;
	}
	public void setdo_loc_lng(String do_loc_lng)
	{
		this.do_loc_lng = do_loc_lng;
	}
}