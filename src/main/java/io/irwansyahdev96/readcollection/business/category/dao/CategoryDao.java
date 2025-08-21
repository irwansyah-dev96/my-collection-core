package io.irwansyahdev96.readcollection.business.category.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Repository;

import io.irwansyahdev96.readcollection.base.dao.BaseDao;
import io.irwansyahdev96.readcollection.business.category.model.Category;



@Repository
public class CategoryDao extends BaseDao{

    @SuppressWarnings("unchecked")
    public List<Category> findAll(){
        String sql = "SELECT * FROM tb_category";

        List<Category> result = new ArrayList<>();

        try {
            result = getEM()
            .createNativeQuery(sql.toString(), Category.class)
            .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Boolean isExistByCategoryCode(String categoryCode){
        return count(Category.class, Map.of("category_code", categoryCode)) > 0;
    }

    public Boolean isChangeByAllRequest(String categoryCode, String categoryName){
        return count(Category.class,
            Map.of(
                "category_code", categoryCode,
                "category_name", categoryName
            )
        ) > 0;
    }

    // book type book mine
    public Boolean isExistOfCategoryFK(String categoryType){
        return count(Category.class,
            Map.of(
                "category_code", categoryType
            )
        ) > 0;
    }
}
