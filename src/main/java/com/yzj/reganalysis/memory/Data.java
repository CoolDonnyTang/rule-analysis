package com.yzj.reganalysis.memory;

import com.yzj.reganalysis.Entity.AddrInfo;
import com.yzj.reganalysis.Entity.IpRegexInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class  Data {
    private static final Logger logger = LoggerFactory.getLogger(Data.class);
    private String token;
    private AddrInfo addrInfo;
    private IpRegexInfo ipRegexInfo;

    @PostConstruct
    public void init() {
        this.token = UUID.randomUUID().toString().replaceAll("-", "");
        logger.info("========================token:" + this.token);
    }

    public String getToken() {
        return token;
    }

    public AddrInfo getAddrInfo() {
        return addrInfo;
    }

    public void setAddrInfo(AddrInfo addrInfo) {
        this.addrInfo = addrInfo;
    }

    public IpRegexInfo getIpRegexInfo() {
        return ipRegexInfo;
    }

    public void setIpRegexInfo(IpRegexInfo ipRegexInfo) {
        this.ipRegexInfo = ipRegexInfo;
    }
}
