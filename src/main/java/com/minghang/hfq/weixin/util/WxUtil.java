package com.minghang.hfq.weixin.util;


import com.minghang.hfq.weixin.util.security.SHA1;

/**
 * 微信工具类
 *
 * @Author: lihong
 * @Date: 2018/7/26
 * @Description 微信验签
 */
public final class WxUtil {

    private WxUtil() {
    }

    /**
     * 验证签名
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 验证结果
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        // 获取本地token
        String token = "TIESUIYA";

        // 计算sha1
        String temp = SHA1.gen(token, timestamp, nonce);

        // 对比signature
        return temp.equals(signature.toUpperCase());
    }

}
