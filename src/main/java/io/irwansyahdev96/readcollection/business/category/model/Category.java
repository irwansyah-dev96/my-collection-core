package io.irwansyahdev96.readcollection.business.category.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @Column(name = "category_code",length = 5,nullable = false, unique = true)
    private String categoryCode;

    @Column(name = "category_name",length = 20,nullable = false)
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
