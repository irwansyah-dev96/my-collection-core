package io.irwansyahdev96.readcollection.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_book_type")
public class BookType {
    
    @Id
    @GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36,nullable = false)
    private String id;

    @Column(name = "book_type_code",length = 5,nullable = false, unique = true)
    private String bookTypeCode;

    @Column(name = "book_type_name",length = 20,nullable = false)
    private String bookTypeName;

    // @Transient
    // @OneToMany(mappedBy = "tb_book_type")
    // private List<Book> books = new ArrayList<>();


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


    // public List<Book> getBooks() {
    //     return books;
    // }

    // public void setBooks(List<Book> books) {
    //     this.books = books;
    // }

    
}
