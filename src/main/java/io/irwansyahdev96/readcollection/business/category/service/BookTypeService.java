package io.irwansyahdev96.readcollection.business.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.irwansyahdev96.readcollection.business.book.dao.BookDao;
import io.irwansyahdev96.readcollection.business.category.dao.BookTypeDao;
import io.irwansyahdev96.readcollection.constant.Message;
import io.irwansyahdev96.readcollection.dto.BaseInsertResDto;
import io.irwansyahdev96.readcollection.dto.BaseResListDto;
import io.irwansyahdev96.readcollection.dto.BaseResSingleDto;
import io.irwansyahdev96.readcollection.dto.BaseUpdateAndDeleteResDto;
import io.irwansyahdev96.readcollection.dto.booktype.BookTypeDeleteReqDto;
import io.irwansyahdev96.readcollection.dto.booktype.BookTypeInsertReqDto;
import io.irwansyahdev96.readcollection.dto.booktype.BookTypeResDataDto;
import io.irwansyahdev96.readcollection.dto.booktype.BookTypeUpdateReqDto;
import io.irwansyahdev96.readcollection.model.Book;
import io.irwansyahdev96.readcollection.model.BookType;

@Service
public class BookTypeService {
    
    @Autowired
    private BookTypeDao bookTypeDao;

    @Autowired
    private BookDao bookDao;

    // @Autowired
    // private ReadBookDao readBookDao;

