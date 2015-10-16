package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//【Step_number】
@DatabaseTable(tableName = "map_location")
public class map_locationVo extends MDVo implements Serializable
{
	public static final String FIELD_Step_number ="Step_number";
	public static final String FIELD_Map_center_loc_lat ="Map_center_loc_lat";
	public static final String FIELD_Map_center_loc_lng ="Map_center_loc_lng";
	public static final String FIELD_Graph_loc_x ="Graph_loc_x";
	public static final String FIELD_Graph_loc_y ="Graph_loc_y";
	public static final String FIELD_Loc_intro ="Loc_intro";
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
	@DatabaseField(columnName = FIELD_Map_center_loc_lat, canBeNull = false)
	private String Map_center_loc_lat;
	public String getMap_center_loc_lat()
	{
		return Map_center_loc_lat;
	}
	public void setMap_center_loc_lat(String Map_center_loc_lat)
	{
		this.Map_center_loc_lat = Map_center_loc_lat;
	}
	@DatabaseField(columnName = FIELD_Map_center_loc_lng, canBeNull = false)
	private String Map_center_loc_lng;
	public String getMap_center_loc_lng() {
		return Map_center_loc_lng;
	}
	public void setMap_center_loc_lng(String Map_center_loc_lng) {
		this.Map_center_loc_lng = Map_center_loc_lng;
	}
	@DatabaseField(columnName = FIELD_Graph_loc_x, canBeNull = true)
	private String Graph_loc_x;//Email
	public String getGraph_loc_x()
	{
		return Graph_loc_x;
	}
	public void setGraph_loc_x(String Graph_loc_x)
	{
		this.Graph_loc_x = Graph_loc_x;
	}
	@DatabaseField(columnName = FIELD_Graph_loc_y, canBeNull = true)
	private String Graph_loc_y;
	public String getGraph_loc_y()
	{
		return Graph_loc_y;
	}
	public void setGraph_loc_y(String Graph_loc_y)
	{
		this.Graph_loc_y = Graph_loc_y;
	}
	@DatabaseField(columnName = FIELD_Loc_intro, canBeNull = true)
	private String Loc_intro;
	public String getLoc_intro()
	{
		return Graph_loc_y;
	}
	public void setLoc_intro(String Loc_intro)
	{
		this.Loc_intro = Loc_intro;
	}
}