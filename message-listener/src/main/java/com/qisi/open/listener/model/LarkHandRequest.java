package com.qisi.open.listener.model;

import lombok.Data;

/**
 * @author : qisi
 * @date: 2023/2/21
 * @description: 飞书订阅回调对象
 */
@Data
public class LarkHandRequest {
    private String encrypt;
    private String challenge;
    private String token;
    private String type;
}
