package io.irwansyahdev96.readcollection.business.book.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.server.ResponseStatusException;

import io.irwansyahdev96.readcollection.base.constant.Message;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResSingleDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;
import io.irwansyahdev96.readcollection.base.dto.validation.ValidationRuntimeException;
import io.irwansyahdev96.readcollection.business.book.dao.BookProducerDao;
import io.irwansyahdev96.readcollection.business.book.dto.BookUpdateStatusReqDto;
import io.irwansyahdev96.readcollection.business.book.model.Book;
import io.irwansyahdev96.readcollection.business.status.model.Status;

@Service
public class BookProducerService {
        
    @Autowired
    private BookProducerDao bookProducerDao;

    /**
     * 
     * validation - request forbidden (400)
     * - Status is must required: v
     * 
     * - Book isn't available: v
     * - request book isn't change: v
     * 
     * @param bookUpdateStatusReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto updateStatus(BookUpdateStatusReqDto bookUpdateStatusReqDto){
        BaseTransactionResDto baseUpdateResDto = new BaseTransactionResDto();

        String statusCode = bookUpdateStatusReqDto.getStatusCode();

        Book book = bookProducerDao.findByUpdate(Book.class, bookUpdateStatusReqDto.getIssbn());

        if(book != null){

            Status status = bookProducerDao.findByPK(Status.class, statusCode); // status

            String statusCodeByBook = book.getStatus().getStatusCode();

            // check status
            if(!statusCodeByBook.equals(statusCode) && status != null){
                book.setStatus(status);
    
                baseUpdateResDto.setMessage(Message.SUCCESS_UPDATE.getMessage());
            }else{

                if(statusCodeByBook.equals(statusCode))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request status book isn't change");
                

                BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(bookUpdateStatusReqDto, "bookUpdateStatusReqDto");
                bindingResult.rejectValue("statusCode", "empty", "Status code isn't available");

                throw new ValidationRuntimeException(bindingResult);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book isn't available");
        }

        return baseUpdateResDto;
    }

    
    public BaseResListDto<Map<String, Object>> getAll(Integer page, Integer limit){
        List<Map<String,Object>> all = bookProducerDao.findAll(page, limit);

        BaseResListDto<Map<String,Object>> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(all);
        baseResListDto.setCountOfData(bookProducerDao.count(Book.class)); // note book
        baseResListDto.setLimit(limit);
        baseResListDto.setPage(page);

        return baseResListDto;
    }

    /**
     * 
     * get all read
     * 
     * validation - request forbidden (400)
     * - Character does not allow: v
     * 
     * @param page
     * @param limit
     * @param search
     * @param status
     * @return
     */
    public BaseResListDto<Map<String, Object>> getAll(Integer page, Integer limit, String search, String status){

        if (!search.isEmpty() && !search.matches("^[a-zA-Z0-9 ]*$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Search contains invalid characters");

        if (!status.isEmpty() && !status.matches("^[a-zA-Z0-9 ]*$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status contains invalid characters");

        List<Map<String,Object>> all = bookProducerDao.findAll(page, limit, search, status);

        BaseResListDto<Map<String,Object>> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(all);
        baseResListDto.setCountOfData(bookProducerDao.countOfReadBook(search, status));
        baseResListDto.setLimit(limit);
        baseResListDto.setPage(page);

        return baseResListDto;
    }

    /**
     * * get by issbn
     * 
     * 
     * @param issbn
     * @return
     */
    public BaseResSingleDto<?> getByIssbn(String issbn){
        Map<String, Object> byIssbn = bookProducerDao.findByIssbn(issbn);

        BaseResSingleDto<Map<String,Object>> baseResSingleRes = new BaseResSingleDto<>();
        baseResSingleRes.setData(byIssbn);

        return baseResSingleRes;
    }

}
