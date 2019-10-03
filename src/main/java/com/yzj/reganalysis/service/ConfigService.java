package com.yzj.reganalysis.service;

import com.yzj.reganalysis.Entity.ActionPart;
import com.yzj.reganalysis.Entity.IpAddrStatus;

public interface ConfigService {
    IpAddrStatus initSourceIpReg(IpAddrStatus source);
    IpAddrStatus initTargetIpReg(IpAddrStatus target);
    ActionPart initActionIpReg(ActionPart action);
}
