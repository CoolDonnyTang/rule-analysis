package com.yzj.reganalysis.controller;

import com.yzj.reganalysis.service.login.LoginService;
import com.yzj.reganalysis.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVo<?> login(String token) {
        ResultVo<?> result = new ResultVo();
        if(loginService.login(token)) {
            result.setStatus(200);
        } else {
            result.setStatus(401);
            result.setMessage("无效的Token");
        }
        return result;
    }
}
