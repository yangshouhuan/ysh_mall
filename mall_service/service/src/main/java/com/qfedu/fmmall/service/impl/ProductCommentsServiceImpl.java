package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.ProductCommentsMapper;
import com.qfedu.fmmall.entity.ProductComments;
import com.qfedu.fmmall.entity.ProductCommentsVO;
import com.qfedu.fmmall.service.ProductCommentsService;
import com.qfedu.fmmall.utils.PageHelper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {

    @Autowired
    private ProductCommentsMapper productCommentsMapper;


    @Override
    public ResultVO listCommentsByProductId(String productId, int pageNum, int limit) {

        /*List<ProductCommentsVO> productCommentsList = productCommentsMapper.selectCommentsByProductId(productId);
        return new ResultVO(ResStatus.OK, "success", productCommentsList);*/

        // 根据商品id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);

        int count = productCommentsMapper.selectCountByExample(example);
        int pageCount = (count % limit == 0) ? (count / limit) : (count / limit) + 1;  // 总页数
        int start = (pageNum - 1) * limit;  // 开始

        // 评论中需要用户信息，所有需要连表查询
        List<ProductCommentsVO> list = productCommentsMapper.selectCommentsByProductId(productId, start, limit);

        ResultVO resultVO = new ResultVO(ResStatus.OK, "success", new PageHelper<ProductCommentsVO>(count, pageCount, list));

        return resultVO;
    }

    @Override
    public ResultVO getCommentsCountByProductId(String productId) {
        // 查询商品评价总数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        int total = productCommentsMapper.selectCountByExample(example);

        // 查询好评总数
        criteria.andEqualTo("commType", 1);
        int goodTotal = productCommentsMapper.selectCountByExample(example);

        // 查询好评总数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("productId",productId);
        criteria1.andEqualTo("commType", 0);
        int midTotal = productCommentsMapper.selectCountByExample(example1);

        // 查询好评总数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andEqualTo("productId",productId);
        criteria2.andEqualTo("commType", -1);
        int badTotal = productCommentsMapper.selectCountByExample(example2);

        double percent = Double.parseDouble(goodTotal + "") / Double.parseDouble(total + "") * 100;
        String percentValue = (percent + "").substring(0, (percent + "").lastIndexOf(".") + 3);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("goodTotal", goodTotal);
        map.put("midTotal", midTotal);
        map.put("badTotal", badTotal);
        map.put("percent", percentValue);

        return new ResultVO(ResStatus.OK, "success", map);
    }
}
