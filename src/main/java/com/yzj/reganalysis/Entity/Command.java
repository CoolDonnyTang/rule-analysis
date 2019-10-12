package com.yzj.reganalysis.Entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class Command {
    private String label;
    private String type;
    private String reg1;
    private String reg2;
    private String targetValue;

    public Command() {
    }

    public Command(String label, String type, String reg1, String reg2, String targetValue) {
        this.label = label;
        this.type = type;
        this.reg1 = reg1;
        this.reg2 = reg2;
        this.targetValue = targetValue;
    }

    @Override
    public String toString() {
        String result = (type + " ");
        List<String> tmp = new ArrayList<>();
        if(reg1!=null) {
            tmp.add(reg1);
        }
        if(reg2!=null) {
            tmp.add(reg2);
        }
        if(targetValue!=null) {
            tmp.add(targetValue);
        }
        return result + StringUtils.join(tmp, ",");
    }
}
