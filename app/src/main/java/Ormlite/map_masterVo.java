package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//�op_number��
@DatabaseTable(tableName = "map_master")
public class map_masterVo extends MDVo implements Serializable
{
	public static final String FIELD_Sop_number ="Sop_number";
	public static final String FIELD_Sop_version ="Sop_version";
	public static final String FIELD_Map_center_loc_lat ="Map_center_loc_lat";
	public static final String FIELD_Map_center_loc_lng ="Map_center_loc_lng";
	public static final String FIELD_Map_scale ="Map_scale";
	public static final String FIELD_Graph_src ="Graph_src";
	public static final String FIELD_Graph_loc_leftx ="Graph_loc_leftx";
	public static final String FIELD_Graph_loc_lefty ="Graph_loc_lefty";
	public static final String FIELD_Graph_loc_rightx ="Graph_loc_rightx";
	public static final String FIELD_Graph_loc_righty ="Graph_loc_righty";
	public static final String FIELD_Graph_scale ="Graph_scale";
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
	@DatabaseField(columnName = FIELD_Map_center_loc_lat, canBeNull = false)
	private String Map_center_loc_lat;
	public String getMap_center_loc_lat() {
		return Map_center_loc_lat;
	}
	public void setMap_center_loc_lat(String Map_center_loc_lat) {
		this.Map_center_loc_lat = Map_center_loc_lat;
	}
	@DatabaseField(columnName = FIELD_Map_center_loc_lng, canBeNull = true)
	private String Map_center_loc_lng;//Email
	public String getMap_center_loc_lng()
	{
		return Map_center_loc_lng;
	}
	public void setMap_center_loc_lng(String Map_center_loc_lng)
	{
		this.Map_center_loc_lng = Map_center_loc_lng;
	}
	@DatabaseField(columnName = FIELD_Map_scale, canBeNull = true)
	private String Map_scale;
	public String getMap_scale()
	{
		return Map_scale;
	}
	public void setMap_scale(String Map_scale)
	{
		this.Map_scale = Map_scale;
	}
	@DatabaseField(columnName = FIELD_Graph_loc_leftx, canBeNull = true)
	private String Graph_loc_leftx;
	public String getGraph_loc_leftx()
	{
		return Graph_loc_leftx;
	}
	public void setGraph_loc_leftx(String Graph_loc_leftx)
	{
		this.Graph_loc_leftx = Graph_loc_leftx;
	}
	@DatabaseField(columnName = FIELD_Graph_src, canBeNull = true)
	private String Graph_src;
	public String getGraph_src()
	{
		return Map_scale;
	}
	public void setGraph_src(String Graph_src)
	{
		this.Graph_src = Graph_src;
	}
	@DatabaseField(columnName = FIELD_Graph_loc_lefty, canBeNull = true)
	private String Graph_loc_lefty;
	public String getGraph_loc_lefty()
	{
		return Graph_loc_lefty;
	}
	public void setGraph_loc_lefty(String Graph_loc_lefty)
	{
		this.Graph_loc_lefty = Graph_loc_lefty;
	}
	@DatabaseField(columnName = FIELD_Graph_loc_rightx, canBeNull = true)
	private String Graph_loc_rightx;
	public String getGraph_loc_rightx()
	{
		return Graph_loc_rightx;
	}
	public void setGraph_loc_rightx(String Graph_loc_rightx)
	{
		this.Graph_loc_rightx = Graph_loc_rightx;
	}
	@DatabaseField(columnName = FIELD_Graph_loc_righty, canBeNull = true)
	private String Graph_loc_righty;
	public String getGraph_loc_righty()
	{
		return Graph_loc_righty;
	}
	public void setGraph_loc_righty(String Graph_loc_righty)
	{
		this.Graph_loc_righty = Graph_loc_righty;
	}
	@DatabaseField(columnName = FIELD_Graph_scale, canBeNull = true)
	private String Graph_scale;
	public String getGraph_scale()
	{
		return Graph_scale;
	}
	public void setGraph_scale(String Graph_scale)
	{
		this.Graph_scale = Graph_scale;
	}
}