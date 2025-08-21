package io.irwansyahdev96.readcollection.dto.book;

import java.util.List;

import io.irwansyahdev96.readcollection.model.ReadBook;
import io.irwansyahdev96.readcollection.model.Status;


public class BookListResDataDto {
 
    private String id;
    private String title;
    private Integer numberOfPage;
    private Status status;

    private List<ReadBook> ReadBooks;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getNumberOfPage() {
        return numberOfPage;
    }
    public void setNumberOfPage(Integer numberOfPage) {
        this.numberOfPage = numberOfPage;
    }
    
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public List<ReadBook> getReadBooks() {
        return ReadBooks;
    }
    public void setReadBooks(List<ReadBook> readBooks) {
        ReadBooks = readBooks;
    }
}
