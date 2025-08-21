package io.irwansyahdev96.readcollection.business.status.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.irwansyahdev96.readcollection.constant.Message;
import io.irwansyahdev96.readcollection.dto.BaseInsertResDto;
import io.irwansyahdev96.readcollection.dto.BaseResListDto;
import io.irwansyahdev96.readcollection.dto.BaseResSingleDto;
import io.irwansyahdev96.readcollection.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.transaction.Transactional;

import io.irwansyahdev96.readcollection.business.status.dao.StatusDao;

@Service
public class StatusService {

    @Autowired
    private StatusDao statusDao;

    public BaseResListDto<Status> getAll(){
        List<Status> statuses = statusDao.getAll("tb_status",Status.class);
        int countOfStatus = statusDao.getCountOfData("tb_status");

        BaseResListDto<Status> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(statuses);
        baseResListDto.setCountOfData(countOfStatus);

        return baseResListDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public BaseInsertResDto save(List<Status> statuses){

        StringJoiner stringJoiner = new StringJoiner(" ");
        
        List<Object> listStatus = new ArrayList<>();

        statuses.forEach(status->{
            Status statusIn = new Status();

            
            statusIn.setStatusCode(status.getStatusCode());
            statusIn.setStatusName(status.getStatusName());

            Status statusInsert = statusDao.save(statusIn);

            if(statusInsert != null){
                stringJoiner.add(statusInsert.getId());
                listStatus.add(statusInsert);
            }
        });

        BaseInsertResDto baseInsertResDto = new BaseInsertResDto();
        
        if(statuses.size() == listStatus.size()){
            baseInsertResDto.setId(stringJoiner.toString());
            baseInsertResDto.setMessage(Message.SUCCESS_SAVE.getMessage());
        }else{
            statusDao.getEM().getTransaction().rollback();
            baseInsertResDto.setMessage(Message.FAILED_SAVE.getMessage());
        }

        return baseInsertResDto;
    }

    public BaseResSingleDto<Status> getByStatusCode(String statusCode){
        BaseResSingleDto<Status> baseResSingleDto = new BaseResSingleDto<>();

        Status status = statusDao.getByStatusCode(statusCode);

        baseResSingleDto.setData(status);

        return baseResSingleDto;
    }
}
