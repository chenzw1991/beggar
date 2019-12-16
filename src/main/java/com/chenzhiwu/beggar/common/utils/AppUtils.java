package com.chenzhiwu.beggar.common.utils;

import org.apache.commons.lang3.RandomUtils;

import java.math.BigInteger;
import java.text.DecimalFormat;

public class AppUtils {
    public static String generateAppId(long id) {
        DecimalFormat decimalFormat = new DecimalFormat("00000000");
        // 该范围数字再加上8位后的10进制数, 转16进制保证16位长度, 且从1xxxx-fxxxx不等
        Long prefixValue = RandomUtils.nextLong(11529215046L, 184467440737L);
        String resultValue = prefixValue + decimalFormat.format(id);
        return new BigInteger(resultValue, 10).toString(16);
    }
}
