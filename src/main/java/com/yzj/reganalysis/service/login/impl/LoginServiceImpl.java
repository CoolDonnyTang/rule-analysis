package com.yzj.reganalysis.service.login.impl;

import com.yzj.reganalysis.memory.Data;
import com.yzj.reganalysis.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private Data data;
    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean login(String token) {
        if(data.getToken().equals(token)) {
            String sessionId = request.getSession().getId();
            Object t =  request.getServletContext().getAttribute("loginUser");
            request.getServletContext().setAttribute("loginUser", sessionId);
            return true;
        }
        return false;
    }
}
