package com.minghang.hfq.weixin.service;

import com.google.gson.Gson;
import com.minghang.hfq.weixin.resources.WechatMessageConfig;
import com.minghang.hfq.weixin.dao.WechatDao;
import com.minghang.hfq.weixin.util.MessageUtil;
import com.minghang.hfq.weixin.util.WechatUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    private WechatMessageConfig messageConfig;

    @Autowired
    private WechatDao wechatDao;

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
                    respMessage = MessageUtil.createTextMsg(fromUserName, toUserName, System.currentTimeMillis(), MessageUtil.RESPONSE_MESSAGE_TYPE_TEXT, 0, messageConfig.getWelcome());
                }
            } else {
                // 默认返回的文本消息内容
                respMessage = MessageUtil.createTextMsg(fromUserName, toUserName, System.currentTimeMillis(), MessageUtil.RESPONSE_MESSAGE_TYPE_TEXT, 0, messageConfig.getPrompt());
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

    /**
     * 获取AccessToken缓存
     *
     * @return AccessToken缓存
     */
    public String getAccessTokenCache() {
        String accessToken = wechatDao.getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            // 通过api获取
            double expiresIn;
            String keyToken = "access_token";
            String keyExpires = "expires_in";

            String url = WechatUtil.getAccessTokenUrl();

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).get().addHeader("Cache-Control", "no-cache").build();
                Response response = client.newCall(request).execute();
                String responseStr = response.body().string();
                if (responseStr.contains(keyToken)) {
                    Gson gson = new Gson();
                    HashMap map = gson.fromJson(responseStr, HashMap.class);

                    accessToken = (String) map.get(keyToken);
                    expiresIn = Double.parseDouble(String.valueOf(map.get(keyExpires)));
                    // 存入Redis
                    wechatDao.putAccessToken(accessToken, (long) expiresIn);
                }
            } catch (IOException e) {
                log.error("获取accessToken出错", e.getMessage());
            }
        }
        return accessToken;
    }

}
