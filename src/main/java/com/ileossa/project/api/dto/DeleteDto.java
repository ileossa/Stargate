package com.ileossa.project.api.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by ileossa on 01/08/2017.
 */
public class DeleteDto {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
