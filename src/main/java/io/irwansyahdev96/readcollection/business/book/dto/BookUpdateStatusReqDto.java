package io.irwansyahdev96.readcollection.business.book.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookUpdateStatusReqDto {
    
    @NotNull(message = "issbn is must required")
    @NotBlank(message = "issbn isn't only whitespace")
    private String issbn;

    @NotNull(message = "status code is must required")
    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getIssbn() {
        return issbn;
    }

    public void setIssbn(String issbn) {
        this.issbn = issbn;
    }

    

    
    
}
