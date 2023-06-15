package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.RoleDao;
import com.ecommerce.entity.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;

	public Role createNewRole(Role role) {
		Role savedRole = roleDao.save(role);
		return savedRole;
	}
}
