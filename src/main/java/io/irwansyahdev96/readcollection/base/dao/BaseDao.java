package io.irwansyahdev96.readcollection.base.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

public abstract class BaseDao {

    @PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEM() {
		return entityManager;
	}
    
    public <T> T save(T entity){
        getEM().persist(entity);

        return entity;
    }

    public <T> T update(T entity){
        T entityUpdate = getEM().merge(entity);

        return entityUpdate;
    }

    public Boolean delete(Class<?> clazz, String columnName, Object value){
        StringBuilder sql = new StringBuilder();

        String tablename = "NONE";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            tablename = table.name();
        }

        sql.append("DELETE FROM ")
        .append(tablename).append(" ")
        .append("WHERE ")
        .append(columnName).append(" = ").append(":value");

        int delete = 0;

        try {
            delete = getEM().createNativeQuery(sql.toString())
            .setParameter("value", value)
            .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delete > 0;
    }

    public <T> T findByUpdate(Class<T> clazz, String pk){
        try {
            return getEM().find(clazz, pk);
                        
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public <T> T findByPK(Class<T> clazz, String pk){
        T entityFind = findByUpdate(clazz, pk);

        if(entityFind != null){
            getEM().detach(entityFind);
        }

        return entityFind;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(String tablename,Class<T> clazz){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM ")
        .append(tablename);

        List<T> lists = getEM().createNativeQuery(sql.toString(),clazz).getResultList();

        return lists;
    }
    
    
    // public Integer getCountOfData(String tablename){
    //     StringBuilder sql = new StringBuilder();

    //     sql.append("SELECT count(*) FROM ")
    //     .append(tablename);

    //     Integer countOfData = 0;

    //     Object obj = null;

    //     try {
    //         obj = getEM().createNativeQuery(sql.toString()).getSingleResult();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     if(obj != null){
    //         countOfData = Integer.parseInt(obj.toString());
    //     }

    //     return countOfData;
    // }

    
    
    public Integer count(Class<?> clazz){
        StringBuilder sql = new StringBuilder();

        String name = "NONE";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            name = table.name();
        }
        
        sql.append("SELECT count(*) FROM ")
        .append(name);

        Integer countOfData = 0;

        Object obj = null;

        try {
            obj = getEM().createNativeQuery(sql.toString()).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(obj != null){
            countOfData = Integer.parseInt(obj.toString());
        }

        return countOfData;
    }

    public Integer count(Class<?> clazz, Map<String, Object> where){
        StringBuilder sql = new StringBuilder();

        String name = "NONE";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            name = table.name();
        }
        
        sql.append("SELECT count(*) FROM ")
        .append(name)
        .append(" WHERE 1=1");

        for (Entry<String, Object> w : where.entrySet()) {
            sql.append(" AND ")
            .append(w.getKey()).append("=")
            .append(" :").append(w.getKey());
        }

        Integer countOfData = 0;

        Object obj = null;

        try {

            Query nativeQuery = getEM()
                            .createNativeQuery(sql.toString());

            // set parameter
            for (Entry<String, Object> w : where.entrySet()) {
                nativeQuery.setParameter(w.getKey(), w.getValue());
            }
                
            obj = nativeQuery.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(obj != null){
            countOfData = Integer.parseInt(obj.toString());
        }

        return countOfData;
    }


    public Integer count(Class<?> clazz, String sqlCondition, Map<String, Object> where){
        StringBuilder sql = new StringBuilder();

        String name = "NONE";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            name = table.name();
        }
        
        sql.append("SELECT count(*) FROM ")
        .append(name)
        .append(" WHERE 1=1 ")
        .append(sqlCondition);

        Integer countOfData = 0;

        Object obj = null;

        try {
            Query nativeQuery = getEM()
                            .createNativeQuery(sql.toString());

            // set parameter
            for (Entry<String, Object> w : where.entrySet()) {
                nativeQuery.setParameter(w.getKey(), w.getValue());
            }
                
            obj = nativeQuery.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(obj != null){
            countOfData = Integer.parseInt(obj.toString());
        }

        return countOfData;
    }

}
