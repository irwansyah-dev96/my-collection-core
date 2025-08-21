package io.irwansyahdev96.readcollection.business.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.irwansyahdev96.readcollection.business.book.dao.BookDao;
import io.irwansyahdev96.readcollection.business.category.dao.BookTypeDao;
import io.irwansyahdev96.readcollection.business.status.dao.StatusDao;
import io.irwansyahdev96.readcollection.constant.Message;
import io.irwansyahdev96.readcollection.dto.BaseInsertResDto;
import io.irwansyahdev96.readcollection.dto.BaseResListDto;
import io.irwansyahdev96.readcollection.dto.BaseResSingleDto;
import io.irwansyahdev96.readcollection.dto.BaseUpdateAndDeleteResDto;
import io.irwansyahdev96.readcollection.dto.book.BookDeleteReqDto;
import io.irwansyahdev96.readcollection.dto.book.BookInsertReqDto;
import io.irwansyahdev96.readcollection.dto.book.BookListResDataDto;
import io.irwansyahdev96.readcollection.dto.book.BookSingleResDto;
import io.irwansyahdev96.readcollection.dto.book.BookUpdateReqDto;
import io.irwansyahdev96.readcollection.dto.book.BookUpdateStatusReqDto;
import io.irwansyahdev96.readcollection.model.Book;
import io.irwansyahdev96.readcollection.model.BookType;
import io.irwansyahdev96.readcollection.model.Status;

@Service
public class BookService {
    
    @Autowired
    private StatusDao statusDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookTypeDao bookTypeDao;

    // @Autowired
    // private ReadBookDao readBookDao;

