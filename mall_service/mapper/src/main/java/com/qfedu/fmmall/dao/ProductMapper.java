package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.Product;
import com.qfedu.fmmall.entity.ProductVO;
import com.qfedu.fmmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDAO<Product> {

    public List<ProductVO> selectRecommendProducts();

    // 查询指定一级类别下销量最高的商品
    public List<ProductVO> selectTop6ByCategory(int cid);

    /**
     * 根据三级分类id查询商品信息
     * @param cid
     * @param start
     * @param limit
     * @return
     */
    public List<ProductVO> selectProductByCategoryId(@Param("cid") int cid,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);

    // 返回类别id当前类别中包含的品牌
    public List<String> selectBrandByCategoryId(int cid);

    // 根据关键字模糊查询
    public List<ProductVO> selectProductByKeyword(@Param("keyword") String keyword,
                                                     @Param("start") int start,
                                                     @Param("limit") int limit);

    // 返回关键字返回当前类别中包含的品牌
    public List<String> selectBrandByKeyword(String keyword);
}