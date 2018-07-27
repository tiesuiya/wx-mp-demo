package com.minghang.hfq.weixin.bean;

import com.minghang.hfq.weixin.util.xml.XStreamCdataConverter;
import com.minghang.hfq.weixin.util.xml.XStreamUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

/**
 * 公众号消息对象
 *
 * @Author: lihong
 * @Date: 2018/7/27
 * @Description
 */
@Data
@XStreamAlias("xml")
public class WxMpXmlMessage {

    // @XStreamConverter(value = XStreamCdataConverter.class)一句的作用为：Object to XML时为内容添加CDATA标记

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCdataConverter.class)
    private String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCdataConverter.class)
    private String fromUserName;

    @XStreamAlias("CreateTime")
    @XStreamConverter(value = XStreamCdataConverter.class)
    private String createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCdataConverter.class)
    private String msgType;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCdataConverter.class)
    private String content;

    @XStreamAlias("MsgId")
    @XStreamConverter(value = XStreamCdataConverter.class)
    private String msgId;


    /**
     * XML to Object
     *
     * @param xml xml字符串
     * @return 公众号消息对象
     */
    public static WxMpXmlMessage buildFromXml(String xml) {
        XStream xStream = XStreamUtil.initXSteam(WxMpXmlMessage.class);
        return (WxMpXmlMessage) xStream.fromXML(xml);
    }

    /**
     * Object to XML
     *
     * @return xml字符串
     */
    public String toXml() {
        XStream xStream = XStreamUtil.initXSteam(WxMpXmlMessage.class);
        return xStream.toXML(this);
    }
}
