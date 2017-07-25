package com.ileossa.project.dao;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by ileossa on 25/07/2017.
 */
@Entity
@Table(name = "USER")
public class UserDao {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    @Column( unique = true)
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @Min(18)
    private Integer age;

    @NotNull
    @Size(min=2, max=30)
    private String name;

    @NotNull
    @ManyToMany
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID") )
    private Set<RoleDao> userRoles;


    public UserDao() {
    }

    public UserDao(String email, String password, Integer age, String name, Set<RoleDao> userRoles) {
        this.email = email;
        this.password = password;
        this.age = age;
        this.name = name;
        this.userRoles = userRoles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleDao> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<RoleDao> userRoles) {
        this.userRoles = userRoles;
    }
}
