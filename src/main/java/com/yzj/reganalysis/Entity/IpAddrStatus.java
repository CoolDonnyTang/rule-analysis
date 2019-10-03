package com.yzj.reganalysis.Entity;

import com.yzj.reganalysis.util.RegUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IpAddrStatus {
    private IpPart part1;
    private IpPart part2;
    private IpPart part3;
    private IpPart part4;
    private IpPart port;

    public IpAddrStatus(String ipReg) {
        String ip = ipReg.split(":")[0];
        String port = ipReg.split(":")[1];
        String[] ipParts = ip.split("\\.");
        String content = RegUtil.findGroup1String(ipParts[0], "\\((.*)\\)");
        part1 = new IpPart(content);

        content = RegUtil.findGroup1String(ipParts[1], "\\((.*)\\)");
        part2 = new IpPart(content);

        content = RegUtil.findGroup1String(ipParts[2], "\\((.*)\\)");
        part3 = new IpPart(content);

        content = RegUtil.findGroup1String(ipParts[3], "\\((.*)\\)");
        part4 = new IpPart(content);

        content = RegUtil.findGroup1String(port, "\\((.*)\\)");
        this.port = new IpPart(content, true);
    }

    public List<IpPart> getParts() {
        List<IpPart> result = new ArrayList<>();
        if(!this.part1.isEmpty()) {
            result.add(this.part1);
        }
        if(!this.part2.isEmpty()) {
            result.add(this.part2);
        }
        if(!this.part3.isEmpty()) {
            result.add(this.part3);
        }
        if(!this.part4.isEmpty()) {
            result.add(this.part4);
        }
        if(!this.port.isEmpty()) {
            result.add(this.port);
        }
        return result;
    }
}
