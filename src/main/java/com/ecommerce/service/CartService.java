package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.CartDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.UserDao;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.security.JwtAuthenticationFilter;

@Service
public class CartService {

	@Autowired
	UserDao userDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	CartDao cartDao;

	public Cart addToCart(Integer productId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//		or
//		String userName = JwtAuthenticationFilter.CURRENT_USER;
		Product product = productDao.findById(productId).get();
		User user = null;
		if (userName != null) {
			user = userDao.findById(userName).get();
		}
		if (product != null && user != null) {
			Cart cart = new Cart(product, user);
			return cartDao.save(cart);
		}
		return null;
	}

	public List<Cart> getCartDetails() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userName != null) {
			User user = userDao.findById(userName).get();
			if (user != null) {
				return cartDao.findByUser(user);
			}
		}
		return null;
	}
}
