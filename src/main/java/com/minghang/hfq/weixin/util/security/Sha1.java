package com.minghang.hfq.weixin.util.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;


/**
 * SHA1加密工具类
 *
 * @Author: lihong
 * @Date: 2018/7/26
 * @Description 加密工具类
 */
public final class Sha1 {

    private Sha1() {
    }

    /**
     * 串接arr参数，生成sha1 digest
     *
     * @param arr 参与计算的字符串数组
     * @return 计算结果
     */
    public static String gen(String... arr) {
        if (StringUtils.isAnyEmpty(arr)) {
            throw new IllegalArgumentException("非法请求，部分参数为空");
        }

        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String a : arr) {
            sb.append(a);
        }
        return DigestUtils.sha1Hex(sb.toString());
    }
}
