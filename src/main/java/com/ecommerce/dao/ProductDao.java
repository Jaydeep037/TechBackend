package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

}
