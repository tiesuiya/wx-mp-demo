package com.minghang.hfq.weixin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信Url配置
 *
 * @Author: lihong
 * @Data: 2018/08/21
 * @Version: 1.0
 */
@Data
@Component
public class WechatUrlConfig {

    /**
     * 获取access_token
     */
    @Value("${wechat.mp.url.accessToken}")
    private String accessToken;

}
