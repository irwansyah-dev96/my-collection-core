package io.irwansyahdev96.readcollection.business.book.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.server.ResponseStatusException;

import io.irwansyahdev96.readcollection.base.constant.Message;
import io.irwansyahdev96.readcollection.base.dto.res.BaseTransactionResDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResSingleDto;
import io.irwansyahdev96.readcollection.base.dto.validation.ValidationRuntimeException;
import io.irwansyahdev96.readcollection.business.book.dao.BookDao;
import io.irwansyahdev96.readcollection.business.book.dto.BookDeleteReqDto;
import io.irwansyahdev96.readcollection.business.book.dto.BookInsertReqDto;
import io.irwansyahdev96.readcollection.business.book.dto.BookUpdateReqDto;
import io.irwansyahdev96.readcollection.business.book.dto.BookUpdateStatusReqDto;
import io.irwansyahdev96.readcollection.business.book.dto.CategoryBookInsertReqDto;
import io.irwansyahdev96.readcollection.business.book.model.Book;
import io.irwansyahdev96.readcollection.business.category.dao.CategoryDao;
import io.irwansyahdev96.readcollection.business.category.dto.CategoryInsertBookReqDto;
import io.irwansyahdev96.readcollection.business.category.model.Category;
import io.irwansyahdev96.readcollection.business.categorybook.dao.CategoryBookDao;
import io.irwansyahdev96.readcollection.business.categorybook.model.CategoryBook;
import io.irwansyahdev96.readcollection.business.httpclient.client.ReadBookClient;
import io.irwansyahdev96.readcollection.business.status.dao.StatusDao;
import io.irwansyahdev96.readcollection.business.status.model.Status;
import io.irwansyahdev96.readcollection.util.DatetimeUtil;


@Service
public class BookService {
    
    @Autowired
    private ReadBookClient readBookClient;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBookDao bookTypeBookDao;

    // @Autowired
    // private ReadBookDao readBookDao;
    
