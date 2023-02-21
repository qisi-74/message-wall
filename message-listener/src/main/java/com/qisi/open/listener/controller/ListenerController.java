package com.qisi.open.listener.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qisi.open.listener.client.LarkClient;
import com.qisi.open.listener.model.LarkHandRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : qisi
 * @date: 2023/2/21
 * @description: 接受回调事件
 */
@Slf4j
@RestController
@RequestMapping("/listener")
public class ListenerController {
    @Resource
    private LarkClient larkClient;

    @PostMapping("/lark")
    public JSONObject larkListener(@RequestBody LarkHandRequest request) {
        log.info("握手开始{}", JSON.toJSONString(request));
        String challenge=request.getChallenge();
        if (StringUtils.hasText(request.getEncrypt())) {
            try {
                String decrypt = larkClient.decrypt(request.getEncrypt());
                LarkHandRequest result = JSONObject.parseObject(decrypt, LarkHandRequest.class);
                challenge=result.getChallenge();
            } catch (Exception e) {
                log.error("握手失败", e);
                return null;
            }
        }
        log.info("握手成功{}", challenge);
        JSONObject result=new JSONObject();
        result.put("challenge",challenge);
        return result;
    }

}
