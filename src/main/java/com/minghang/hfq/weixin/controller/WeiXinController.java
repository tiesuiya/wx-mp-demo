package com.minghang.hfq.weixin.controller;

import com.minghang.hfq.weixin.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

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
     *
     * @param requestBody  收到微信的请求体
     * @param signature    请求签名
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param encType      编码方式
     * @param msgSignature 消息签名
     * @return 服务响应消息xml
     * @throws IOException
     * @throws DocumentException
     */
    @PostMapping(produces = "application/xml;charset=utf-8")
    public String handleMessage(@RequestBody String requestBody, String signature, String timestamp,
                                String nonce, String encType, String msgSignature) throws IOException, DocumentException {

        if (true) {
            return "<xml><ToUserName><![Cdata[oM7dR1j97khdvaTyVcTebFss4Mrs]]></ToUserName>" +
                    "<FromUserName><![Cdata[gh_138ac3fa6b80]]></FromUserName>" +
                    "<CreateTime>1532659308</CreateTime>" +
                    "<MsgType><![Cdata[text]]></MsgType>" +
                    "<Content><![Cdata[嘟嘟嘟嘟嘟嘟嘟嘟嘟]]></Content>" +
                    "<MsgId>6582721604207764727</MsgId>" +
                    "</xml>";
        }
        return "success";
    }
}
