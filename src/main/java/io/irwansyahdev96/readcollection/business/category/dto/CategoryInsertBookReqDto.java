package io.irwansyahdev96.readcollection.business.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryInsertBookReqDto {
    
    @NotNull(message = "Category code is must required")
    @NotBlank(message = "Category code isn't only whitespace")
    private String categoryCode;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    
}
