package com.ileossa.project.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ileossa on 01/08/2017.
 */
public class UpdateDto {

    @NotNull
    private String email;

    @NotNull
    @Size(min=4, max = 32)
    private String password;

    @NotNull
    @Size(min=4, max = 32)
    private String matchedPassword;

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

    public String getMatchedPassword() {
        return matchedPassword;
    }

    public void setMatchedPassword(String matchedPassword) {
        this.matchedPassword = matchedPassword;
    }
}
