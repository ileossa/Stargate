package com.ileossa.project.api.repository;

import com.ileossa.project.api.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ileossa on 25/07/2017.
 */
public interface UserRepository extends JpaRepository<UserDao, Long>{

    UserDao findUserDaoByEmailEquals(String email);

}
