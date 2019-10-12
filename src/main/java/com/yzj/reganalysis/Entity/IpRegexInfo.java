package com.yzj.reganalysis.Entity;

import lombok.Data;

import java.util.List;

@Data
public class IpRegexInfo {
    private String sourceIP;
    private String targetIP;
    private Integer ActionType;
    private String status;
    private String message;
    private CommandParseResultBO cmdParseResult;
    private List<String> binaryCode;
}
