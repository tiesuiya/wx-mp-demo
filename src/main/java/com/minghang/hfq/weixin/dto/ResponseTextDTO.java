package com.minghang.hfq.weixin.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 文本消息
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
@Data
@XStreamAlias("xml")
public class ResponseTextDTO extends ResponseBaseDTO {
    /**
     * 回复的消息内容
     */
    @XStreamAlias("content")
    private String content;

}
