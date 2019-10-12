package com.yzj.reganalysis.service.impl;

import com.yzj.reganalysis.Entity.ActionPart;
import com.yzj.reganalysis.Entity.IpAddrStatus;
import com.yzj.reganalysis.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {
    private static final String ZERO_REG_KEY = "$zero";
    private static final String TMP_REG_KEY = "$at0";

    private static final String SOURCE_PART1_TARGET_VALUE_REG_KEY = "$s11";
    private static final String SOURCE_PART2_TARGET_VALUE_REG_KEY = "$s12";
    private static final String SOURCE_PART3_TARGET_VALUE_REG_KEY = "$s13";
    private static final String SOURCE_PART4_TARGET_VALUE_REG_KEY = "$s14";
    private static final String SOURCE_PORT_TARGET_VALUE_REG_KEY = "$s15";

    private static final String TARGET_PART1_TARGET_VALUE_REG_KEY = "$s21";
    private static final String TARGET_PART2_TARGET_VALUE_REG_KEY = "$s22";
    private static final String TARGET_PART3_TARGET_VALUE_REG_KEY = "$s23";
    private static final String TARGET_PART4_TARGET_VALUE_REG_KEY = "$s24";
    private static final String TARGET_PORT_TARGET_VALUE_REG_KEY = "$s25";

    private Map<String, String> regKeyAndValue;

    @PostConstruct
    private void init() {
        regKeyAndValue = new HashMap<>();
        regKeyAndValue.put(ZERO_REG_KEY, "");
        regKeyAndValue.put(TMP_REG_KEY, "");

        regKeyAndValue.put(SOURCE_PART1_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(SOURCE_PART2_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(SOURCE_PART3_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(SOURCE_PART4_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(SOURCE_PORT_TARGET_VALUE_REG_KEY, "");

        regKeyAndValue.put(TARGET_PART1_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(TARGET_PART2_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(TARGET_PART3_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(TARGET_PART4_TARGET_VALUE_REG_KEY, "");
        regKeyAndValue.put(TARGET_PORT_TARGET_VALUE_REG_KEY, "");
    }

    @Override
    public IpAddrStatus initSourceIpReg(IpAddrStatus source) {
        //TODO
        source.getPart1().setTargetReg(SOURCE_PART1_TARGET_VALUE_REG_KEY);
        source.getPart1().setTempReg(TMP_REG_KEY);
        source.getPart1().setZeroReg(ZERO_REG_KEY);

        source.getPart2().setTargetReg(SOURCE_PART2_TARGET_VALUE_REG_KEY);
        source.getPart2().setTempReg(TMP_REG_KEY);
        source.getPart2().setZeroReg(ZERO_REG_KEY);

        source.getPart3().setTargetReg(SOURCE_PART3_TARGET_VALUE_REG_KEY);
        source.getPart3().setTempReg(TMP_REG_KEY);
        source.getPart3().setZeroReg(ZERO_REG_KEY);

        source.getPart4().setTargetReg(SOURCE_PART4_TARGET_VALUE_REG_KEY);
        source.getPart4().setTempReg(TMP_REG_KEY);
        source.getPart4().setZeroReg(ZERO_REG_KEY);

        source.getPort().setTargetReg(SOURCE_PORT_TARGET_VALUE_REG_KEY);
        source.getPort().setTempReg(TMP_REG_KEY);
        source.getPort().setZeroReg(ZERO_REG_KEY);

        return source;
    }

    @Override
    public IpAddrStatus initTargetIpReg(IpAddrStatus target) {
        //TODO
        target.getPart1().setTargetReg(TARGET_PART1_TARGET_VALUE_REG_KEY);
        target.getPart1().setTempReg(TMP_REG_KEY);
        target.getPart1().setZeroReg(ZERO_REG_KEY);

        target.getPart2().setTargetReg(TARGET_PART2_TARGET_VALUE_REG_KEY);
        target.getPart2().setTempReg(TMP_REG_KEY);
        target.getPart2().setZeroReg(ZERO_REG_KEY);

        target.getPart3().setTargetReg(TARGET_PART3_TARGET_VALUE_REG_KEY);
        target.getPart3().setTempReg(TMP_REG_KEY);
        target.getPart3().setZeroReg(ZERO_REG_KEY);

        target.getPart4().setTargetReg(TARGET_PART4_TARGET_VALUE_REG_KEY);
        target.getPart4().setTempReg(TMP_REG_KEY);
        target.getPart4().setZeroReg(ZERO_REG_KEY);

        target.getPort().setTargetReg(TARGET_PORT_TARGET_VALUE_REG_KEY);
        target.getPort().setTempReg(TMP_REG_KEY);
        target.getPort().setZeroReg(ZERO_REG_KEY);

        return target;
    }

    @Override
    public ActionPart initActionIpReg(ActionPart action) {
        action.setTargetReg("s11");
        return action;
    }

    @Override
    public String getRegConfig(String regKey) {
        return regKeyAndValue.get(regKey);
    }

    @Override
    public Map<String, String> getRegKeyAndValue() {
        return regKeyAndValue;
    }

}
