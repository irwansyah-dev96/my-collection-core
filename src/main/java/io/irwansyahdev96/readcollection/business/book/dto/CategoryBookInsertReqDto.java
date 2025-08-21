package io.irwansyahdev96.readcollection.business.book.dto;

import javax.validation.constraints.NotNull;

public class CategoryBookInsertReqDto {
    @NotNull(message = "category code is must required")
    private String bookTypeCode;

    @NotNull(message = "issbn is must required")
    private String issbn;

    public String getCategoryCode() {
        return bookTypeCode;
    }

    public void setCategoryCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getIssbn() {
        return issbn;
    }

    public void setIssbn(String issbn) {
        this.issbn = issbn;
    }
    
}
