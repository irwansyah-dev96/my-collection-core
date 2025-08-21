package io.irwansyahdev96.readcollection.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2",strategy = "uuid2")
    @Column(length = 36,nullable = false)
    private String id;

    @Column(name = "username",nullable = false,length = 50,unique = true)
    private String username;

    @Column(name = "name",nullable = false,length = 50)
    private String name;

    @Column(name = "password",nullable = false,length = 250)
    private String password;

    @Column(name = "role",nullable = false,length = 50)
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
