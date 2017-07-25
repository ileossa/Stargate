package com.ileossa.project.repository;

import com.ileossa.project.dao.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ileossa on 25/07/2017.
 */
public interface RoleRepository extends JpaRepository<RoleDao, Long> {
}
