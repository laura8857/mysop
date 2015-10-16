package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//嚙踐�count嚙踝蕭
@DatabaseTable(tableName = "like_record")
public class like_recordVo extends MDVo implements Serializable
{
	public static final String FIELD_Like_number ="Like_number";
	public static final String FIELD_Account ="Account";
	public static final String FIELD_Sop_number ="Sop_number";
	public static final String FIELD_Like_time ="Like_time";
	public static final String FIELD_Collection_time ="Collection_time";
	public static final String FIELD_Sop_comment ="Sop_comment";
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
	@DatabaseField(columnName = FIELD_Like_number, canBeNull = false)
	private String Like_number;
	public String getLike_number()
	{
		return Like_number;
	}
	public void setLike_number(String Like_number)
	{
		this.Like_number = Like_number;
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
	@DatabaseField(columnName = FIELD_Sop_number, canBeNull = false)
	private String Sop_number;
	public String getSop_number() {
		return Sop_number;
	}
	public void setSop_number(String Sop_number) {
		this.Sop_number = Sop_number;
	}
	@DatabaseField(columnName = FIELD_Like_time, canBeNull = true)
	private String Like_time;//Email
	public String getLike_time()
	{
		return Like_time;
	}
	public void setLike_time(String Like_time)
	{
		this.Like_time = Like_time;
	}
	@DatabaseField(columnName = FIELD_Collection_time, canBeNull = true)
	private String Collection_time;
	public String getCollection_time()
	{
		return Collection_time;
	}
	public void setCollection_time(String Collection_time)
	{
		this.Collection_time = Collection_time;
	}
	@DatabaseField(columnName = FIELD_Sop_comment, canBeNull = true)
	private String Sop_comment;
	public String getSop_comment()
	{
		return Sop_comment;
	}
	public void setSop_comment(String Sop_comment)
	{
		this.Sop_comment = Sop_comment;
	}
}