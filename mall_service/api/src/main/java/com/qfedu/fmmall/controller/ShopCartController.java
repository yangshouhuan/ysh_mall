package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.entity.ShoppingCart;
import com.qfedu.fmmall.service.ShoppingCartService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopcart")
@CrossOrigin
@Api(value = "提供购物车相关接口", tags = "购物车管理")
public class ShopCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("增加购物车")
    @PostMapping("/add")
    public ResultVO addShoppingCart (@RequestBody ShoppingCart cart) {
        return shoppingCartService.addShoppingCart(cart);
    }

    @ApiOperation("获取购物车列表")
    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int", name = "userId", value = "用户id", required = true)
    // 要求必须传token
    public ResultVO list(Integer userId, @RequestHeader("token") String token) {
        if (userId != null) {
            return shoppingCartService.listShoppingCartByUserId(userId);
        }
        return new ResultVO(ResStatus.NO, "userId不能为空", null);
    }

    @ApiOperation("增加购物车数量")
    @PutMapping("/update/{cid}/{cnum}")
    public ResultVO updateCartNum(@PathVariable("cid") Integer cartId,
                                  @PathVariable("cnum") Integer cartNum) {
        return shoppingCartService.updateCartNum(cartId, cartNum);
    }

    @ApiOperation("购物车详情")
    @GetMapping("/listbycids")
    @ApiImplicitParam(dataType = "String", name = "cids", value = "购物车ids", required = true)
    public ResultVO listByCids(String cids) {
        return shoppingCartService.listShoppingCartsByCids(cids);
    }
}
