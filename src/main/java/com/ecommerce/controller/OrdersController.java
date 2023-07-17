package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.OrderInput;
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
}
