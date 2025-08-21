package io.irwansyahdev96.readcollection.business.category.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.server.ResponseStatusException;

import io.irwansyahdev96.readcollection.base.constant.Message;
import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;
import io.irwansyahdev96.readcollection.base.dto.validation.ValidationRuntimeException;
import io.irwansyahdev96.readcollection.business.category.dao.CategoryDao;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryDeleteReqDto;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryInsertReqDto;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryUpdateReqDto;
import io.irwansyahdev96.readcollection.business.category.model.Category;


@Service
public class CategoryService {
    
    @Autowired
    private CategoryDao categoryDao;
    
    /*
     * get all data book type
     */
    public BaseResListDto<Category> getAll(){
        List<Category> all = categoryDao.findAll();

        BaseResListDto<Category> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(all);
        baseResListDto.setCountOfData(categoryDao.count(Category.class));

        return baseResListDto;
    }

    /**
     * add new book type
     * 
     * validation - request forbidden (400)
     * - book type code is require: v
     * - book type name is require: v
     * - book type name is not blank: v
     
     * - book type code has been available: v
     * 
     * @param categoryReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto save(CategoryInsertReqDto categoryReqDto){
        BaseTransactionResDto baseInsertResDto = new BaseTransactionResDto();


        String categoryCode = categoryReqDto.getCategoryCode();

        // check duplicate data
        if(categoryDao.isExistByCategoryCode(categoryCode)){
            BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(categoryReqDto, "categoryReqDto");
            bindingResult.rejectValue("categoryCode", "duplicate", "Category already exists");

            throw new ValidationRuntimeException(bindingResult);
        }

        Category category = new Category();
        category.setCategoryCode(categoryCode);
        category.setCategoryName(categoryReqDto.getCategoryName());

        Category categoryInsert = categoryDao.save(category);

        // check 
        if(categoryInsert != null){
            baseInsertResDto.setId(categoryInsert.getCategoryCode());
            baseInsertResDto.setMessage(Message.SUCCESS_SAVE.getMessage());
        } else{
            throw new RuntimeException("Failed to save category");
        } 

        return baseInsertResDto;
    }

    /**
     * update book type
     * 
     * validation - request forbidden (400)
     * - book type code is require: v
     * - book type name is require: v
     * - book type name is not blank: v
     *
     * - book type isn't available: v
     * - request book type isn't change: v
     * 
     * @param categoryReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto update(CategoryUpdateReqDto categoryUpdateReqDto){
        BaseTransactionResDto baseUpdateResDto = new BaseTransactionResDto();

        if(categoryDao.isChangeByAllRequest(categoryUpdateReqDto.getCategoryCode(), categoryUpdateReqDto.getCategoryName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request book type isn't change");

        Category category = categoryDao.findByUpdate(Category.class, categoryUpdateReqDto.getCategoryCode());

        if(category != null){

            category.setCategoryName(categoryUpdateReqDto.getCategoryName());
            
            baseUpdateResDto.setMessage(Message.SUCCESS_UPDATE.getMessage());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category isn't available");
        }

        return baseUpdateResDto;
    }

    /**
     * delete book type
     * 
     * validation - request forbidden (400)
     * - book type isn't available: v
     * - book type still available on book: v
     * 
     * @param categoryReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto delete(CategoryDeleteReqDto categoryDeleteReqDto){
        BaseTransactionResDto baseUpdateResDto = new BaseTransactionResDto();

        String categoryCode = categoryDeleteReqDto.getCategoryCode();

        // check data type isn't available
        if(!categoryDao.isExistByCategoryCode(categoryCode))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category isn't available");
        
        // check book
        if(categoryDao.isExistOfCategoryFK(categoryCode))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category still available on book");

        // delete book type
        Boolean isDelete = categoryDao.delete(Category.class, "category_code", categoryCode);
        
        if(isDelete){
            baseUpdateResDto.setMessage(Message.SUCCESS_DELETE.getMessage());
        }else{
            throw new RuntimeException("Failed to delete data category");
        }
        
        return baseUpdateResDto;
    }

}
