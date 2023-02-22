package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.ShoppingCartMapper;
import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.entity.ShoppingCartVO;
import com.qfedu.fmmall.service.ShoppingCartService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        // 添加购物车
        int i = shoppingCartMapper.insert(cart);
        if (i > 0) {
            return new ResultVO(ResStatus.OK, "success", null);
        }

        return new ResultVO(ResStatus.NO, "fail", null);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listShoppingCartByUserId(int userId) {

        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByUserId(userId);

        return new ResultVO(ResStatus.OK, "success", list);
    }

    @Override
    public ResultVO updateCartNum(int cartId, int cartNum) {

        int i = shoppingCartMapper.updateCartnumByCartid(cartId, cartNum);

        if (i > 0) {
            return new ResultVO(ResStatus.OK, "success", null);
        }

        return new ResultVO(ResStatus.NO, "fail", null);
    }

    @Override
    public ResultVO listShoppingCartsByCids(String cids) {

        String[] arr = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cartIds.add(Integer.parseInt(arr[i]));
        }

        List<ShoppingCartVO> shoppingCartVOS = shoppingCartMapper.selectShopcartByCids(cartIds);

        return new ResultVO(ResStatus.OK, "success", shoppingCartVOS);
    }
}
