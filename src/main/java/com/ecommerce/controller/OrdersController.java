package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.OrderInput;
import com.ecommerce.entity.TransactionDetails;
import com.ecommerce.service.OrderDetailService;

@RestController
public class OrdersController {
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@PostMapping("/placeorder/{isCheckout}")
	public void placeorder(@PathVariable boolean isCheckout, @RequestBody OrderInput orderInput)
	{
		this.orderDetailService.placeholder(orderInput,isCheckout);
	}
	
	@GetMapping("/getOrderDetails")
	public List<OrderDetail> getOrderDetails(){
		return this.orderDetailService.getOrdetails();
	}
	
	@GetMapping("/getAllOrders/{status}")
	public List<OrderDetail> getAllOrderDetails(@PathVariable String status){
		return this.orderDetailService.getAllOrderDetails(status);
	}
	
	@GetMapping("/markAsDelivered/{orderId}")
	public OrderDetail markAsDelivered(@PathVariable Integer orderId) {
	return this.orderDetailService.markAsDelivered(orderId);	
	}
	
	@GetMapping("/transaction/{amount}")
	public TransactionDetails createTransaction(@PathVariable Long amount) {
		return this.orderDetailService.createTransaction(amount);
	}
	
}
