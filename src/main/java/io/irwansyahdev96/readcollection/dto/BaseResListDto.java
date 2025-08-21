package io.irwansyahdev96.readcollection.dto;

import java.util.List;

public class BaseResListDto<T> {
    
    private List<T> data;
    private Integer countOfData;
    
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public Integer getCountOfData() {
        return countOfData;
    }
    public void setCountOfData(Integer countOfData) {
        this.countOfData = countOfData;
    }

    
}
