package com.ileossa.project.service;

import com.ileossa.project.dao.RoleDao;
import com.ileossa.project.dao.staticValues.Role;
import com.ileossa.project.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by v.lafosse on 27/07/2017.
 */
public class RoleService {

	private final RoleDao roleDao;
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleDao roleDao, RoleRepository roleRepository) {
		this.roleDao = roleDao;
		this.roleRepository = roleRepository;
	}



	public void newRole(RoleDao roleDao){
		RoleDao result = roleRepository.findRoleDaoByRoleEquals(roleDao.getRole());
		if( roleDao == result){
			return;
		}
		roleRepository.save(roleDao);
	}

}
