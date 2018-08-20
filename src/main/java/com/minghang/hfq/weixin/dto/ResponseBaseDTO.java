package com.minghang.hfq.weixin.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 消息基类
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
@Data
public class ResponseBaseDTO {
    /**
     * 接收方帐号（收到的OpenID）   
     */
    @XStreamAlias("ToUserName")
    private String toUserName;

    /**
     * 开发者微信号   
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间（整型）   
     */
    @XStreamAlias("CreateTime")
    private Long createTime;

    /**
     * 消息类型（text/music/news）   
     */
    @XStreamAlias("MsgType")
    private String msgType;

    /**
     * 位0x0001被标志时，星标刚收到的消息   
     */
    @XStreamAlias("FuncFlag")
    private Integer funcFlag;

}
