package io.irwansyahdev96.readcollection.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_read_book")
public class ReadBook {
    
    @Id
    @GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36,nullable = false)
    private String id;

    @Column(name = "page_of_read",length=5)
    private Integer pageOfRead;

    @Column(name = "date_of_read",nullable = false)
    private LocalDateTime dateOfRead;

    @ManyToOne
    @JoinColumn(name = "book_id",columnDefinition = "VARCHAR(36)")
    private Book book;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageOfRead() {
        return pageOfRead;
    }

    public void setPageOfRead(Integer pageOfRead) {
        this.pageOfRead = pageOfRead;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getDateOfRead() {
        return dateOfRead;
    }

    public void setDateOfRead(LocalDateTime dateOfRead) {
        this.dateOfRead = dateOfRead;
    }

}
