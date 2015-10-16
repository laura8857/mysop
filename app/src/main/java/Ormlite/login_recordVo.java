package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//嚙踐�count嚙踝蕭
@DatabaseTable(tableName = "login_record")
public class login_recordVo extends MDVo implements Serializable
{
	public static final String FIELD_Login_number ="Login_number";
	public static final String FIELD_Account ="Account";
	public static final String FIELD_Ip ="Ip";
	public static final String FIELD_Login_time ="Login_time";
	public static final String FIELD_Login_result ="Login_result";
	public static final String FIELD_Login_channel ="Login_channel";
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
	@DatabaseField(columnName = FIELD_Login_number, canBeNull = false)
	private String Login_number;
	public String getLogin_number()
	{
		return Login_number;
	}
	public void setLogin_number(String Login_number)
	{
		this.Login_number = Login_number;
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
	@DatabaseField(columnName = FIELD_Ip, canBeNull = false)
	private String Ip;
	public String getIp() {
		return Ip;
	}
	public void setIp(String Ip) {
		this.Ip = Ip;
	}
	@DatabaseField(columnName = FIELD_Login_time, canBeNull = true)
	private String Login_time;//Email
	public String getLogin_time()
	{
		return Login_time;
	}
	public void setLogin_time(String Login_time)
	{
		this.Login_time = Login_time;
	}
	@DatabaseField(columnName = FIELD_Login_result, canBeNull = true)
	private String Login_result;
	public String getLogin_result()
	{
		return Login_result;
	}
	public void setLogin_result(String Login_result)
	{
		this.Login_result = Login_result;
	}
	@DatabaseField(columnName = FIELD_Login_channel, canBeNull = true)
	private String Login_channel;
	public String getLogin_channel()
	{
		return Login_channel;
	}
	public void setLogin_channel(String Login_channel)
	{
		this.Login_channel = Login_channel;
	}
}