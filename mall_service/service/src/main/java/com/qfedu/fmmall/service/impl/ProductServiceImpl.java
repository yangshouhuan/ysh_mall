package com.qfedu.fmmall.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.dao.ProductImgMapper;
import com.qfedu.fmmall.dao.ProductMapper;
import com.qfedu.fmmall.dao.ProductParamsMapper;
import com.qfedu.fmmall.dao.ProductSkuMapper;
import com.qfedu.fmmall.entity.*;
import com.qfedu.fmmall.service.ProductService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResultVO listRecommendProducts() {

        List<ProductVO> productVOS = productMapper.selectRecommendProducts();

        return new ResultVO(ResStatus.OK, "success", productVOS);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO getProductBasicInfo(String productId) {

        try {
            // 先判断 redis 内存中是否存在商品快照
            String productInfo = (String) stringRedisTemplate.boundHashOps("products").get(productId);
            if (productInfo != null) {
                Product product = objectMapper.readValue(productInfo, Product.class);

                // 图片
                String imgs = (String) stringRedisTemplate.boundHashOps("productImgs").get(productId);
                JavaType javaType1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
                List<ProductImg> productImgs = objectMapper.readValue(imgs, javaType1);

                // 商品套餐
                String skus = (String) stringRedisTemplate.boundHashOps("productSkus").get(productId);
                JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
                List<ProductImg> productSkus = objectMapper.readValue(skus, javaType2);

                HashMap<String, Object> basicInfo = new HashMap<>();
                basicInfo.put("product", product);
                basicInfo.put("productImgs", productImgs);
                basicInfo.put("productSkus", productSkus);

                return new ResultVO(ResStatus.OK, "success", basicInfo);
            }

            // 商品基本信息
            Example example = new Example(Product.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("productId", productId);
            criteria.andEqualTo("productStatus", 1); // 只查询状态为1的上架商品

            List<Product> products = productMapper.selectByExample(example);
            if (products.size() > 0) {

                Product product = products.get(0);
                String jsonStr = objectMapper.writeValueAsString(product);
                stringRedisTemplate.boundHashOps("products").put(productId, jsonStr);

                // 商品图片
                Example example1 = new Example(ProductImg.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("itemId", productId);
                List<ProductImg> productImgs = productImgMapper.selectByExample(example1);
                stringRedisTemplate.boundHashOps("productImgs").put(productId, objectMapper.writeValueAsString(productImgs));

                // 商品套餐
                Example example2 = new Example(ProductSku.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andEqualTo("productId", productId);
                criteria2.andEqualTo("status", 1);
                List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);
                stringRedisTemplate.boundHashOps("productSkus").put(productId, objectMapper.writeValueAsString(productSkus));

                HashMap<String, Object> basicInfo = new HashMap<>();
                basicInfo.put("product", product);
                basicInfo.put("productImgs", productImgs);
                basicInfo.put("productSkus", productSkus);

                return new ResultVO(ResStatus.OK, "success", basicInfo);
            }
        } catch (Exception e) {

        }
        return new ResultVO(ResStatus.NO, "查询商品不存在", null);
    }

    @Override
    public ResultVO getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);

        if (productParams.size() > 0) {
            return new ResultVO(ResStatus.OK, "success", productParams.get(0));
        } else {
            return new ResultVO(ResStatus.NO, "商品不存在", null);
        }
    }

    @Override
    public ResultVO getProductsByCategoryId(int categoryId, int pageNum, int limit) {
        // 查询分页数据
        int start = (pageNum - 1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByCategoryId(categoryId, start, limit);
        // 查询当前类别下的总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("categoryId", categoryId);
        int count = productMapper.selectCountByExample(example);
        // 计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count, pageCount, productVOS);

        return new ResultVO(ResStatus.OK, "success", pageHelper);
    }

    @Override
    public ResultVO listBrands(int categoryId) {
        List<String> brands = productMapper.selectBrandByCategoryId(categoryId);
        return new ResultVO(ResStatus.OK, "success", brands);
    }

    /**
     * 根据关键字查询商品
     * @param kw
     * @param pageNum
     * @param limit
     * @return
     */
    @Override
    public ResultVO searchProduct(String kw, int pageNum, int limit) {
        kw = "%" + kw + "%";
        int start = (pageNum - 1) * limit;
        List<ProductVO> productVOS = productMapper.selectProductByKeyword(kw, start, limit);

        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName", kw);
        int count = productMapper.selectCountByExample(example);

        // 计算总页数
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
        PageHelper<ProductVO> productVOPageHelper = new PageHelper<>(count, pageCount, productVOS);

        return new ResultVO(ResStatus.OK, "success", productVOPageHelper);
    }

    /**
     * 返回对应关键字对应的品牌
     * @param keyword
     * @return
     */
    @Override
    public ResultVO listBrands(String keyword) {
        keyword = "%" + keyword + "%";
        List<String> brands = productMapper.selectBrandByKeyword(keyword);
        return new ResultVO(ResStatus.OK, "success", brands);
    }
}
