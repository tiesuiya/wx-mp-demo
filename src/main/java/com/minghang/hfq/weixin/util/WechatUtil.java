package com.minghang.hfq.weixin.util;


import com.minghang.hfq.weixin.resources.WechatConfig;
import com.minghang.hfq.weixin.resources.WechatUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信工具类
 *
 * @Author: lihong
 * @Date: 2018/7/26
 * @Description 微信验签
 */
@Component
public final class WechatUtil implements Wechat {

    private static WechatConfig wechatConfig;

    private static WechatUrlConfig wechatUrlConfig;

    @Autowired
    private WechatUtil(WechatConfig wechatConfig, WechatUrlConfig wechatUrlConfig) {
        WechatUtil.wechatConfig = wechatConfig;
        WechatUtil.wechatUrlConfig = wechatUrlConfig;
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
        String temp = Sha1.gen(token, timestamp, nonce);

        // 对比signature
        return temp.equals(signature.toUpperCase());
    }


    /**
     * 构建，获取AccessToken请求的地址
     *
     * @return url
     */
    public static String getAccessTokenUrl() {
        return wechatUrlConfig.getAccessToken().replace(APP_ID, wechatConfig.getAppId()).replace(APP_SECRET, wechatConfig.getAppSecret());
    }
}
