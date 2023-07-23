package com.ecommerce.entity;

public class TransactionDetails {

	private String orderId;
	private Long amount;
	private String currency;
	private String key;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public TransactionDetails(String orderId, Long amount, String currency, String key) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.key = key;
	}
	public TransactionDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
