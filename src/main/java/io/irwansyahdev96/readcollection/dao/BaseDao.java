package io.irwansyahdev96.readcollection.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public Boolean delete(String tableName,String columnName,Object value){
        StringBuilder sql = new StringBuilder();

        sql.append("DELETE FROM ")
        .append(tableName).append(" ")
        .append("WHERE ")
        .append(columnName).append(" = ").append(":value");

        int delete = 0;

        try {
            delete = getEM().createNativeQuery(sql.toString())
            .setParameter("value", value)
            .executeUpdate();
        } catch (Exception e) {
            
        }

        return delete > 0;
    }

    public <T> T findById(Class<T> clazz,String id){
        T entityFind = null;

        try {
            entityFind = getEM().find(clazz, id);
                        
        } catch (Exception e) {
            
        }

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
    
    public Integer getCountOfData(String tablename){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT count(*) FROM ")
        .append(tablename);

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
}
