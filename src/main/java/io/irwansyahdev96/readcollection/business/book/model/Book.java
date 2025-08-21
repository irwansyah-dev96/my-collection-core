package io.irwansyahdev96.readcollection.business.book.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.irwansyahdev96.readcollection.business.status.model.Status;

@Entity
@Table(name = "tb_book")
public class Book {
    
    @Id
    @Column(name = "issbn",nullable = false,length = 15)
    private String issbn;

    @Column(name = "title",nullable = false,length = 50)
    private String title;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "number_of_page",nullable = false,length = 5)
    private Integer numberOfPage;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "publisher",nullable = false,length = 20)
    private String publisher;

    @Column(name = "author_name",nullable = false,length = 20)
    private String authorName;

    @Column(name="release_date")
    private Long releaseDate;

    @ManyToOne
    @JoinColumn(name = "status_code",columnDefinition = "VARCHAR(36)")
    private Status status;


    public String getIssbn() {
        return issbn;
    }

    public void setIssbn(String issbn) {
        this.issbn = issbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(Integer numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Long releaseDate) {
        this.releaseDate = releaseDate;
    }

    

}
