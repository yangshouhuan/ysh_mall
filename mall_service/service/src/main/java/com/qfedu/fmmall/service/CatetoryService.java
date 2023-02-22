package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;
import org.springframework.stereotype.Repository;

public interface CatetoryService {

    public ResultVO listCategories();

    public ResultVO listFirstLevelCategories();

}
