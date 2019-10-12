package com.yzj.reganalysis.util;

public class BinaryUtil {
    public static String toBinary(int data, int digits) {
        int value = 1 << digits | data;
        String bs = Integer.toBinaryString(value); //0x20 | 这个是为了保证这个string长度是6位数
        return  bs.substring(1);
    }
}
