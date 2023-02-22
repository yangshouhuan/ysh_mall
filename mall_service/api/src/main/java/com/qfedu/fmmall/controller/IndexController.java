package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.entity.ProductVO;
import com.qfedu.fmmall.service.CatetoryService;
import com.qfedu.fmmall.service.IndexImgService;
import com.qfedu.fmmall.service.ProductService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/index")
@Api(value = "提供列表", tags = "列表管理")
public class IndexController {

    @Autowired
    private IndexImgService indexImgService;
    @Autowired
    private CatetoryService catetoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/indeximg")
    @ApiOperation("轮播图接口")
    public ResultVO listIndexImg() {
        return indexImgService.listIndexImgs();
    }

    @GetMapping("/categorylist")
    @ApiOperation("商品分类查询接口")
    public ResultVO listCategory() {
        return catetoryService.listCategories();
    }

    @GetMapping("/listrecmomends")
    @ApiOperation("推荐商品查询接口")
    public ResultVO listRecommendProducts() {
        return productService.listRecommendProducts();
    }

    @GetMapping("/listcategoryrecmomends")
    @ApiOperation("分类推荐商品查询接口")
    public ResultVO listRecommendProductsByCategory() {
        return catetoryService.listFirstLevelCategories();
    }

}
