package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService cartService;
	
//	@PreAuthorize("hasRole('User')")
	@PostMapping("/newCart/{productId}")
	public Cart addToCart(@PathVariable Integer productId) {
		Cart cart = this.cartService.addToCart(productId);
		return cart;
	}
	
	@GetMapping("/getCartDetails")
	public List<Cart> getCartDetails(){
		return this.cartService.getCartDetails();
	}
	
	@DeleteMapping("/deleteCart/{cartId}")
	public void deleteCart(@PathVariable Integer cartId) {
		this.cartService.deleteCartDetail(cartId);
	}
}
