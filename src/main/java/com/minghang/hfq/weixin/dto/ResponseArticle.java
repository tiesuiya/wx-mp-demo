package com.minghang.hfq.weixin.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 图文消息
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
@Data
@XStreamAlias("xml")
public class ResponseArticle {
    /**
     * 图文消息名称
     */
    @XStreamAlias("Title")
    private String title;

    /**
     * 图文消息描述
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发
     * 者填写的基本资料中的Url一致
     */
    @XStreamAlias("PicUrl")
    private String picUrl;

    /**
     * 点击图文消息跳转链接
     */
    @XStreamAlias("Url")
    private String url;

}