    @Transactional(rollbackOn = Exception.class)
    public BaseInsertResDto save(BookInsertReqDto bookInsertReqDto){
        BaseInsertResDto baseInsertResDto = new BaseInsertResDto();

        BookType bookType = bookTypeDao.findByBookTypeCode(bookInsertReqDto.getBookType().getBookTypeCode());

        if(bookType != null){
            Book book = new Book();
            Status status = statusDao.getByStatusCode("N");
        
            book.setIssbn(bookInsertReqDto.getIssbn());
            book.setTitle(bookInsertReqDto.getTitle());
            book.setNumberOfPage(bookInsertReqDto.getNumberOfPage());
    
            book.setSynopsis(bookInsertReqDto.getSynopsis());
            book.setPrice(bookInsertReqDto.getPrice());
            book.setStatus(status);
            book.setPublisher(bookInsertReqDto.getPublisher());
            book.setAuthorName(bookInsertReqDto.getAuthorName());

            book.setBookType(bookType);

            Book bookInsert = bookDao.save(book);

            if(bookInsert != null){
                baseInsertResDto.setId(bookInsert.getId());
                baseInsertResDto.setMessage(Message.SUCCESS_SAVE.getMessage());
            }else{
                baseInsertResDto.setMessage(Message.FAILED_SAVE.getMessage());
            }
        }else{
            baseInsertResDto.setMessage(Message.FAILED_SAVE.getMessage()+", Book Type isnt available");
        }

        return baseInsertResDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public BaseUpdateAndDeleteResDto update(BookUpdateReqDto bookUpdateReqDto){
        BaseUpdateAndDeleteResDto baseUpdateResDto = new BaseUpdateAndDeleteResDto();

        Book book = bookDao.findById(Book.class, bookUpdateReqDto.getId());

        if(book != null){

            if(bookUpdateReqDto.getTitle() != null){
                book.setTitle(bookUpdateReqDto.getTitle());
            }

            if(bookUpdateReqDto.getNumberOfPage() != null){
                book.setNumberOfPage(bookUpdateReqDto.getNumberOfPage());
            }

            if(bookUpdateReqDto.getNumberOfPage() != null){
                book.setSynopsis(bookUpdateReqDto.getSynopsis());
            }

            if(bookUpdateReqDto.getPrice() != null){
                book.setPrice(bookUpdateReqDto.getPrice());
            }

            if(bookUpdateReqDto.getPublisher() != null){
                book.setPublisher(bookUpdateReqDto.getPublisher());
            }

            if(bookUpdateReqDto.getAuthorName() != null){
                book.setAuthorName(bookUpdateReqDto.getAuthorName());
            }

            Book bookUpdate = bookDao.update(book);

            if(bookUpdate != null){   
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
    public BaseUpdateAndDeleteResDto updateStatus(BookUpdateStatusReqDto bookUpdateStatusReqDto){
        BaseUpdateAndDeleteResDto baseUpdateResDto = new BaseUpdateAndDeleteResDto();

        Book book = bookDao.findById(Book.class, bookUpdateStatusReqDto.getId());

        Status status = statusDao.getByStatusCode(bookUpdateStatusReqDto.getStatusCode());

        if(book != null){

            book.setStatus(status);

            Book bookUpdate = bookDao.update(book);

            if(bookUpdate != null){
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
    public BaseUpdateAndDeleteResDto delete(BookDeleteReqDto bookDeleteReqDto){
        BaseUpdateAndDeleteResDto baseDeleteResDto = new BaseUpdateAndDeleteResDto();

        BookType book = bookDao.findById(BookType.class, bookDeleteReqDto.getId());

        if(book != null){

            // delete book
            bookTypeDao.delete("tb_read_book", "book_id", bookDeleteReqDto.getId());
            
            Boolean isDeleteBookType = bookTypeDao.delete("tb_book", "id", bookDeleteReqDto.getId());

            if(isDeleteBookType){
                baseDeleteResDto.setMessage(Message.SUCCESS_UPDATE.getMessage()+"(data relation a book, deleted)");    
            }
        }else{
            baseDeleteResDto.setMessage(Message.FAILED_UPDATE.getMessage());
        }

        return baseDeleteResDto;
    }

    public BaseResListDto<BookListResDataDto> getAll(){
        BaseResListDto<BookListResDataDto> baseResListDto = new BaseResListDto<>();

        String tableName = "tb_book";

        List<Book> books = bookDao.getAll(tableName, Book.class);
        Integer countOfBook = bookDao.getCountOfData(tableName);

        List<BookListResDataDto> bookResDataDtos = new ArrayList<>();

        books.forEach(book->{
            BookListResDataDto bookResDataDto = new BookListResDataDto();

            bookResDataDto.setId(book.getId());
            bookResDataDto.setTitle(book.getTitle());
            bookResDataDto.setStatus(book.getStatus());
            bookResDataDto.setNumberOfPage(book.getNumberOfPage());
            
            // List<ReadBook> readBooks = readBookDao.getByBookId(book.getId());
            // bookResDataDto.setReadBooks(readBooks);

            bookResDataDtos.add(bookResDataDto);

        });

        baseResListDto.setData(bookResDataDtos);
        baseResListDto.setCountOfData(countOfBook);

        return baseResListDto;
    }

    public BaseResListDto<BookListResDataDto> getAll(Object search){
        BaseResListDto<BookListResDataDto> baseResListDto = new BaseResListDto<>();

        String tableName = "tb_book";

        List<Book> books = bookDao.getAll(tableName, Book.class);

        books = books.stream()
                .filter(book -> book.getIssbn().equals(search) 
                || book.getTitle().equals(search)
                || book.getPublisher().equals(search)
                || book.getAuthorName().equals(search)
                || book.getIssbn().equals(search))
                .collect(Collectors.toList());

        List<BookListResDataDto> bookResDataDtos = new ArrayList<>();
        books.forEach(book->{
            BookListResDataDto bookResDataDto = new BookListResDataDto();

            bookResDataDto.setId(book.getId());
            bookResDataDto.setTitle(book.getTitle());
            bookResDataDto.setStatus(book.getStatus());
            bookResDataDto.setNumberOfPage(book.getNumberOfPage());
            
            // List<ReadBook> readBooks = readBookDao.getByBookId(book.getId());
            // bookResDataDto.setReadBooks(readBooks);

            bookResDataDtos.add(bookResDataDto);

        });

        baseResListDto.setData(bookResDataDtos);
        baseResListDto.setCountOfData(books.size());

        return baseResListDto;
    }

    public BaseResSingleDto<BookSingleResDto> getById(String id){
        BaseResSingleDto<BookSingleResDto> baseResSingleDto = new BaseResSingleDto<>();

        Book book = bookDao.findById(Book.class, id);

        if(book != null){
            BookSingleResDto bookSingleResDataDto = new BookSingleResDto();


            bookSingleResDataDto.setId(book.getId());
            bookSingleResDataDto.setTitle(book.getTitle());
            bookSingleResDataDto.setStatus(book.getStatus());
            bookSingleResDataDto.setNumberOfPage(book.getNumberOfPage());
            
            bookSingleResDataDto.setSynopsis(book.getSynopsis());
            bookSingleResDataDto.setAuthor(id);
            bookSingleResDataDto.setPublisher(book.getPublisher());
            bookSingleResDataDto.setBookType(book.getBookType());
            bookSingleResDataDto.setPrice(book.getPrice());

            // List<ReadBook> readBooks = readBookDao.getByBookId(book.getId());
            // bookSingleResDataDto.setReadBooks(readBooks);
            
            baseResSingleDto.setData(bookSingleResDataDto);
        }

        return baseResSingleDto;
    }
    
}
