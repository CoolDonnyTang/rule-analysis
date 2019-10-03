package com.yzj.reganalysis.service.addr.impl;

import com.yzj.reganalysis.Entity.AddrInfo;
import com.yzj.reganalysis.memory.Data;
import com.yzj.reganalysis.service.addr.AddrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddrInfoServiceImpl implements AddrInfoService {

    @Autowired
    private Data data;

    @Override
    public boolean hasAddrInfo() {
        if(data.getAddrInfo()!=null) {
            return true;
        }
        return false;
    }

    @Override
    public void saveAddrInfo(AddrInfo addr) {
        data.setAddrInfo(addr);
    }
}
