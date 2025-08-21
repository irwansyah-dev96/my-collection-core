package io.irwansyahdev96.readcollection.business.book.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.irwansyahdev96.readcollection.dao.BaseDao;
import io.irwansyahdev96.readcollection.model.Book;

@Repository
public class BookDao extends BaseDao{
    
    @SuppressWarnings("unchecked")
    public List<Book> getByBookTypeId(String bookTypeId){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM tb_book WHERE book_type_id = :bookTypeId");

        List<Book> lists = getEM().createNativeQuery(sql.toString(),Book.class)
        .setParameter("bookTypeId", bookTypeId)
        .getResultList();

        return lists;
    }
}
