package com.yzj.reganalysis.service.login;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    boolean login(String token);
}
