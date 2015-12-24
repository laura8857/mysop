package Ormlite;

/**
 * Created by kelly on 2015/12/24.
 */
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "system_message")
public class system_messageVo extends MDVo implements Serializable{

    public static final String FIELD_Id ="id";
    public static final String FIELD_System_message ="System_message";


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

}
