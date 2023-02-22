package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.service.UserAddressService;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(value = "提供收货地址相关接口", tags = "地址管理")
@RequestMapping("/useraddr")
public class UserAddrControler {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int",name = "userId", value = "用户id",required = true)
    public ResultVO listAddr(Integer userId) {
        return userAddressService.listAddressByUid(userId);
    }

}
