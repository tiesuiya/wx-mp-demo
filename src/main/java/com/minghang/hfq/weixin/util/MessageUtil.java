package com.minghang.hfq.weixin.util;

import com.minghang.hfq.weixin.dto.ResponseBaseDTO;
import com.minghang.hfq.weixin.dto.ResponseTextDTO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息处理工具类
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
public class MessageUtil {

    /**
     * 返回消息类型：文本
     */
    public static final String RESPONSE_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：文本
     */
    public static final String REQUEST_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：推送
     */
    public static final String REQUEST_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 自定义xstream实例
     */
    private static XStream xstream;

    static {
        xstream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点的转换都增加CDATA标记
                    boolean cdata = true;

                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
    }

    /**
     * 解析微信发来的请求（XML）
     * 若在Controller中配置了@RequestBody String requestBody，会解析失败
     * org.dom4j.DocumentException: Error on line 1 of document  : 文件提前结束。 Nested exception: 文件提前结束。
     * 目前不知道什么原因
     *
     * @param request 微信服务端请求
     * @return 请求参数map
     * @throws IOException       读取异常
     * @throws DocumentException 解析异常
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws IOException, DocumentException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>(20);
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 对象转换为xml
     *
     * @param t   目标对象
     * @param <T> 目标类型
     * @return xml
     */
    public static <T extends ResponseBaseDTO> String toXml(T t) {
        return xstream.toXML(t);
    }


    /**
     * 文本消息响应封装方法
     *
     * @param fromUserName 公众帐号
     * @param toUserName   接收方帐号（收到的OpenID）
     * @param createTime   消息创建时间 （整型）
     * @param mesType      消息类型（text/music/news）
     * @param funcFlag     位0x0001被标志时，星标刚收到的消息
     * @param content      回复的文本消息
     * @return xml文本消息
     */
    public static String createTextMsg(String fromUserName, String toUserName, long createTime, String mesType, int funcFlag, String content) {
        ResponseTextDTO textMessage = new ResponseTextDTO();
        // 公众帐号  
        textMessage.setToUserName(fromUserName);
        // 接收方帐号（收到的OpenID）
        textMessage.setFromUserName(toUserName);
        // 消息创建时间 （整型）   
        textMessage.setCreateTime(createTime);
        // 消息类型（text/music/news）
        textMessage.setMsgType(mesType);
        // 位0x0001被标志时，星标刚收到的消息
        textMessage.setFuncFlag(funcFlag);
        // 内容
        textMessage.setContent(content);

        return MessageUtil.toXml(textMessage);
    }
}
