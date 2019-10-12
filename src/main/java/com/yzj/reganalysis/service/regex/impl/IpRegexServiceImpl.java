package com.yzj.reganalysis.service.regex.impl;

import com.yzj.reganalysis.Entity.*;
import com.yzj.reganalysis.controller.IpRegexController;
import com.yzj.reganalysis.memory.Data;
import com.yzj.reganalysis.service.ConfigService;
import com.yzj.reganalysis.service.regex.IpRegexService;
import com.yzj.reganalysis.util.CommandParser;
import com.yzj.reganalysis.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpRegexServiceImpl implements IpRegexService {
    private static final Logger log = LoggerFactory.getLogger(IpRegexService.class);

    @Autowired
    ConfigService configService;
    @Autowired
    private Data data;

    @Override
    public IpRegexInfo getCurrentIpRegex() {
        IpRegexInfo currentReg = data.getIpRegexInfo();
//        if(currnetReg==null) {
//            currnetReg = new IpRegexInfo();
//        }
        return currentReg;
    }

    @Override
    public IpRegexInfo updateIpRegex(IpRegexInfo ipRegexInfo) {
        try {
            //获取解析状态
            IpAddrStatus source = new IpAddrStatus(ipRegexInfo.getSourceIP());
            IpAddrStatus target = new IpAddrStatus(ipRegexInfo.getTargetIP());
            ActionPart action = new ActionPart(ipRegexInfo.getActionType());

            //初始化寄存器地址的key
            configService.initSourceIpReg(source);
            configService.initTargetIpReg(target);
            configService.initActionIpReg(action);

            //TODO 将解析状态转换为指令并发送
            //解析rule
            CommandParser parser = new CommandParser(source, target, action);
            CommandParseResultBO parseResult = parser.parse();
            log.info("---------------------------------------------------");
            log.info(StringUtils.join(parseResult.getAllCommandString(), "\n"));
            ipRegexInfo.setCmdParseResult(parseResult);

            List<String> binaryCode = configService.convertCmdToBinary(parseResult.getAllCommand());
            ipRegexInfo.setBinaryCode(binaryCode);

            ipRegexInfo.setMessage("成功");
            ipRegexInfo.setStatus(Constants.SUCCESS);
        } catch (Exception e) {
            ipRegexInfo.setMessage(e.getMessage());
            ipRegexInfo.setStatus(Constants.FAILED);
            throw new RuntimeException(e);
        } finally {
            data.setIpRegexInfo(ipRegexInfo);
        }
        return ipRegexInfo;
    }
}
