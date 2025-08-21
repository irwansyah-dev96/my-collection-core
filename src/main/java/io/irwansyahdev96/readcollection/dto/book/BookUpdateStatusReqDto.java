package io.irwansyahdev96.readcollection.dto.book;

import javax.validation.constraints.NotNull;

public class BookUpdateStatusReqDto {
    
    @NotNull(message = "id is must required")
    private String id;

    @NotNull(message = "status code is must required")
    private String statusCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    

    
    
}
