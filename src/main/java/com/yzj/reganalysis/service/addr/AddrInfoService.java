package com.yzj.reganalysis.service.addr;

import com.yzj.reganalysis.Entity.AddrInfo;

public interface AddrInfoService {
    boolean hasAddrInfo();
    void saveAddrInfo(AddrInfo addr);
}
