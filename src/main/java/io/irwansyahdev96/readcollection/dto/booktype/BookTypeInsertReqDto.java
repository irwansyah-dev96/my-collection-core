package io.irwansyahdev96.readcollection.dto.booktype;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookTypeInsertReqDto {
    
    @NotNull(message = "code is must required")
    private String bookTypeCode;

    @NotNull(message = "book type name is must required")
    @NotBlank(message = "book type name isnt only whitespace")
    private String bookTypeName;

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }
   
}
