package io.irwansyahdev96.readcollection.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_book")
public class Book {
    
    @Id
    @GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36,nullable = false)
    private String id;

    @Column(name = "issbn",nullable = false,length = 15)
    private String issbn;

    @Column(name = "title",nullable = false,length = 50)
    private String title;

    @Column(name = "synopsis",columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "number_of_page",nullable = false,length = 5)
    private Integer numberOfPage;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "status_id",columnDefinition = "VARCHAR(36)")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "book_type_id",columnDefinition = "VARCHAR(36)")
    private BookType bookType;

    @Column(name = "publisher",nullable = false,length = 20)
    private String publisher;

    @Column(name = "author_name",nullable = false,length = 20)
    private String authorName;


    // @Transient
    // @OneToMany
    // private List<ReadBook> readBooks = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
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

    // public List<ReadBook> getReadBooks() {
    //     return readBooks;
    // }

    // public void setReadBooks(List<ReadBook> readBooks) {
    //     this.readBooks = readBooks;
    // }

}
