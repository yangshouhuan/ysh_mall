package com.qfedu.fmmall;

import com.qfedu.fmmall.dao.CategoryMapper;
import com.qfedu.fmmall.dao.ProductMapper;
import com.qfedu.fmmall.entity.CategoryVO;
import com.qfedu.fmmall.entity.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ImportProductInfoIntoES {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;


    @Test
    public void testImportData() throws IOException {

        List<CategoryVO> categoryVOList = categoryMapper.selectAllCategories2(0);

        for (CategoryVO c1 : categoryVOList) {
            System.out.println(c1);
            for (CategoryVO c2 : categoryVOList) {
                System.out.println("\t" + c2);
                for (CategoryVO c3 : categoryVOList) {
                    System.out.println("\t" + c3);
                }
            }
        }
    }


    @Test
    public void testImportData2() throws IOException {

        List<ProductVO> productVOS = productMapper.selectRecommendProducts();

        for (ProductVO p1 : productVOS) {
            System.out.println(p1);
        }
    }

    @Test
    public void testImportData3() throws IOException {

        List<CategoryVO> categoryVOList = categoryMapper.selectFirstLevelCategories();
        for (CategoryVO c: categoryVOList) {
            System.out.println(c);
        }
    }
}
