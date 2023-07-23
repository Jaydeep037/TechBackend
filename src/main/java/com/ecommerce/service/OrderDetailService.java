package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.CartDao;
import com.ecommerce.dao.OrderDetailDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.UserDao;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.OrderInput;
import com.ecommerce.entity.OrderProductQuantity;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.TransactionDetails;
import com.ecommerce.entity.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class OrderDetailService {
	
	private static final String ORDER_PLACED = "Placed";
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CartDao cartDao;
	
	public static final String KEY = "rzp_test_EMPKQtrxaEzhC4";
	public static final String SECRET_KEY ="h1OcTafPXUzuPdS1C6nWUrnm";
	public static final String CURRENCY ="INR";
	
	
	public List<OrderDetail> getOrdetails(){
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = this.userDao.findById(currentUser).get();
		return this.orderDetailDao.findByUser(user);
	}
	
	public void placeholder(OrderInput orderInput,boolean isCheckout) {
		List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantityList();	
		for (OrderProductQuantity orderProductQuantity : orderProductQuantityList) {
			Integer productId = orderProductQuantity.getProductId();
			String currentUser= SecurityContextHolder.getContext().getAuthentication().getName();
			
//			String currentUser = JwtAuthenticationFilter.CURRENT_USER;
			
			 User user = userDao.findById(currentUser).get();
			Product product = productDao.findById(productId).get();
			OrderDetail orderDetail = new OrderDetail(
					orderInput.getFullName(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					ORDER_PLACED,
					orderInput.getTransactionId(),
					product.getProductDiscountedPrice() * orderProductQuantity.getQuantity(),
					product,
					user
					);
			if(!isCheckout) {
				List<Cart> carts = this.cartDao.findByUser(user);
		     	carts.stream().forEach(x->cartDao.deleteById(x.getCartId()));
			}
			orderDetailDao.save(orderDetail);
		}
	}
	
	public List<OrderDetail> getAllOrderDetails(String status){
		if(status.equals("All")) {
			List<OrderDetail> orderDetail = this.orderDetailDao.findAll();
			return orderDetail;	
		}else {
			List<OrderDetail> orderDetail = this.orderDetailDao.findByOrderStatus(status);
			return orderDetail;
		}
	}
	
	public OrderDetail markAsDelivered(Integer orderId) {
		Optional<OrderDetail> orderDetail = orderDetailDao.findById(orderId);
		if(orderDetail.isPresent()) {
			orderDetail.get().setOrderStatus("Delivered");
			return orderDetailDao.save(orderDetail.get());
		}else {
			return null;
		}	
	}

	public TransactionDetails createTransaction(Integer amount) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("amount", amount*100);
			jsonObject.put("currency", CURRENCY);
			RazorpayClient razorClient = new RazorpayClient(KEY, SECRET_KEY);	
			Order order = razorClient.orders.create(jsonObject);
			return prepareTransaction(order);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	private TransactionDetails prepareTransaction(Order order) {
		String orderId = order.get("id");
		Integer amount = order.get("amount");
		String currency = order.get("currency");
		TransactionDetails transactionDetails = new TransactionDetails(orderId, amount, currency,KEY);
		return transactionDetails;
	}
}
