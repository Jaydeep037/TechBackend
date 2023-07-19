package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.User;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer>{
public List<OrderDetail> findByUser(User user);

public List<OrderDetail> findByOrderStatus(String order);
}
