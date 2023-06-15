package com.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.User;

public interface UserDao extends CrudRepository<User, String> {

}
