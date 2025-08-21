package io.irwansyahdev96.readcollection.dto.book;

import javax.validation.constraints.NotNull;

public class BookInsertReadBookReqDto {
    
    @NotNull(message = "book is must required")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
