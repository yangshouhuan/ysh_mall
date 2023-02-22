package com.qfedu.fmmall.service;

import com.qfedu.fmmall.entity.ProductComments;
import com.qfedu.fmmall.entity.ProductCommentsVO;
import com.qfedu.fmmall.vo.ResultVO;

import java.util.List;

public interface ProductCommentsService {

    public ResultVO listCommentsByProductId(String productId, int pageNum, int limit);

    /**
     * 根据当前商品ID统计当前商品的评价信息
     * @param productId
     * @return
     */
    public ResultVO getCommentsCountByProductId(String productId);

}
