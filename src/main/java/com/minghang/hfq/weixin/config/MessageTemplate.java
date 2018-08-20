package com.minghang.hfq.weixin.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信消息模板
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
@Data
@Component
public class MessageTemplate {

    /**
     * 首次关注提示
     */
    @Value("${wechat-mp.message.template.welcome}")
    private String welcome;

    /**
     * 默认回复
     */
    @Value("${wechat-mp.message.template.prompt}")
    private String prompt;
}
