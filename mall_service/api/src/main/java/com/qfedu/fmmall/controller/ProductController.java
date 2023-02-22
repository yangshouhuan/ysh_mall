package com.qfedu.fmmall.controller;

import com.qfedu.fmmall.service.ProductCommentsService;
import com.qfedu.fmmall.service.ProductService;
import com.qfedu.fmmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin
@Api(value = "提供商品信息", tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentsService commentsService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detailinfo/{pid}")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid) {

        return productService.getProductBasicInfo(pid);
    }

    @ApiOperation("商品参数信息查询接口")
    @GetMapping("/detailparams/{pid}")
    public ResultVO getProductParams(@PathVariable("pid") String pid) {

        return productService.getProductParamsById(pid);
    }

    @ApiOperation("商品评论查询接口")
    @GetMapping("/detailComments/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示条数", required = true),
    })
    public ResultVO getProductComments(@PathVariable("pid") String pid, int pageNum, int limit) {
        return commentsService.listCommentsByProductId(pid, pageNum, limit);
    }

    @ApiOperation("商品评论记录数")
    @GetMapping("/detailCommentsCount/{pid}")
    public ResultVO getCommentsCount(@PathVariable("pid") String pid) {
        return commentsService.getCommentsCountByProductId(pid);
    }


    @ApiOperation("根据类别查询商品接口")
    @GetMapping("/listbycid/{cid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示条数", required = true),
    })
    public ResultVO getProductByCategoryId(@PathVariable("cid") int cid, int pageNum, int limit) {
        return productService.getProductsByCategoryId(cid, pageNum, limit);
    }

    @ApiOperation("根据类别id查询商品品牌")
    @GetMapping("/listbrands/{cid}")
    public ResultVO getBrandsByCategoryId(@PathVariable("cid") int cid) {
        return productService.listBrands(cid);
    }

    @ApiOperation("根据关键字查询商品品牌")
    @GetMapping("/listkeyword")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "keyword", value = "关键字", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示条数", required = true),
    })
    public ResultVO searchProduct(String keyword,int pageNum,int limit) {
        return productService.searchProduct(keyword, pageNum, limit);
    }

    @ApiOperation("根据关键字查询商品品牌")
    @GetMapping("/listbrands")
    @ApiImplicitParam(dataType = "string", name = "keyword", value = "关键字", required = true)
    public ResultVO getBrandsByKeyword(String keyword) {
        return productService.listBrands(keyword);
    }
}
