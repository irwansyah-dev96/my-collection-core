package io.irwansyahdev96.readcollection.business.status.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_status")
public class Status {

    @Id
    @Column(name = "status_code",nullable = false,length = 5)
    private String statusCode;


    @Column(name = "status_name",nullable = false,length = 15)
    private String statusName;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
