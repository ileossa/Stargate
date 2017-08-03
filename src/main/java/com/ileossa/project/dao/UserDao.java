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
    private Roles role;

    private Boolean actif;


    public UserDao() {
        super();
        this.role = Roles.ANONYMOUS;
        this.actif = false;
    }

    public UserDao(String email, String password) {
        super();
        this.email = email;
        this.password = password;
        this.role = Roles.ANONYMOUS;
        this.actif = false;
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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}
