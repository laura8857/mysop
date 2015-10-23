package Ormlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_user")

public class User {

    //自動生成
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "desc")
    private String desc;

    public User(long millis){
    }

    public User(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

}
