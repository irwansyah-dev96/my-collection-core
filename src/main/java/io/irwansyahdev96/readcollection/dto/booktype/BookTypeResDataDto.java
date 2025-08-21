package io.irwansyahdev96.readcollection.dto.booktype;

import java.util.List;

import io.irwansyahdev96.readcollection.model.Book;

public class BookTypeResDataDto {
    private String id;
    private String bookTypeCode;
    private String bookTypeName;

    private List<Book> books;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }    
    
}
