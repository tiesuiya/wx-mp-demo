package com.minghang.hfq.weixin.controller;

import com.minghang.hfq.weixin.service.WechatServer;
import com.minghang.hfq.weixin.util.WechatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 微信控制器
 *
 * @Author: lihong
 * @Date: 2018/7/26
 * @Description 微信相关路由，如绑定
 */
@Slf4j
@Controller
@RequestMapping("/weixin")
public class WechatController {

    @Autowired
    private WechatServer wechatServer;

    /**
     * 验证绑定服务器
     *
     * @return 微信回调字符串
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String checkWeiXinServer(String signature, String timestamp, String nonce, String echostr) {
        if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
            log.debug("验证服务器成功");
            return echostr;
        }
        log.debug("验证服务器失败，{}", Arrays.asList(signature, timestamp, nonce, echostr));
        return null;
    }

    /**
     * 处理用户消息
     *
     * @param request xml回复消息
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST}, produces = "application/xml;charset=UTF-8")
    public String dispose(HttpServletRequest request) {
        return wechatServer.processRequest(request);
    }

}
