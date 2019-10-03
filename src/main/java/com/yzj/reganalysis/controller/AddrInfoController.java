package com.yzj.reganalysis.controller;

import com.yzj.reganalysis.Entity.AddrInfo;
import com.yzj.reganalysis.service.addr.AddrInfoService;
import com.yzj.reganalysis.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddrInfoController {

    @Autowired
    private AddrInfoService addrInfoService;

    @RequestMapping(value = "/addr/status", method = RequestMethod.GET)
    public ResultVo<Boolean> getAddrInfoStatus() {
        ResultVo<Boolean> result = new ResultVo();
        if(addrInfoService.hasAddrInfo()) {
            result.setData(true);
        } else {
            result.setData(false);
        }
        result.setStatus(200);
        return result;
    }

    @RequestMapping(value = "/addr", method = RequestMethod.POST)
    public ResultVo<?> saveAddrInfo(AddrInfo addr) {
        ResultVo<?> result = new ResultVo();
        addrInfoService.saveAddrInfo(addr);
        result.setStatus(200);
        return result;
    }
}
