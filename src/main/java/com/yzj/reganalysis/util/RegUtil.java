package com.yzj.reganalysis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {

    public static String findGroup1String(String str, String reg) {
        return findStringByGroup(str, reg, 1);
    }

    public static String findStringByGroup(String str, String reg, int group) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        if(m.find()) {
            return m.group(group);
        }
        return null;
    }
}
