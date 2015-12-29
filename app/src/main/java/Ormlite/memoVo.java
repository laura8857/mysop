package Ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by laura on 15/12/29.
 */
@DatabaseTable(tableName = "memo")
public class memoVo extends MDVo implements Serializable {
    public static final String FIELD_Id ="id";
    public static final String FIELD_System_message ="System_message";


    @DatabaseField(columnName = FIELD_Id,canBeNull = true)
    private int id;
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    @DatabaseField(columnName = FIELD_System_message, canBeNull = false)
    private String System_message;
    public String getSystem_message()
    {
        return System_message;
    }
    public void setSystem_message(String System_message)
    {
        this.System_message = System_message;
    }

}
