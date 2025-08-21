package io.irwansyahdev96.readcollection.dto.book;

import java.math.BigDecimal;

import io.irwansyahdev96.readcollection.model.BookType;

public class BookSingleResDto extends BookListResDataDto{
    
    private String synopsis;
    private String author;
    private String publisher;
    private BigDecimal price;

    private BookType bookType;

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    
}
