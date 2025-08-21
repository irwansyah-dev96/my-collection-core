package io.irwansyahdev96.readcollection.business.status.controller;

import io.irwansyahdev96.readcollection.base.dto.res.BaseResListDto;
import io.irwansyahdev96.readcollection.base.dto.res.BaseResSingleDto;
import io.irwansyahdev96.readcollection.business.status.model.Status;
import io.irwansyahdev96.readcollection.business.status.service.StatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<BaseResListDto<?>> getAll(){
        BaseResListDto<Map<String,Object>> baseResListDto = statusService.getAll();

        return new ResponseEntity<>(baseResListDto,HttpStatus.OK);
    }

    @GetMapping("{statusCode}/status")
    public ResponseEntity<BaseResSingleDto<Status>> getByStatus(@PathVariable("statusCode") String statusCode){
        BaseResSingleDto<Status> byStatusCode = statusService.getByStatusCode(statusCode);

        return new ResponseEntity<>(byStatusCode, HttpStatus.OK);
    }



}
