package com.minghang.hfq.weixin.service;

import com.minghang.hfq.weixin.config.MessageTemplate;
import com.minghang.hfq.weixin.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 微信接收消息，解析消息，回复消息相关
 *
 * @Author: lihong
 * @Data: 2018/08/20
 * @Version: 1.0
 */
@Service
@Slf4j
public class WechatServer {

    @Autowired
    private MessageTemplate messageTemplate;

    /**
     * 处理微信发来的请求
     *
     * @param request 微信服务端请求
     * @return xml回复消息
     */
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;

        try {
            request.setCharacterEncoding("UTF-8");

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 进行相关操作时会先判断用户的状态
            if (msgType.equals(MessageUtil.REQUEST_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    // 默认返回的文本消息内容(首次关注提示)
                    respMessage = MessageUtil.createTextMsg(fromUserName, toUserName, System.currentTimeMillis(), MessageUtil.RESPONSE_MESSAGE_TYPE_TEXT, 0, messageTemplate.getWelcome());
                }
            } else {
                // 默认返回的文本消息内容
                respMessage = MessageUtil.createTextMsg(fromUserName, toUserName, System.currentTimeMillis(), MessageUtil.RESPONSE_MESSAGE_TYPE_TEXT, 0, messageTemplate.getPrompt());
            }
        } catch (Exception e) {
            // 记录日志
            log.error("处理微信服务端消息错误：{}", e.getLocalizedMessage());
            // 发送邮件
            String title = "wechat-mp异常邮件";
            String content = "wechat-mp异常 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " \n " + e.getLocalizedMessage();
        }
        return respMessage;
    }

}