    @Transactional(rollbackOn = Exception.class)
    public BaseInsertResDto save(BookTypeInsertReqDto bookTypeReqDto){
        BaseInsertResDto baseInsertResDto = new BaseInsertResDto();

        BookType bookType = new BookType();
        
        bookType.setBookTypeCode(bookTypeReqDto.getBookTypeCode());
        bookType.setBookTypeName(bookTypeReqDto.getBookTypeName());

        BookType bookTypeInsert = bookTypeDao.save(bookType);

        if(bookTypeInsert != null){
            baseInsertResDto.setId(bookTypeInsert.getId());
            baseInsertResDto.setMessage(Message.SUCCESS_SAVE.getMessage());
        }else{
            baseInsertResDto.setMessage(Message.FAILED_SAVE.getMessage());
        } 

        return baseInsertResDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public BaseUpdateAndDeleteResDto update(BookTypeUpdateReqDto bookTypeUpdateReqDto){
        BaseUpdateAndDeleteResDto baseUpdateResDto = new BaseUpdateAndDeleteResDto();

        BookType bookType = bookTypeDao.findById(BookType.class, bookTypeUpdateReqDto.getId());

        if(bookType != null){

            bookType.setBookTypeName(bookTypeUpdateReqDto.getBookTypeName());

            BookType bookTypeUpdate = bookTypeDao.update(bookType);

            if(bookTypeUpdate != null){
                baseUpdateResDto.setMessage(Message.SUCCESS_UPDATE.getMessage());
            }else{
                baseUpdateResDto.setMessage(Message.FAILED_UPDATE.getMessage());
            }
        }else{
            baseUpdateResDto.setMessage(Message.FAILED_UPDATE.getMessage());
        }

        return baseUpdateResDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public BaseUpdateAndDeleteResDto delete(BookTypeDeleteReqDto bookTypeDeleteReqDto){
        BaseUpdateAndDeleteResDto baseDeleteResDto = new BaseUpdateAndDeleteResDto();
        BookType bookType = bookTypeDao.findById(BookType.class, bookTypeDeleteReqDto.getId());

        if(bookType != null){

            List<Book> books = bookDao.getByBookTypeId(bookTypeDeleteReqDto.getId());

            // delete read book, book and book type
            // books.forEach(book ->{
            //     readBookDao.delete("tb_read_book", "book_id", book.getId());
            // });

            bookDao.delete("tb_book", "book_type_id", bookTypeDeleteReqDto.getId());

            Boolean isDeleteBookType = bookTypeDao.delete("tb_book_type", "id", bookTypeDeleteReqDto.getId());

            if(isDeleteBookType){
                baseDeleteResDto.setMessage(Message.SUCCESS_DELETE.getMessage()+"(data relation a book, deleted)");    
            }
            
            
            
        }else{
            baseDeleteResDto.setMessage(Message.FAILED_DELETE.getMessage());
        }

        return baseDeleteResDto;
    }

    public BaseResListDto<BookTypeResDataDto> getAll(){
        BaseResListDto<BookTypeResDataDto> baseResListDto = new BaseResListDto<>();

        String tableName = "tb_book_type";

        List<BookType> bookTypes = bookTypeDao.getAll(tableName, BookType.class);
        Integer countOfBookType = bookTypeDao.getCountOfData(tableName);

        List<BookTypeResDataDto> bookTypeResDataDtos = new ArrayList<>();
        bookTypes.forEach(bookType->{
            BookTypeResDataDto bookTypeResDataDto = new BookTypeResDataDto();

            bookTypeResDataDto.setId(bookType.getId());
            bookTypeResDataDto.setBookTypeCode(bookType.getBookTypeCode());
            bookTypeResDataDto.setBookTypeName(bookType.getBookTypeName());
            
            List<Book> books = bookDao.getByBookTypeId(bookType.getId());

            bookTypeResDataDto.setBooks(books);

            bookTypeResDataDtos.add(bookTypeResDataDto);
        });

        baseResListDto.setData(bookTypeResDataDtos);
        baseResListDto.setCountOfData(countOfBookType);

        return baseResListDto;
    }

    public BaseResListDto<BookTypeResDataDto> getAll(Object search){
        BaseResListDto<BookTypeResDataDto> baseResListDto = new BaseResListDto<>();

        String tableName = "tb_book_type";

        List<BookType> bookTypes = bookTypeDao.getAll(tableName, BookType.class);

        bookTypes =  bookTypes.stream()
        .filter(bookType-> bookType.getBookTypeCode().equals(search) 
                || bookType.getBookTypeName().equals(search))
        .collect(Collectors.toList());

        List<BookTypeResDataDto> bookTypeResDataDtos = new ArrayList<>();
        bookTypes.forEach(bookType->{
            BookTypeResDataDto bookTypeResDataDto = new BookTypeResDataDto();

            bookTypeResDataDto.setId(bookType.getId());
            bookTypeResDataDto.setBookTypeCode(bookType.getBookTypeCode());
            bookTypeResDataDto.setBookTypeName(bookType.getBookTypeName());
            
            List<Book> books = bookDao.getByBookTypeId(bookType.getId());

            bookTypeResDataDto.setBooks(books);

            bookTypeResDataDtos.add(bookTypeResDataDto);
        });

        baseResListDto.setData(bookTypeResDataDtos);
        baseResListDto.setCountOfData(bookTypes.size());

        return baseResListDto;
    }

    public BaseResSingleDto<BookTypeResDataDto> getById(String id){
        BaseResSingleDto<BookTypeResDataDto> baseResSingleDto = new BaseResSingleDto<>();

        BookType bookType = bookTypeDao.findById(BookType.class, id);

        if(bookType != null){
            BookTypeResDataDto bookTypeResDataDto = new BookTypeResDataDto();
            bookTypeResDataDto.setId(bookType.getId());
            bookTypeResDataDto.setId(bookType.getId());
            bookTypeResDataDto.setBookTypeCode(bookType.getBookTypeCode());
            bookTypeResDataDto.setBookTypeName(bookType.getBookTypeName());
            
            List<Book> books = bookDao.getByBookTypeId(bookType.getId());

            bookTypeResDataDto.setBooks(books);

            baseResSingleDto.setData(bookTypeResDataDto);
        }

        return baseResSingleDto;
    }

    public BaseResSingleDto<BookType> getByBookTypeCode(String bookTypeCode){
        BaseResSingleDto<BookType> baseResSingleDto = new BaseResSingleDto<>();

        BookType BookType = bookTypeDao.findByBookTypeCode(bookTypeCode);

        baseResSingleDto.setData(BookType);

        return baseResSingleDto;
    }
}
