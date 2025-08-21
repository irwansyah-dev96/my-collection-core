package io.irwansyahdev96.readcollection.business.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryInsertReqDto {
    
    @NotNull(message = "Category code is must required")
    @NotBlank(message = "Category code isn't only whitespace")
    private String categoryCode;

    @NotNull(message = "Category name is must required")
    @NotBlank(message = "Category name isn't only whitespace")
    private String categoryName;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
   
}
