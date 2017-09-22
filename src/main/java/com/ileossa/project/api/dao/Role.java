package com.ileossa.project.api.dao;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ileossa on 20/09/2017.
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    @ManyToMany(mappedBy = "roles")
    private Set<UserAccount> userAccountSet;


    protected Role() {
    }

    public Role(String name, Set<UserAccount> userAccountSet) {
        this.name = name;
        this.userAccountSet = userAccountSet;
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

    public Set<UserAccount> getUserAccountSet() {
        return userAccountSet;
    }

    public void setUserAccountSet(Set<UserAccount> userAccountSet) {
        this.userAccountSet = userAccountSet;
    }
}
