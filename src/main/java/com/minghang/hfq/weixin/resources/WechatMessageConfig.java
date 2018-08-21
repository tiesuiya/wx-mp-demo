package com.minghang.hfq.weixin.resources;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信消息配置
 *
 * @Author: lihong
 * @Date: 2018/8/21
 * @Description
 */
@Data
@Component
public class WechatMessageConfig {
    /**
     * 首次关注提示
     */
    @Value("${wechat.mp.message.welcome}")
    private String welcome;

    /**
     * 默认回复
     */
    @Value("${wechat.mp.message.prompt}")
    private String prompt;
}
