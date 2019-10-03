package com.yzj.reganalysis.Entity;

import lombok.Data;

@Data
public class AddrInfo {
    private String sendToAddr;
    private Integer sendToPort;
    private Integer receivePort;
}
