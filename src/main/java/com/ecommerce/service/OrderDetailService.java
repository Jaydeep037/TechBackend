package com.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	
	@Transactional
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
					orderInput.getEmail(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					ORDER_PLACED,
					product.getProductDiscountedPrice() * orderProductQuantity.getQuantity(),
					orderInput.getTransactionId(),
					product,
					user
					);
			if(!isCheckout) {
				List<Cart> carts = this.cartDao.findByUser(user);
		     	carts.stream().forEach(x->cartDao.deleteById(x.getCartId()));
			}
			String from="jaydeepingale3464@gmail.com";
			String to =orderInput.getEmail();			
			orderDetailDao.save(orderDetail);
			sendEmail(from,to,orderInput.getFullName(),orderDetail.getTransactionId(),orderInput.getFullAddress(),orderDetail.getOrderAmount());
		}
	}
	
	
	private void sendEmail(String from, String to,String customerName,String orderId,String address,Double totalAmount) {
	    Properties properties = new Properties();
	    properties.setProperty("mail.smtp.host", "smtp.gmail.com");
	    properties.setProperty("mail.smtp.port", "587");
	    properties.setProperty("mail.smtp.auth", "true");
	    properties.setProperty("mail.smtp.starttls.enable", "true");

	    String username = "jaydeepingale3464@gmail.com";
	    String password = "lkhfcskorfbfccvz";

	    Session session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	    });

	    try {
	    	 MimeMessage message = new MimeMessage(session);
	         MimeMessageHelper helper = new MimeMessageHelper(message, true); // Set the second parameter to true for HTML content
	         helper.setFrom(new InternetAddress(from));
	         helper.setTo(new InternetAddress(to));
	         helper.setSubject("Your Order has been Placed!");

	         String emailContent = getEmailContent(customerName, orderId, address, totalAmount);
	         helper.setText(emailContent, true); // Set the second parameter to true to send the email as HTML

	         Transport.send(message);
	         System.out.println("Message sent successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Something went wrong");
	    }
	}
	

	private String getEmailContent(String customerName, String orderId, String address, Double totalAmount) {
		return "<!DOCTYPE html>\n" +
		           "<html>\n" +
		           "<head>\n" +
		           "    <style>\n" +
		           "        /* Add your custom styles here */\n" +
		           "        body {\n" +
		           "            font-family: Arial, sans-serif;\n" +
		           "            background-color: #f2f2f2;\n" +
		           "        }\n" +
		           "        .container {\n" +
		           "            max-width: 600px;\n" +
		           "            margin: 0 auto;\n" +
		           "            padding: 20px;\n" +
		           "            background-color: #fff;\n" +
		           "            border-radius: 5px;\n" +
		           "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
		           "        }\n" +
		           "        .header {\n" +
		           "            text-align: center;\n" +
		           "            background-color: #007bff;\n" +
		           "            color: #fff;\n" +
		           "            padding: 10px;\n" +
		           "            border-radius: 5px 5px 0 0;\n" +
		           "        }\n" +
		           "        .content {\n" +
		           "            padding: 20px;\n" +
		           "        }\n" +
		           "        .footer {\n" +
		           "            text-align: center;\n" +
		           "            padding: 10px;\n" +
		           "            background-color: #f5f5f5;\n" +
		           "            border-radius: 0 0 5px 5px;\n" +
		           "        }\n" +
		           "    </style>\n" +
		           "</head>\n" +
		           "<body>\n" +
		           "    <div class=\"container\">\n" +
		           "        <div class=\"header\">\n" +
		           "            <h1>Your Order has been Placed!</h1>\n" +
		           "        </div>\n" +
		           "        <div class=\"content\">\n" +
		           "            <p>Dear <b>" + customerName + "</b>,</p>\n" +
		           "            <p>Thank you for placing an order with us. We're excited to let you know that your order has been successfully placed and is being processed.</p>\n" +
		           "            <p>Order Details:</p>\n" +
		           "            <ul>\n" +
		           "                <li><b>Order ID: " + orderId + "</b></li>\n" +
		           "                <li><b>Delivery Address: " + address + "</b></li>\n" +
		           "                <li><b>Total Amount: " + totalAmount + "</b></li>\n" +
		           "                <!-- Add more order details here -->\n" +
		           "            </ul>\n" +
		           "            <p>We will notify you once your order is shipped. If you have any questions or need assistance, feel free to contact our support team.</p>\n" +
		           "            <p>Thank you for choosing us!</p>\n" +
		           "        </div>\n" +
		           "        <div class=\"footer\">\n" +
		           "            <p>If you have any questions or need support, please contact us at jaydeepingale3464@gmail.com</p>\n" +
		           "        </div>\n" +
		           "    </div>\n" +
		           "</body>\n" +
		           "</html>";
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
