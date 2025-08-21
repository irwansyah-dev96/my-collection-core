package io.irwansyahdev96.readcollection.business.book.dto;

import java.math.BigDecimal;
import java.util.Locale.Category;



public class BookSingleResDto{
    
    private String synopsis;
    private String author;
    private String publisher;
    private BigDecimal price;

    private Category bookType;

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

    public Category getBookType() {
        return bookType;
    }

    public void setBookType(Category bookType) {
        this.bookType = bookType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    
}
