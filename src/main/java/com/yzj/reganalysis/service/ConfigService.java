package com.yzj.reganalysis.service;

import com.yzj.reganalysis.Entity.ActionPart;
import com.yzj.reganalysis.Entity.Command;
import com.yzj.reganalysis.Entity.IpAddrStatus;

import java.util.List;
import java.util.Map;

public interface ConfigService {
    IpAddrStatus initSourceIpReg(IpAddrStatus source);
    IpAddrStatus initTargetIpReg(IpAddrStatus target);
    ActionPart initActionIpReg(ActionPart action);
    Integer getRegConfig(String regKey);
    Map<String, Integer> getRegKeyAndValue();
    List<String> convertCmdToBinary(List<Command> cmd);
}
