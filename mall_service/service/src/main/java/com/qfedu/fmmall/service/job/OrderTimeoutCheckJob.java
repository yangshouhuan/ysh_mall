package com.qfedu.fmmall.service.job;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.qfedu.fmmall.dao.OrderItemMapper;
import com.qfedu.fmmall.dao.OrdersMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.entity.OrderItem;
import com.qfedu.fmmall.entity.Orders;
import com.qfedu.fmmall.entity.ProductSku;
import com.qfedu.fmmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeoutCheckJob {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderService orderService;

    private WXPay wxPay = new WXPay(new MyPayConfig());

    /**
     * 超时订单，修改状态
     * 每一分钟请求一次
     */
    //@Scheduled(cron = "0/10 * * * * ?")
    @Scheduled(cron = "0/60 * * * * ?")
    public void checkAndCloseOrder() throws Exception {
        System.out.println("-------这是——------");

        try {
            Example example = new Example(Orders.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", 1);
            // 超时时间约束为30分钟
            Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
            criteria.andLessThan("createTime", time);
            // 查询返回来的超时订单
            List<Orders> ordersList = ordersMapper.selectByExample(example);
            for (int i = 0; i < ordersList.size(); i++) {
                Orders order = ordersList.get(i);
                HashMap<String, String> params = new HashMap<>();
                params.put("out_trade_no", order.getOrderId());
                // 向微信确认订单为未支付订单
                Map<String, String> resp = wxPay.orderQuery(params);

                if ("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))) {
                    // 已支付, 修改订单状态
                    Orders updateOrder = new Orders();
                    updateOrder.setOrderId(order.getOrderId());
                    updateOrder.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(updateOrder);
                } else if ("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))) {
                    // 向微信请求关闭订单，避免在修改状态时用户支付订单
                    Map<String, String> map = wxPay.closeOrder(params);
                    // 关闭订单
                    orderService.closeOrder(order.getOrderId());
                }

                System.out.println(resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
