package com.techouts.domain;



public class Order {
	
	private String order_id;
	private String order_date;
	private String cust_name;
	private long phone;
	private String email;
	private Float billAmt;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Float getBillAmt() {
		return billAmt;
	}
	public void setBillAmt(Float billAmt) {
		this.billAmt = billAmt;
	}
	
	
	
   

}
