package com.yzj.reganalysis.service.impl;

import com.yzj.reganalysis.Entity.ActionPart;
import com.yzj.reganalysis.Entity.Command;
import com.yzj.reganalysis.Entity.IpAddrStatus;
import com.yzj.reganalysis.service.ConfigService;
import com.yzj.reganalysis.util.BinaryUtil;
import com.yzj.reganalysis.util.CommandParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static final String ACTION_TARGET_VALUE_REG_KEY = "$s31";

    private Map<String, Integer> regKeyAndValue;

    @PostConstruct
    private void init() {
        regKeyAndValue = new HashMap<>();
        regKeyAndValue.put(ZERO_REG_KEY, 0);
        regKeyAndValue.put(TMP_REG_KEY, 1);

        regKeyAndValue.put(SOURCE_PART1_TARGET_VALUE_REG_KEY, 21);
        regKeyAndValue.put(SOURCE_PART2_TARGET_VALUE_REG_KEY, 22);
        regKeyAndValue.put(SOURCE_PART3_TARGET_VALUE_REG_KEY, 23);
        regKeyAndValue.put(SOURCE_PART4_TARGET_VALUE_REG_KEY, 24);
        regKeyAndValue.put(SOURCE_PORT_TARGET_VALUE_REG_KEY, 29);

        regKeyAndValue.put(TARGET_PART1_TARGET_VALUE_REG_KEY, 25);
        regKeyAndValue.put(TARGET_PART2_TARGET_VALUE_REG_KEY, 26);
        regKeyAndValue.put(TARGET_PART3_TARGET_VALUE_REG_KEY, 27);
        regKeyAndValue.put(TARGET_PART4_TARGET_VALUE_REG_KEY, 28);
        regKeyAndValue.put(TARGET_PORT_TARGET_VALUE_REG_KEY, 30);

        regKeyAndValue.put(ACTION_TARGET_VALUE_REG_KEY, 31);

        //Command type
        regKeyAndValue.put(CommandParser.CMD_TYPE_NOP, 0);
        regKeyAndValue.put(CommandParser.CMD_TYPE_ADDI, 1);
        regKeyAndValue.put(CommandParser.CMD_TYPE_SLT, 2);
        regKeyAndValue.put(CommandParser.CMD_TYPE_BEQ, 3);
        regKeyAndValue.put(CommandParser.CMD_TYPE_BNE, 4);
        regKeyAndValue.put(CommandParser.CMD_TYPE_J, 5);
        regKeyAndValue.put(CommandParser.CMD_TYPE_STOP, 6);

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
        action.setTargetReg(ACTION_TARGET_VALUE_REG_KEY);
        action.setZeroReg(ZERO_REG_KEY);
        action.setTempReg(TMP_REG_KEY);
        return action;
    }

    @Override
    public Integer getRegConfig(String regKey) {
        return regKeyAndValue.get(regKey);
    }

    @Override
    public Map<String, Integer> getRegKeyAndValue() {
        return regKeyAndValue;
    }

    public List<String> convertCmdToBinary(List<Command> cmd) {
        List<String> result = new ArrayList<>();
        for(Command c : cmd) {
            StringBuilder sb = new StringBuilder();
            //exec cmd type
            sb.append(BinaryUtil.toBinary(getRegConfig(c.getType()), 6));

            //exec reg2
            if(c.getReg2()==null) {
                sb.append(BinaryUtil.toBinary(0, 5));
            } else {
                sb.append(BinaryUtil.toBinary(getRegConfig(c.getReg2()), 5));
            }

            //exec reg1
            if(c.getReg1()==null) {
                sb.append(BinaryUtil.toBinary(0, 5));
            } else {
                sb.append(BinaryUtil.toBinary(getRegConfig(c.getReg1()), 5));
            }

            //exec target value
            if(c.getTargetValue()==null) {
                sb.append(BinaryUtil.toBinary(0, 16));
            } else {
                sb.append(BinaryUtil.toBinary(Integer.valueOf(c.getTargetValue()), 16));
            }

            result.add(sb.toString());
        }
        return result;
    }
}
