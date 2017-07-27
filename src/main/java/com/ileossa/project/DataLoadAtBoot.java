package com.ileossa.project;

import com.ileossa.project.dao.RoleDao;
import com.ileossa.project.dao.UserDao;
import com.ileossa.project.dao.staticValues.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by v.lafosse on 27/07/2017.
 */
public class DataLoadAtBoot implements ApplicationRunner{

	private final RoleDao roleDao;
	private final UserDao userDao;

	@Autowired
	public DataLoadAtBoot(RoleDao roleDao, UserDao userDao) {
		this.roleDao = roleDao;
		this.userDao = userDao;
	}

	@Override public void run(ApplicationArguments args) throws Exception {
			saveRoleUser();
			createAdminIfNotExist();
	}


	private void saveRoleUser(){
	}


	private void createAdminIfNotExist(){

	}

}