    public BaseResListDto<Map<String, Object>> getAll(Integer page, Integer limit){
        List<Map<String,Object>> all = bookDao.findAll(page, limit);

        BaseResListDto<Map<String,Object>> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(all);
        baseResListDto.setCountOfData(bookDao.count(Book.class));
        baseResListDto.setLimit(limit);
        baseResListDto.setPage(page);

        return baseResListDto;
    }

    
    /**
     * 
     * get all book
     * 
     * validation - request forbidden (400)
     * - Character does not allow: v
     * 
     * @param page
     * @param limit
     * @param search
     * @return
     */
    public BaseResListDto<Map<String, Object>> getAll(Integer page, Integer limit, String search){

        if (!search.isEmpty() && !search.matches("^[a-zA-Z0-9 ]*$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Search contains invalid characters");
        
        List<Map<String,Object>> all = bookDao.findAll(page, limit, search);

        BaseResListDto<Map<String,Object>> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(all);
        baseResListDto.setCountOfData(statusDao.count(Status.class));
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
        Map<String, Object> byIssbn = bookDao.findByIssbn(issbn);

        BaseResSingleDto<Map<String,Object>> baseResSingleRes = new BaseResSingleDto<>();
        baseResSingleRes.setData(byIssbn);

        return baseResSingleRes;
    }

    /**
     * add new book
     * 
     * validation - request forbidden (400)
     * - Issbn is must required: v
     * - Issbn isn't only whitespace: v
     * - Title is must required: v
     * - Title isn't only whitespace: v
     * - Number of page is must required: v
     * 
     * - Book has been available: v
     * - Book Type with code ${code} isn't available: v
     * 
     * @param bookInsertReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto add(BookInsertReqDto bookInsertReqDto){
        BaseTransactionResDto baseInsertResDto = new BaseTransactionResDto();

        String issbn = bookInsertReqDto.getIssbn();
        
        if(bookDao.isExistBook(issbn)){
            BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(bookInsertReqDto, "bookInsertReqDto");
            bindingResult.rejectValue("issbn", "duplicate", "Book already exists");

            throw new ValidationRuntimeException(bindingResult);
        }

        // save book
        Book book = new Book();
        Status status = statusDao.findByPK(Status.class,"N"); // automatic
    
        String dummy = "Anonymous";
        String publisher = bookInsertReqDto.getPublisher();
        String authorName = bookInsertReqDto.getAuthorName();
        LocalDate releaseDate = bookInsertReqDto.getReleaseDate();

        book.setIssbn(issbn);
        book.setTitle(bookInsertReqDto.getTitle());
        book.setNumberOfPage(bookInsertReqDto.getNumberOfPage());
        book.setDescription(bookInsertReqDto.getDescription());
        book.setPrice(bookInsertReqDto.getPrice());
        book.setStatus(status); // first create
        book.setPublisher(publisher.isEmpty() || publisher == null?dummy:publisher); // set dummy if null
        book.setAuthorName(authorName.isEmpty() || authorName == null?dummy:authorName); // set dummy if null
        book.setReleaseDate(releaseDate == null?DatetimeUtil.toMillis(2010, 1, 1):DatetimeUtil.localDateToEpochMilli(releaseDate));  // default 1-1-2010
        Book bookInsert = bookDao.save(book); // save book

        if(bookInsert != null){

            // get book type
            List<CategoryInsertBookReqDto> categories = bookInsertReqDto.getCategories();

            // check req body book type
            if(categories != null){
                int sizeOfCategory = categories.size();
    
                BeanPropertyBindingResult bindingResult =
                    new BeanPropertyBindingResult(bookInsertReqDto, "bookInsertReqDto");
    
                for (int i = 0;i < sizeOfCategory;i++) {
                    
                    String categoryCode = categories.get(i).getCategoryCode();
    
                    // check book type code is not exits
                    if(!categoryDao.isExistByCategoryCode(categoryCode)){
                        bindingResult.rejectValue(String.format("bookTypes[%d].categoryCode", i), "empty", "Category ".concat(categoryCode).concat(" isn't available"));
                        continue;
                    }
    
                    Category bt = categoryDao.findByPK(Category.class, categoryCode);
    
                    // book type and book
                    CategoryBook categoryBook = new CategoryBook();
                    categoryBook.setBook(bookInsert);
                    categoryBook.setCategory(bt);
                    CategoryBook bookTypeBookSave = bookTypeBookDao.save(categoryBook);
    
                    if(bookTypeBookSave == null)
                        throw new RuntimeException("Failed to add book type");
                    
                }
    
                // if check errors
                if(bindingResult.hasErrors())
                    throw new ValidationRuntimeException(bindingResult);
            }

            //
            baseInsertResDto.setId(bookInsert.getIssbn());
            baseInsertResDto.setMessage(Message.SUCCESS_SAVE.getMessage());
        }else{
            throw new RuntimeException("Failed to save book");
        }
        
        return baseInsertResDto;
    }

    /**
     * 
     * validation - request forbidden (400)
     * 
     * - book type code is required: v
     * - issbn is required: v
     
     * - book type has been available: v
     * - book has been available: v
     * 
     * 
     * @param categoryBookInsertReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto addCategory(CategoryBookInsertReqDto categoryBookInsertReqDto){
        BaseTransactionResDto baseInsertResDto = new BaseTransactionResDto();

        String issbn = categoryBookInsertReqDto.getIssbn();
        String bookTypeCode = categoryBookInsertReqDto.getCategoryCode();

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(categoryBookInsertReqDto, "categoryBookInsertReqDto");

        // check duplicate data
        if(!bookDao.isExistBook(issbn))
            bindingResult.rejectValue("issbn", "empty", "Book isn't available");
        

        // check book type code is not exist
        if(!categoryDao.isExistByCategoryCode(bookTypeCode))
            bindingResult.rejectValue("bookTypeCode", "empty", "Book type code already exists");

        
        // if binding has error
        if(bindingResult.hasErrors())
            throw new ValidationRuntimeException(bindingResult);

        // init book and book type
        Book book = bookDao.findByPK(Book.class, issbn);
        Category bookType = categoryDao.findByPK(Category.class, categoryBookInsertReqDto.getCategoryCode());

        if(book != null 
                || bookType != null){

            // set book type and book
            CategoryBook bookTypeBook = new CategoryBook();
            bookTypeBook.setBook(book);
            bookTypeBook.setCategory(bookType);

            CategoryBook bookTypeBookSave = bookTypeBookDao.save(bookTypeBook);

            if(bookTypeBookSave == null){
                throw new RuntimeException("Failed to add book type");
            }else{
                //
                baseInsertResDto.setId(bookTypeBookSave.getId());
                baseInsertResDto.setMessage(Message.SUCCESS_SAVE.getMessage());
            }
        }else{
            baseInsertResDto.setMessage("Failed to add");
        }
        
        return baseInsertResDto;
    }

    /**
     * 
     * validation - request forbidden (400)
     * - Issbn is must required: v
     * - Issbn isn't only whitespace: v
     * - Title is must required: v
     * - Title isn't only whitespace: v
     * - Number of page is must required: v
     * 
     * - Book isn't available: v
     * - request book isn't change: v
     * 
     * @param bookUpdateReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto update(BookUpdateReqDto bookUpdateReqDto){
        BaseTransactionResDto baseUpdateResDto = new BaseTransactionResDto();

        String issbn = bookUpdateReqDto.getIssbn();
        String title = bookUpdateReqDto.getTitle();
        Integer numberOfPage = bookUpdateReqDto.getNumberOfPage();
        String description = bookUpdateReqDto.getDescription();
        BigDecimal price = bookUpdateReqDto.getPrice();
        String publisher = bookUpdateReqDto.getPublisher();
        String authorName = bookUpdateReqDto.getAuthorName();
        LocalDate releaseDate = bookUpdateReqDto.getReleaseDate();

        // check data isnt change
        if(bookDao.isChangeByAllRequest(issbn, title, numberOfPage, description, price, publisher, authorName, releaseDate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request book isn't change");

        Book book = bookDao.findByUpdate(Book.class, issbn);

        if(book != null){

            if(title != null)
                book.setTitle(title);
            
            if(numberOfPage != null)
                book.setNumberOfPage(numberOfPage);
            
            if(description != null)
                book.setDescription(description);
            
            if(price != null)
                book.setPrice(price);
            
            if(publisher != null)
                book.setPublisher(publisher);
            
            if(authorName != null)
                book.setAuthorName(authorName);

            if(releaseDate != null)
                book.setReleaseDate(DatetimeUtil.localDateToEpochMilli(releaseDate));
            
            //
            baseUpdateResDto.setMessage(Message.SUCCESS_UPDATE.getMessage());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book isn't available");
        }

        return baseUpdateResDto;
    }


    /**
     * 
     * validation - request forbidden (400)
     * - book isn't available: v
     * - book still available on read: o
     * @param bookDeleteReqDto
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public BaseTransactionResDto delete(BookDeleteReqDto bookDeleteReqDto){
        BaseTransactionResDto baseDeleteResDto = new BaseTransactionResDto();

        String issbn = bookDeleteReqDto.getIssbn();

        // check read_book
        Boolean isReadBook = readBookClient.isExistReadbook(issbn); // readBookDao.isExistReadBook(issbn)
        if(isReadBook)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book type still available on read");

        // check book isnt exist
        if(!bookDao.isExistBook(issbn))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book type isn't available");

        Book book = bookDao.findByPK(Book.class, issbn);

        if(book != null){

            Boolean isDeleteCategory = false;

            // check and delete book_type_book by issbn
            if(bookDao.isExistOfBookFK(issbn)){
                isDeleteCategory = true;
                categoryDao.delete(CategoryBook.class, "issbn", issbn);
            }

            // delete book by issbn
            Boolean isDeleteBook = categoryDao.delete(Book.class, "issbn", issbn);

            if(isDeleteBook){
                baseDeleteResDto.setMessage(Message.SUCCESS_DELETE.getMessage().concat(isDeleteCategory?" and relation delete":""));    
            }else{
                throw new RuntimeException("book failed delete");      
            }

        }else{
            baseDeleteResDto.setMessage("No book available");
        }

        return baseDeleteResDto;
    }

}
