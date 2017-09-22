package com.ileossa.project.api.repository;

import com.ileossa.project.api.dao.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ileossa on 20/09/2017.
 */
public interface UserRolesRepository extends CrudRepository<Role, Long> {
}
