package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.OrderItemMapper;
import com.qfedu.fmmall.dao.OrdersMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.dao.ShoppingCartMapper;
import com.qfedu.fmmall.entity.*;
import com.qfedu.fmmall.service.OrderService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     *
     * @param cids      购物车id
     * @param orders    订单数据
     * @return
     */
    // @Override
    // Transactional: 表示方法执行完成之后，才做事务提交操作，如果有一个事务失败，则做回滚操作
    @Transactional
    public Map<String, String> addOrder(String cids, Orders orders) throws SQLException {
        logger.info("add order begin...");
        Map<String, String> map = new HashMap<>();

        String[] arr = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cartIds.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopcartByCids(cartIds);

        // 库存是否充足
        boolean f = true;
        String untitled = "";
        for (ShoppingCartVO sc : shoppingCartVOS) {
            if (Integer.parseInt(sc.getCartNum()) > sc.getSkuStock()) {
                f = false;
            }
            // 拼接商品名称
            untitled = untitled + sc.getProductName() + ", ";
        }


        if (f) { // 充足
            logger.info("库存充足...");
            orders.setUntitled(untitled);
            orders.setCancelTime(new Date());

            // 生成订单id
            String orderId = UUID.randomUUID().toString().replace("-", "");
            orders.setOrderId(orderId);

            // 保存订单
            int i = ordersMapper.insert(orders);
            //List<OrderItem> orderItems = new ArrayList<>();
            // 生成订单快照
            for (ShoppingCartVO sc : shoppingCartVOS) {
                int cnum = Integer.parseInt(sc.getCartNum());
                String itemId = (System.currentTimeMillis() + "") + (new Random().nextInt(8999) + 1000);
                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(), sc.getProductName(), sc.getProductImg(), sc.getSkuId(), sc.getSkuName(), new BigDecimal(sc.getSellPrice()), cnum, new BigDecimal(sc.getSellPrice() * cnum), new Date(), new Date(), 0);
                orderItemMapper.insert(orderItem);
            }
            //int j = orderItemMapper.insertList(orderItems);

            // 扣减库存
            for (ShoppingCartVO sc : shoppingCartVOS) {
                String skuId = sc.getSkuId();
                int newStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());

                ProductSku productSku = new ProductSku();
                // 需要设置主键
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                // updateByPrimaryKeySelective: 只会更新有值的属性
                int k = productSkuMapper.updateByPrimaryKeySelective(productSku);
            }

            // 删除购物车记录
            for (int cid : cartIds) {
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }

            map.put("orderId", orderId);
            map.put("productNames", untitled);
            logger.info("add order finish...");
            return map;
        } else { // 不充足
            return null;
        }
    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param status
     * @return
     */
    @Override
    public int updateOrderStatus(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);

        return ordersMapper.updateByPrimaryKeySelective(orders);
    }

    /**
     * 获取订单状态
     * @param orderId
     * @return
     */
    @Override
    public ResultVO getOrderById(String orderId) {

        Orders orders = ordersMapper.selectByPrimaryKey(orderId);

        return new ResultVO(ResStatus.OK, "success", orders.getStatus());
    }

    /**
     * 关闭订单，需要同时执行成功/失败
     * Isolation.SERIALIZABLE： 避免并发操作同一个数据
     * @param orderId
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void closeOrder(String orderId) {
        // 未支付，修改订单状态
        Orders cancleOrder = new Orders();
        cancleOrder.setOrderId(orderId);
        cancleOrder.setStatus("6");
        cancleOrder.setCloseType(1);
        ordersMapper.updateByPrimaryKeySelective(cancleOrder);

        // 还原库存
        Example example1 = new Example(OrderItem.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("orderId", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
        // 还原库存
        for (int j = 0; j < orderItems.size(); j++) {
            OrderItem orderItem = orderItems.get(j);
            ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getProductId());
            productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
            productSkuMapper.updateByPrimaryKey(productSku);
        }
    }

    /**
     * 根据状态分页查询订单列表
     * @param userId
     * @param status
     * @param pageNum
     * @param limit
     * @return
     */
    @Override
    public ResultVO listOrders(String userId, String status, int pageNum, int limit) {
        int start = (pageNum - 1) * limit;
        List<OrdersVO> ordersVOS = ordersMapper.selectOrders(userId, status, start, limit);

        // 查询总记录数
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("userId", userId);
        if (status != null && !"".equals(status)) {
            criteria.andLike("status", status);
        }
        int count = ordersMapper.selectCountByExample(example);
        int pageCount = (count % limit) == 0 ? (count / limit) : (count / limit) + 1;

        PageHelper<OrdersVO> ordersVOPageHelper = new PageHelper<>(count, pageCount, ordersVOS);
        return new ResultVO(ResStatus.OK, "success", ordersVOPageHelper);
    }
}
