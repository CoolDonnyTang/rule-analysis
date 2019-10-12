package com.yzj.reganalysis.service;

import com.yzj.reganalysis.Entity.ActionPart;
import com.yzj.reganalysis.Entity.IpAddrStatus;

import java.util.Map;

public interface ConfigService {
    IpAddrStatus initSourceIpReg(IpAddrStatus source);
    IpAddrStatus initTargetIpReg(IpAddrStatus target);
    ActionPart initActionIpReg(ActionPart action);
    String getRegConfig(String regKey);
    Map<String, String> getRegKeyAndValue();
}
