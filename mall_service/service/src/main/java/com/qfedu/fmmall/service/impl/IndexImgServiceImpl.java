package com.qfedu.fmmall.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.dao.IndexImgMapper;
import com.qfedu.fmmall.entity.IndexImg;
import com.qfedu.fmmall.entity.ProductImg;
import com.qfedu.fmmall.service.IndexImgService;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgList = null;

        try {
            // 使用 redis 存放轮播图信息
            //stringRedisTemplate.opsForValue().get("indexImgs");
            String imgsStr = stringRedisTemplate.boundValueOps("indexImgs").get();
            if (imgsStr != null) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                indexImgList = objectMapper.readValue(imgsStr, javaType);
            } else {
                // 加锁，防止同时有大量请求进来查数据库和操作redis
                synchronized (this) {
                    // 再次查询redis
                    String s = stringRedisTemplate.boundValueOps("indexImgs").get();
                    // 再次判断redis中是否有值
                    if (s == null) {
                        indexImgList = indexImgMapper.listIndexImgs();
                        if (indexImgList != null) {
                            stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgList));
                            // 设置过期时间为 一天
                            stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);
                        } else {
                            // 当数据库返回为null时，写一个非空的数据到 redis 中去，并且设置过期时间
                            List<IndexImg> arr = new ArrayList<>();
                            stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(arr));
                            // 设置过期时间为 一天
                            stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);
                        }
                    } else {
                        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                        indexImgList = objectMapper.readValue(s, javaType);
                    }
                }
            }
        } catch (Exception e) {
        }

        if (indexImgList != null) {
            return new ResultVO(ResStatus.OK, "success", indexImgList);
        }

        return new ResultVO(ResStatus.NO, "fail", null);
    }
}
