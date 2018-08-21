package com.minghang.hfq.weixin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信配置
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
@Data
@Component
public class WechatConfig {

    @Value("${wechat.mp.appId}")
    private String appId;

    @Value("${wechat.mp.appSecret}")
    private String appSecret;

    @Value("${wechat.mp.token}")
    private String token;

}
