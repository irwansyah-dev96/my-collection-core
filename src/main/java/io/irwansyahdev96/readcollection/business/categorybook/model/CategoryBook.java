package io.irwansyahdev96.readcollection.business.categorybook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.irwansyahdev96.readcollection.business.book.model.Book;
import io.irwansyahdev96.readcollection.business.category.model.Category;

@Entity
@Table(name = "tb_category_book")
public class CategoryBook {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "issbn",columnDefinition = "VARCHAR(15)")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "category_code")
    private Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    
}
