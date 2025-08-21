package io.irwansyahdev96.readcollection.business.status.service;

import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResSingleDto;
import io.irwansyahdev96.readcollection.business.status.dao.StatusDao;
import io.irwansyahdev96.readcollection.business.status.model.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatusService {

    @Autowired
    private StatusDao statusDao;

    public BaseResListDto<Map<String,Object>> getAll(){
        List<Map<String,Object>> all = statusDao.findAll();

        BaseResListDto<Map<String,Object>> baseResListDto = new BaseResListDto<>();
        baseResListDto.setData(all);
        baseResListDto.setCountOfData(statusDao.count(Status.class));

        return baseResListDto;
    }

    public BaseResSingleDto<Status> getByStatusCode(String statusCode){
        Status byPK = statusDao.findByPK(Status.class, statusCode);

        BaseResSingleDto<Status> baseResListDto = new BaseResSingleDto<>();
        baseResListDto.setData(byPK);

        return baseResListDto;
    }

}
