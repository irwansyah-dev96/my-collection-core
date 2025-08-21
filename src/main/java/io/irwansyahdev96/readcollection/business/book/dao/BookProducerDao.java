package io.irwansyahdev96.readcollection.business.book.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import io.irwansyahdev96.readcollection.base.dao.BaseDao;
import io.irwansyahdev96.readcollection.business.book.model.Book;
import io.irwansyahdev96.readcollection.util.DatetimeUtil;

@Repository
public class BookProducerDao extends BaseDao{
    
 
    public List<Map<String, Object>> findAll(Integer page, Integer data){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tb.issbn, tb.title, tb.description, ")
        .append("CONCAT(tb.publisher,'-', tb.author_name) AS publisher, ")
        .append("tbc.categories, ts.status_code, ts.status_name ")
        .append("FROM tb_book tb ")
        .append("INNER JOIN tb_status ts on ts.status_code = tb.status_code ")
        .append("LEFT JOIN ( ")
            .append("SELECT issbn, GROUP_CONCAT(category_name ORDER BY category_name SEPARATOR ',') AS categories ")
            .append("FROM ( ")
                .append("SELECT tb.issbn, category_name ")
                .append("FROM tb_book tb ")
                .append("RIGHT JOIN tb_category_book tbd ON tb.issbn = tbd.issbn ")
                .append("INNER JOIN tb_category tbt ON tbt.category_code = tbd.category_code ")
            .append(") tbg GROUP BY issbn ")
        .append(") tbc ON tb.issbn = tbc.issbn ");

        List<Map<String, Object>> result = new LinkedList<>();
        try {
            List<?> resultList = getEM()
                .createNativeQuery(sql.toString())
                .setFirstResult((page - 1) * data)   // offset
                .setMaxResults(data)
                .getResultList();

            for (Object obj : resultList) {
                Object[] o = (Object[]) obj;
                
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("issbn", o[0]);
                map.put("title", o[1]);
                map.put("description", o[2]);
                map.put("publisher", o[3]);
                map.put("bookTypes", o[4]);
                map.put("statusCode", o[5]);
                map.put("statusName", o[6]);
                result.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public List<Map<String, Object>> findAll(Integer page, Integer data, String search, String status){
        StringBuilder sql = new StringBuilder();

        StringBuilder conditionSql = new StringBuilder();
        
        if(!search.isEmpty())
            conditionSql.append("AND (tb.title LIKE CONCAT(:search,'%') ").append("OR tb.issbn LIKE CONCAT(:search,'%')) ");
        

        if (!status.isEmpty())
            conditionSql.append("AND tb.status_code = :status ");
        
        sql.append("SELECT tb.issbn, tb.title, tb.description, ")
        .append("CONCAT(tb.publisher,'-', tb.author_name) AS publisher, ")
        .append("tbc.categories, ts.status_code, ts.status_name ")
        .append("FROM tb_book tb ")
        .append("INNER JOIN tb_status ts on ts.status_code = tb.status_code ")
        .append("LEFT JOIN ( ")
            .append("SELECT issbn, GROUP_CONCAT(category_name ORDER BY category_name SEPARATOR ', ') AS categories ")
            .append("FROM ( ")
                .append("SELECT tb.issbn, category_name ")
                .append("FROM tb_book tb ")
                .append("RIGHT JOIN tb_category_book tbd ON tb.issbn = tbd.issbn ")
                .append("INNER JOIN tb_category tbt ON tbt.category_code = tbd.category_code ")
            .append(") tbg GROUP BY issbn ")
        .append(") tbc ON tb.issbn = tbc.issbn ")
        .append("WHERE 1=1 ")
        .append(conditionSql.toString());
        
        List<Map<String, Object>> result = new LinkedList<>();
        try {
            Query setParameter = getEM()
                            .createNativeQuery(sql.toString())
                            .setFirstResult((page - 1) * data)   // offset
                            .setMaxResults(data);

            if(!search.isEmpty())
                setParameter.setParameter("search", search);
            

            if (!status.isEmpty())
                setParameter.setParameter("status", status);

            List<?> resultList = setParameter
                .getResultList();

            for (Object obj : resultList) {
                Object[] o = (Object[]) obj;
                
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("issbn", o[0]);
                map.put("title", o[1]);
                map.put("description", o[2]);
                map.put("publisher", o[3]);
                map.put("bookTypes", o[4]);
                map.put("statusCode", o[5]);
                map.put("statusName", o[6]);
                result.add(map);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    
    public Integer countOfReadBook(String search, String status){
        Map<String, Object> filter = new LinkedHashMap<>();

        StringBuilder conditionSql = new StringBuilder();

        if(!search.isEmpty()){
            conditionSql.append("AND (title LIKE CONCAT(:search,'%') ").append("OR issbn LIKE CONCAT(:search,'%')) ");
            filter.put("search", search);
        }

        if (!status.isEmpty()){
            conditionSql.append("AND status_code = :status ");
            filter.put("status", status);
        }

        return count(Book.class, conditionSql.toString(), filter); // note data
    }

    public Map<String, Object> findByIssbn(String issbn){

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tb.issbn, tb.title, ts.status_name, tbcg.categorys, tb.number_of_page, tb.author_name, tb.publisher, tb.release_date, ts.status_code ")
        .append("FROM tb_book tb ")
        .append("INNER JOIN tb_status ts ON tb.status_code = ts.status_code ")
        .append("LEFT JOIN ( ")
            .append("SELECT issbn, GROUP_CONCAT(category_name ORDER BY category_name SEPARATOR ', ') AS categorys ")
            .append("FROM ( ")
                .append("SELECT tb.issbn, category_name ")
                .append("FROM tb_book tb ")
                .append("RIGHT JOIN tb_category_book tbd ON tb.issbn = tbd.issbn ")
                .append("INNER JOIN tb_category tbt ON tbt.category_code = tbd.category_code ")
            .append(") tbc GROUP BY issbn ")
        .append(") tbcg ON tb.issbn = tbcg.issbn ")
        .append("WHERE tb.issbn=:issbn ");

        Map<String, Object> result = new LinkedHashMap<>();
        try {
            Object singleResult = getEM()
                .createNativeQuery(sql.toString())
                .setParameter("issbn", issbn)
                .getSingleResult();;

            
            Object[] o = (Object[]) singleResult;
            
            result.put("issbn", o[0]);
            result.put("title", o[1]);
            result.put("statusName", o[2]);
            result.put("category", o[3]);
            result.put("page", o[4]);
            result.put("authorName", o[5]);
            result.put("publisher", o[6]);
            result.put("releaseDate", DatetimeUtil.epochMillisToDate(((BigInteger) o[7]).longValue()));
            result.put("statusCode", o[8]);

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
