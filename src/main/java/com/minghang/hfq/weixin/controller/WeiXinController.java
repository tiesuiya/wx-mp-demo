package com.minghang.hfq.weixin.controller;

import com.minghang.hfq.weixin.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信控制器
 *
 * @Author: lihong
 * @Date: 2018/7/26
 * @Description 微信相关路由，如绑定
 */
@Slf4j
@RestController
@RequestMapping("/weixin")
public class WeiXinController {

    /**
     * 验证绑定服务器
     *
     * @return 微信回调字符串
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String checkWeiXinServer(String signature, String timestamp, String nonce, String echostr) {
        if (WxUtil.checkSignature(signature, timestamp, nonce)) {
            log.debug("验证服务器成功");
            return echostr;
        }
        log.debug("验证服务器失败，{}", Arrays.asList(signature, timestamp, nonce, echostr));
        return null;
    }

    /**
     * 处理用户消息
     */
    @RequestMapping(method = RequestMethod.POST)
    public String handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();
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
        System.out.println(map);
        String resultMessage = "";
        if ("text".equals(map.get("MsgType"))) {
            if (map.get("Content").startsWith("苏喂")) {
                int length = map.get("Content").length();
                String responseStr = "";
                for (int i = 0; i < length; i++) {
                    responseStr += "嘟";
                }
                responseStr += "！";
                resultMessage =  "<xml>" +
                        "<ToUserName>< ![CDATA[gh_138ac3fa6b80] ]></ToUserName>" +
                        "<FromUserName>< ![CDATA[oM7dR1j97khdvaTyVcTebFss4Mrs] ]></FromUserName>" +
                        "<CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
                        "<MsgType>< ![CDATA[text] ]></MsgType>" +
                        "<Content>< ![CDATA[" + responseStr + "] ]></Content>" +
                        "</xml>";
            } else if ("jb".equals(map.get("Content"))) {
                resultMessage = "<xml>" +
                        "<ToUserName>< ![CDATA[gh_138ac3fa6b80] ]></ToUserName>" +
                        "<FromUserName>< ![CDATA[oM7dR1j97khdvaTyVcTebFss4Mrs] ]></FromUserName>" +
                        "<CreateTime>" + System.currentTimeMillis() + "</CreateTime>" +
                        "<MsgType>< ![CDATA[image] ]></MsgType>" +
                        "<Image>" +
                        "<MediaId>< ![CDATA[bDeuAnFoY20l_MG3PyZoMAEUfNVVtwU9C9y8kUISHeHVYYoA7VIZmZR2_538ps] ]></MediaId>" +
                        "</Image>" +
                        "</xml>";
            }
        }
        // 响应消息
        PrintWriter out = response.getWriter();
        out.print(resultMessage);
        out.close();
        return "success";
    }
}
