package Ormlite;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by laura on 15/12/25.
 */
public class mysopVo extends MDVo implements Serializable {
    @DatabaseField
    private String Sop_name;
    public String getSop_name() {
        return Sop_name;
    }
    @DatabaseField
    private String Sop_graph_src;
    public String getSop_graph_src() {
        return Sop_graph_src;
    }

    @DatabaseField
    private String Case_number;
    public String getCase_number() {
        return Case_number;
    }

    @DatabaseField
    private String Step_order;
    public String getStep_order() {
        return Step_order;
    }

    @DatabaseField
    private String Start_rule;
    public String getStart_rule() {
        return Start_rule;
    }

}
