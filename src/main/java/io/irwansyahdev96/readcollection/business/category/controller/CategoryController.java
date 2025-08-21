package io.irwansyahdev96.readcollection.business.category.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryDeleteReqDto;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryInsertReqDto;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryUpdateReqDto;
import io.irwansyahdev96.readcollection.business.category.model.Category;
import io.irwansyahdev96.readcollection.business.category.service.CategoryService;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;


@RestController
@RequestMapping("categories")
public class CategoryController {
    
    @Autowired
    private CategoryService bookTypeService;

    @GetMapping
    public ResponseEntity<BaseResListDto<?>> getAll(){
        BaseResListDto<Category> all = bookTypeService.getAll();

        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseTransactionResDto> save(@Valid @RequestBody CategoryInsertReqDto bookTypeInsertReqDto){
        BaseTransactionResDto baseInsertResDto = bookTypeService.save(bookTypeInsertReqDto);

        return new ResponseEntity<>(baseInsertResDto, HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<BaseTransactionResDto> update(@Valid @RequestBody CategoryUpdateReqDto bookTypeUpdateReqDto){
        BaseTransactionResDto baseUpdateResDto = bookTypeService.update(bookTypeUpdateReqDto);

        return new ResponseEntity<>(baseUpdateResDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseTransactionResDto> delete(@RequestBody CategoryDeleteReqDto bookTypeDeleteReqDto){
        BaseTransactionResDto BaseTransactionResDto = bookTypeService.delete(bookTypeDeleteReqDto);

        return new ResponseEntity<>(BaseTransactionResDto, HttpStatus.OK);
    }

}
