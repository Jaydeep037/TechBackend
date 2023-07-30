package com.ecommerce.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private String orderFullName;
	private String email;
	private String orderFullOrder;
	private String orderContactNumber;
	private String orderAlternateContantNumber;
	private String orderStatus;
	private Double orderAmount;
	private String transactionId;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@OneToOne
	private User user;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderFullName() {
		return orderFullName;
	}
	public void setOrderFullName(String orderFullName) {
		this.orderFullName = orderFullName;
	}
	public String getOrderFullOrder() {
		return orderFullOrder;
	}
	public void setOrderFullOrder(String orderFullOrder) {
		this.orderFullOrder = orderFullOrder;
	}
	public String getOrderContactNumber() {
		return orderContactNumber;
	}
	public void setOrderContactNumber(String orderContactNumber) {
		this.orderContactNumber = orderContactNumber;
	}
	public String getOrderAlternateContantNumber() {
		return orderAlternateContantNumber;
	}
	public void setOrderAlternateContantNumber(String orderAlternateContantNumber) {
		this.orderAlternateContantNumber = orderAlternateContantNumber;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public OrderDetail(String orderFullName, String email, String orderFullOrder, String orderContactNumber,
			String orderAlternateContantNumber, String orderStatus, Double orderAmount, String transactionId,
			Product product, User user) {
		super();
		this.orderFullName = orderFullName;
		this.email = email;
		this.orderFullOrder = orderFullOrder;
		this.orderContactNumber = orderContactNumber;
		this.orderAlternateContantNumber = orderAlternateContantNumber;
		this.orderStatus = orderStatus;
		this.orderAmount = orderAmount;
		this.transactionId = transactionId;
		this.product = product;
		this.user = user;
	}
	public OrderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
