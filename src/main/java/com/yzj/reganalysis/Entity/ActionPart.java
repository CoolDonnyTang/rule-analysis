package com.yzj.reganalysis.Entity;

import lombok.Data;

@Data
public class ActionPart {
    //需要比较的值在寄存器存放的地址
    private String targetReg;
    //暂存值
    private String tempReg  = "$at0";
    //零号寄存器
    private String zeroReg  = "$zero";

    private Integer type;

    public ActionPart() {
    }

    public ActionPart(Integer type) {
        if(type == null) {
            throw new RuntimeException("非法输入！！！Action 值为null");
        }
        this.type = type;
    }
}
