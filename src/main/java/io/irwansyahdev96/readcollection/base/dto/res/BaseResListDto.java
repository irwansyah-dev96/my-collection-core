package io.irwansyahdev96.readcollection.base.dto.res;

import java.util.List;

public class BaseResListDto<T> {
    
    private List<T> data;
    private Integer page;
    private Integer limit;
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
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    
    
}
