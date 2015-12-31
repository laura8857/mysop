package Ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//�ccount��
@DatabaseTable(tableName = "member_account")
public class member_accountVo extends MDVo implements Serializable
{
	public static final String FIELD_Account ="account";
	public static final String FIELD_Username ="username";
	public static final String FIELD_Password ="password";
	public static final String FIELD_Captcha ="captcha";
	public static final String FIELD_Gcm ="gcm";
	public static final String FIELD_Note ="note";
	public static final String FIELD_Auth ="auth";
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
	@DatabaseField(columnName = FIELD_Account, canBeNull = false)
	private String Account;
	public String getAccount()
	{
		return Account;
	}
	public void setAccount(String account)
	{
		this.Account = account;
	}
	@DatabaseField(columnName = FIELD_Username, canBeNull = true)
	private String Username;
	public String getUsername()
	{
		return Username;
	}
	public void setUsername(String username)
	{
		this.Username = username;
	}
	@DatabaseField(columnName = FIELD_Password, canBeNull = false)
	private String Password;
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		this.Password = password;
	}
	@DatabaseField(columnName = FIELD_Captcha, canBeNull = true)
	private String Captcha;//Email
	public String getCaptcha()
	{
		return Captcha;
	}
	public void setCaptcha(String captcha)
	{
		this.Captcha = captcha;
	}
	@DatabaseField(columnName = FIELD_Gcm, canBeNull = true)
	private String Gcm;
	public String getGcm()
	{
		return Gcm;
	}
	public void setGcm(String gcm)
	{
		this.Gcm = gcm;
	}
	@DatabaseField(columnName = FIELD_Note, canBeNull = true)
	private String Note;
	public String getNote()
	{
		return Note;
	}
	public void setNote(String note)
	{
		this.Note = note;
	}
	@DatabaseField(columnName = FIELD_Auth, canBeNull = true)
	private String Auth;
	public String getAuth()
	{
		return Auth;
	}
	public void setAuth(String auth)
	{
		this.Auth = auth;
	}
}