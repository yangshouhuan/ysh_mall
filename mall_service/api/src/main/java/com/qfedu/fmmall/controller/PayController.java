package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    /**
     * 支付回调
     */
    @PostMapping("/callback")
    public String success(HttpServletRequest request) throws Exception {
        ServletInputStream is = request.getInputStream();

        byte[] bs = new byte[1024];
        int len = -1;
        StringBuilder builder = new StringBuilder();
        while ((len = is.read(bs)) != -1) {
            builder.append(new String(bs, 0, len));
        }
        String s = builder.toString();
        // 将xml转换成map
        Map<String, String> map = WXPayUtil.xmlToMap(s);

        System.out.println("callback---");

        if (map != null && "success".equalsIgnoreCase(map.get("result_code"))) {
            // 表示支付成功
            // 修改订单状态
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId, "2");

            // 通过websocket向前端推送信息
            WebSocketServer.sendMsg(orderId, "1");

            if (i > 0) {
                // 支付响应
                HashMap<String, String> resp = new HashMap<>();
                resp.put("return_code", "success");
                resp.put("return_msg", "OK");
                resp.put("app_id", map.get("appid"));
                resp.put("result_code", "success");
                return WXPayUtil.mapToXml(resp);
            }
        }

        return null;
    }

}
