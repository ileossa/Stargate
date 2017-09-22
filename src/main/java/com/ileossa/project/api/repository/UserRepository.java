package com.ileossa.project.api.repository;

import com.ileossa.project.api.dao.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ileossa on 25/07/2017.
 */
public interface UserRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findUserDaoByEmailEquals(String email);
    UserAccount findByEmail(String email);
    UserAccount findByConfirmationToken(String token);

}
