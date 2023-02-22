package com.qfedu.fmmall.controller;

import com.github.wxpay.sdk.WXPay;
import com.qfedu.fmmall.service.job.MyPayConfig;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(value = "提供用户订单接口",tags = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("添加订单")
    @PostMapping("/add")
    public ResultVO add(String cids, @RequestBody Orders orders) {
        ResultVO resultVO = null;
        try {
            Map<String, String> ordreInfo = orderService.addOrder(cids, orders);

            if (ordreInfo.get("orderId") != null) {
                // 设置支付信息
                WXPay wxPay = new WXPay(new MyPayConfig());

                // 设置订单信息
                HashMap<String, String> data = new HashMap<>();
                data.put("body", ordreInfo.get("productNames"));  // 商品描述
                data.put("out_trade_no", ordreInfo.get("orderId"));  // 订单编号
                data.put("fee_type", "CNY");  // 支付币种
                // data.put("total_fee", orders.getActualAmount() * 100 + "");
                data.put("total_fee", "1");  // 支付金额, 分
                data.put("trade_type", "NATIVE");  // 交易类型
                //data.put("notify_url", "http://ysh.free.idcfengye.com/pay/callback");  // 支付响应url
                data.put("notify_url", "http://192.168.113.108:8090/pay/callback");  // 支付响应url

                // 发送请求
                Map<String, String> resp = wxPay.unifiedOrder(data);

                // 支付二维码地址
                ordreInfo.put("payUrl", resp.get("code_url"));
                System.out.println(resp);
                resultVO = new ResultVO(ResStatus.OK, "提交订单成功", ordreInfo);
            } else {
                resultVO = new ResultVO(ResStatus.NO, "提交订单失败", null);
            }
        } catch (SQLException e) {
            resultVO = new ResultVO(ResStatus.NO, "提交订单失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultVO;
    }

    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("oid") String orderId, @RequestHeader("token") String token) {
        return orderService.getOrderById(orderId);
    }

    @ApiOperation("根据状态获取用户订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "userId", value = "用户id",required = true),
            @ApiImplicitParam(dataType = "string",name = "status", value = "订单状态",required = false),
            @ApiImplicitParam(dataType = "int",name = "pageNum", value = "页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit", value = "每页条数",required = false, defaultValue = "5")
    })
    @GetMapping("/list")
    public ResultVO list(@RequestHeader("token") String token, String userId, String status, int pageNum, int limit) {
        return orderService.listOrders(userId, status, pageNum, limit);
    }
}
