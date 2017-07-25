package com.ileossa.project.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Created by ileossa on 24/07/2017.
 */
public class UserDto {

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String verified_password;

    @NotNull
    @Min(18)
    private Integer age;

    @NotNull
    @Size(min=2, max=30)
    private String name;


    /////


    public UserDto() {
    }

    public UserDto(String email, String password, String verified_password, Integer age, String name) {
        this.email = email;
        this.password = password;
        this.verified_password = verified_password;
        this.age = age;
        this.name = name;
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

    public String getVerified_password() {
        return verified_password;
    }

    public void setVerified_password(String verified_password) {
        this.verified_password = verified_password;
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
}
