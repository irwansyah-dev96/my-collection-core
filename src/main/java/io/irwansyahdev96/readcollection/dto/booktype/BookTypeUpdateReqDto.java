package io.irwansyahdev96.readcollection.dto.booktype;

import javax.validation.constraints.NotNull;

public class BookTypeUpdateReqDto {
    
    @NotNull(message = "id is must required")
    private String id;

    private String bookTypeName;

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
