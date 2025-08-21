package io.irwansyahdev96.readcollection.business.book.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResSingleDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;
import io.irwansyahdev96.readcollection.business.book.dto.BookUpdateStatusReqDto;
import io.irwansyahdev96.readcollection.business.book.service.BookProducerService;

@RestController
@RequestMapping("books/produce")
public class BookProducerController {
    
    @Autowired
    private BookProducerService bookProducerService;

    // producer
    @PutMapping("/update-status")
    public ResponseEntity<BaseTransactionResDto> updateStatus(@Valid @RequestBody BookUpdateStatusReqDto bookUpdateStatusReqDto){ 
        BaseTransactionResDto baseUpdateResDto = bookProducerService.updateStatus(bookUpdateStatusReqDto);

        return new ResponseEntity<>(baseUpdateResDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResListDto<?>> getAll(@RequestParam(value="search",required = false,defaultValue = "") String search, 
                                                            @RequestParam(value="status",required = false,defaultValue = "") String status,
                                                             Integer page, Integer limit){
        BaseResListDto<?> baseResListDto = null;

        if(search.isEmpty() && status.isEmpty()){
            baseResListDto = bookProducerService.getAll(page, limit);
        }else{
            baseResListDto = bookProducerService.getAll(page, limit, search, status);
        }
        
        return new ResponseEntity<>(baseResListDto, HttpStatus.OK);
    }

    @GetMapping("/{issbn}/issbn")
    public ResponseEntity<BaseResSingleDto<?>> getById(@PathVariable("issbn") String issbn){
        BaseResSingleDto<?> byIssbn = bookProducerService.getByIssbn(issbn);

        return new ResponseEntity<>(byIssbn, HttpStatus.OK);
    }

}
