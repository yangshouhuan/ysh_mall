package com.qfedu.fmmall.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.dao.CategoryMapper;
import com.qfedu.fmmall.entity.CategoryVO;
import com.qfedu.fmmall.entity.IndexImg;
import com.qfedu.fmmall.service.CatetoryService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CatetoryServiceImpl implements CatetoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 查询分类列表，共三层
     * @return
     */
    @Override
    public ResultVO listCategories() {

        List<CategoryVO> categoryVOList = null;

        try {
            // 判断 redis 中是否有值
            String categories = stringRedisTemplate.boundValueOps("categories").get();
            if (categories != null) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CategoryVO.class);
                categoryVOList = objectMapper.readValue(categories, javaType);
            } else {
                categoryVOList = categoryMapper.selectAllCategories();
                String str = objectMapper.writeValueAsString(categoryVOList);
                // 设置值和过期时间
                stringRedisTemplate.boundValueOps("categories").set(str, 1, TimeUnit.DAYS);
            }
        } catch (Exception e) {}

        return new ResultVO(ResStatus.OK, "success", categoryVOList);
    }

    /**
     * 查询一级分类下的销量最高的6个商品
     * @return
     */
    @Override
    public ResultVO listFirstLevelCategories() {

        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();

        return new ResultVO(ResStatus.OK, "success", categoryVOS);
    }
}
