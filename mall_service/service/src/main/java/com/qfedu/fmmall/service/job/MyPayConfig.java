package com.qfedu.fmmall.service.job;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

// 需要的请到 https://pay.weixin.qq.com/index.php/apply/applyment_home/guide_normal 申请成为商家
public class MyPayConfig implements WXPayConfig {
    @Override
    public String getAppID() {
        // 你的APPID
        return "";
    }

    @Override
    public String getMchID() {
        // 你的MchID
        return "";
    }

    @Override
    public String getKey() {
        // 你的key
        return "";
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
