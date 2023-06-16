package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.OrderDetail;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer>{

}
