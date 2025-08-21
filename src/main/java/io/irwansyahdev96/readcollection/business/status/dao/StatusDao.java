package io.irwansyahdev96.readcollection.business.status.dao;

import org.springframework.stereotype.Repository;

import io.irwansyahdev96.readcollection.dao.BaseDao;
import io.irwansyahdev96.readcollection.model.Status;

@Repository
public class StatusDao extends BaseDao{

    public Status getByStatusCode(String statusCode){
        String sql = "SELECT id, status_code, status_name FROM tb_status WHERE status_code = :statusCode";

        Object obj = null;

        try {
            obj = getEM().createNativeQuery(sql,Status.class)
                    .setParameter("statusCode", statusCode)
                    .getSingleResult(); 
        } catch (Exception e) {
            
        }
        
        Status status = null;
        if(obj != null){            
            status = (Status) obj;
        }

        return status;
    }
}
