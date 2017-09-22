package com.ileossa.project.api.repository;

import com.ileossa.project.api.dao.Role;
import com.ileossa.project.api.dao.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by ileossa on 20/09/2017.
 */
public interface UserRolesRepository extends CrudRepository<Role, Long> {
    Role findByName(String nameRole);
}
