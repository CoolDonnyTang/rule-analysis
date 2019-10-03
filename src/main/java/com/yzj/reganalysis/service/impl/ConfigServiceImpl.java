package com.yzj.reganalysis.service.impl;

import com.yzj.reganalysis.Entity.ActionPart;
import com.yzj.reganalysis.Entity.IpAddrStatus;
import com.yzj.reganalysis.service.ConfigService;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Override
    public IpAddrStatus initSourceIpReg(IpAddrStatus source) {
        //TODO
        source.getPart1().setTargetReg("s1");
        source.getPart2().setTargetReg("s2");
        source.getPart3().setTargetReg("s3");
        source.getPart4().setTargetReg("s4");
        source.getPort().setTargetReg("s5");
        return source;
    }

    @Override
    public IpAddrStatus initTargetIpReg(IpAddrStatus target) {
        //TODO
        target.getPart1().setTargetReg("s6");
        target.getPart2().setTargetReg("s7");
        target.getPart3().setTargetReg("s8");
        target.getPart4().setTargetReg("s9");
        target.getPort().setTargetReg("s10");
        return target;
    }

    @Override
    public ActionPart initActionIpReg(ActionPart action) {
        action.setTargetReg("s11");
        return action;
    }
}
