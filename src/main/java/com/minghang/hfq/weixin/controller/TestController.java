package com.minghang.hfq.weixin.controller;

import com.minghang.hfq.weixin.service.WechatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试控制器
 *
 * @Author: lihong
 * @Date: 2018/7/26
 * @Description 微信相关路由，如绑定
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private WechatServer wechatServer;

    /**
     * 验证绑定服务器
     *
     * @return 微信回调字符串
     */
    @GetMapping("getAccessToken")
    @ResponseBody
    public String getAccessToken() {
        log.debug(String.valueOf(System.currentTimeMillis()));
        return wechatServer.getAccessTokenCache();
    }

}
