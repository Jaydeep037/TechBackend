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
		List<Cart> existingCar = cartDao.findAll();
		boolean flag = existingCar.stream().anyMatch(x->productId==x.getProduct().getProductId());
		if(flag) {
			return null;
		}else {
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
	
	public void deleteCartDetail(Integer cartId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(userName != null) {
			User user  = this.userDao.findById(userName).get();
			if(user!=null) {
				List<Cart> cart = cartDao.findByUser(user);
				boolean flag = cart.stream().anyMatch(x->cartId==x.getCartId());
				if(flag) {
					cartDao.deleteById(cartId);
				}
			}
		}
	}
	
}
