package com.ileossa.project.dao;

import com.ileossa.project.dto.UserDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by ileossa on 25/07/2017.
 */
@Entity
@Table(name = "ROLE")
public class RoleDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "userRoles")
    private Set<UserDao> userDaos;

    public RoleDao() {
    }

    public RoleDao(String name, Set<UserDao> userDaos) {
        this.name = name;
        this.userDaos = userDaos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserDao> getUserDaos() {
        return userDaos;
    }

    public void setUserDaos(Set<UserDao> userDaos) {
        this.userDaos = userDaos;
    }
}
