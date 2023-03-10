package com.qfedu.fmmall.dao;

import com.qfedu.fmmall.entity.IndexImg;
import com.qfedu.fmmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexImgMapper extends GeneralDAO<IndexImg> {

    // 查询轮播图信息, 查询status=1，且按照seq进行排序
    public List<IndexImg> listIndexImgs();

}