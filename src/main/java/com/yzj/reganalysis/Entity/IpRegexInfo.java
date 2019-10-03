package com.yzj.reganalysis.Entity;

import lombok.Data;

@Data
public class IpRegexInfo {
    private String sourceIP;
    private String targetIP;
    private Integer ActionType;
    private String status;
    private String message;
}
