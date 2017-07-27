package com.ileossa.project.dao;

import com.ileossa.project.dao.staticValues.Role;

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
    private Role role;

    @ManyToMany(mappedBy = "userRoles")
    private Set<UserDao> userDaos;

    public RoleDao() {
    }

    public RoleDao(Role role, Set<UserDao> userDaos) {
        this.role = role;
        this.userDaos = userDaos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<UserDao> getUserDaos() {
        return userDaos;
    }

    public void setUserDaos(Set<UserDao> userDaos) {
        this.userDaos = userDaos;
    }
}
