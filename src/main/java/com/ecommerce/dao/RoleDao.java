package com.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.Role;

public interface RoleDao extends  CrudRepository<Role, String> {

}
