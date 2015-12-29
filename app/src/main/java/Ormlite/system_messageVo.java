package Ormlite;

/**
 * Created by kelly on 2015/12/24.
 */
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "system_message")
public class system_messageVo extends MDVo implements Serializable{

    public static final String FIELD_Id ="id";
    public static final String FIELD_System_message ="System_message";
    public static final String FIELD_Message_mark="Message_mark";


    @DatabaseField(generatedId = true,columnName = FIELD_Id)
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

    @DatabaseField(columnName = FIELD_Message_mark, canBeNull = false)
    private String Message_mark;
    public String getMessage_mark()
    {
        return System_message;
    }
    public void setMessage_mark(String Message_mark)
    {
        this.Message_mark = Message_mark;
    }

}
