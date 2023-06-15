//package com.ecommerce.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.ecommerce.dao.OrderDetailDao;
//import com.ecommerce.entity.OrderDetail;
//import com.ecommerce.entity.OrderInput;
//import com.ecommerce.entity.OrderProductQuantity;
//
//@Service
//public class OrderDetailService {
//	
//	private static final String ORDER_PLACED = "";
//	
//	@Autowired
//	OrderDetailDao orderDetailDao;
//
//	public void placeholder(OrderInput orderInput) {
//		List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantityList();	
//		for (OrderProductQuantity orderProductQuantity : orderProductQuantityList) {
//			OrderDetail orderDetail = new OrderDetail(
//					orderInput.getFullName(),
//					orderInput.getFullAddress(),
//					orderInput.getContactNumber(),
//					orderInput.getAlternateContactNumber()
//					);
//		}
//	}
//}
