package io.irwansyahdev96.readcollection.business.status.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.irwansyahdev96.readcollection.dto.BaseInsertResDto;
import io.irwansyahdev96.readcollection.dto.BaseResListDto;
import io.irwansyahdev96.readcollection.dto.BaseResSingleDto;
import io.irwansyahdev96.readcollection.model.Status;

import java.util.List;

import io.irwansyahdev96.readcollection.business.status.service.StatusService;

@RestController
@RequestMapping("statuses")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<BaseResListDto<Status>> getAll(){
        BaseResListDto<Status> baseResListDto = statusService.getAll();

        return new ResponseEntity<>(baseResListDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseInsertResDto> save(@RequestBody List<Status> statusInsertReqDto){
        BaseInsertResDto baseInsertResDto = statusService.save(statusInsertReqDto);

        return new ResponseEntity<>(baseInsertResDto, HttpStatus.CREATED);
    }

    @GetMapping("{statusCode}/code")
    public ResponseEntity<BaseResSingleDto<Status>> getByStatusCode(@PathVariable("statusCode") String statusCode){
        BaseResSingleDto<Status> baseResSingleDto = statusService.getByStatusCode(statusCode);

        return new ResponseEntity<BaseResSingleDto<Status>>(baseResSingleDto, HttpStatus.OK);
    }
}
