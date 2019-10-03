package com.yzj.reganalysis.Entity;

import com.yzj.reganalysis.util.RegUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Data
public class IpPart {
    //需要比较的值在寄存器存放的地址
    private String targetReg = "$s_x";
    //暂存值
    private String tempReg = "$at0";
    //零号寄存器
    private String zeroReg = "$zero";
    private String partString;
    private Set<Range> range;
    private Set<Integer> status;
    private boolean isEmpty;
    private boolean isPort = false;

    public IpPart(String s, boolean isPort) {
        this.isPort = isPort;
        partString = s;
        if(StringUtils.isEmpty(s)) {
            isEmpty = true;
        } else {
            status = new HashSet<>();
            range = new HashSet<>();
            String[] state = s.split("\\|");
            for(String str : state) {
                String regStr = "(\\d+)-(\\d+)";
                if(str.matches(regStr)) {
                    int min = Integer.parseInt(RegUtil.findStringByGroup(str, regStr,1));
                    int max = Integer.parseInt(RegUtil.findStringByGroup(str, regStr,2));
                    if(min>=max) {
                        throw new RuntimeException("输入非法！！！范围表达式[" + str + "]的开始值不能大于等于结束值");
                    }
                    if(isPort && ((min>65535 || min<0)||(max>65535 || max<0))) {
                        throw new RuntimeException("输入非法！！！端口范围表达式[" + str + "]不在0-65535范围");
                    }
                    if(!isPort && ((min>255 || min<0)||(max>255 || max<0))) {
                        throw new RuntimeException("输入非法！！！IP范围表达式[" + str + "]不在0-255范围");
                    }
                    Range range = new Range(min, max);
                    this.range.add(range);
                } else if(str.matches("\\d+")) {
                    int data = Integer.parseInt(str);
                    if(isPort && (data>65535 || data<0)) {
                        throw new RuntimeException("输入非法！！！端口[" + data + "]不在0-65535范围");
                    }
                    if(!isPort && (data>255 || data<0)) {
                        throw new RuntimeException("输入非法！！！IP[" + data + "]不在0-255范围");
                    }
                    status.add(data);
                } else {
                    throw new RuntimeException("输入非法！！！[" + str + "]为非法值，你可以输入形如1-10|20-30、2|3|4、1-10|50|100-200的值");
                }
            }
        }
    }


    public IpPart(String s) {
        this(s, false);
    }
}
