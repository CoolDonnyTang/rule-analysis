package com.yzj.reganalysis.service.regex;

import com.yzj.reganalysis.Entity.IpRegexInfo;

public interface IpRegexService {
    IpRegexInfo getCurrentIpRegex();

    IpRegexInfo updateIpRegex(IpRegexInfo ipRegexInfo);
}
