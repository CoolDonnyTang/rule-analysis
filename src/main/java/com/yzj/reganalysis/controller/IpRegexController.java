package com.yzj.reganalysis.controller;

import com.yzj.reganalysis.Entity.AddrInfo;
import com.yzj.reganalysis.Entity.IpRegexInfo;
import com.yzj.reganalysis.service.regex.IpRegexService;
import com.yzj.reganalysis.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpRegexController {
    private static final Logger log = LoggerFactory.getLogger(IpRegexController.class);

    @Autowired
    private IpRegexService regexService;

    @RequestMapping(value = "/ipReg", method = RequestMethod.GET)
        public ResultVo<IpRegexInfo> getCurrentIpRegxInfo() {
        ResultVo<IpRegexInfo> result = new ResultVo();
        IpRegexInfo info = regexService.getCurrentIpRegex();
        result.setData(info);
        result.setStatus(200);
        return result;
    }

    @RequestMapping(value = "/ipReg", method = RequestMethod.POST)
    public ResultVo<IpRegexInfo> updateIpRegxInfo(IpRegexInfo ipRegexInfo) {
        ResultVo<IpRegexInfo> result = new ResultVo();
        log.info("ipRegexInfo:" + ipRegexInfo);
        try {
            regexService.updateIpRegex(ipRegexInfo);
            result.setStatus(200);
            result.setMessage("Success.");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setStatus(500);
            result.setMessage(e.getMessage());
        }
        result.setData(ipRegexInfo);
        return result;
    }

}
