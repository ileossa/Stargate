package com.ileossa.project.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by ileossa on 01/08/2017.
 */
public class ResendEmailDto {

    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
