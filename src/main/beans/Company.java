package com.ronit.beans;

import java.util.List;

public class Company {
	private int CompanyID;
	private String name;
	private String email;
	private String password;

	private List<Coupon> coupons;

	public Company() {
	}

	public Company(int CompanyID, String name, String email, String password) {
		this.CompanyID = CompanyID;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Company(int CompanyID, String name, String email, String password, List<Coupon> coupons) {
		this.CompanyID = CompanyID;
		this.name = name;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
	}

	public int getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(int CompanyID) {
		this.CompanyID = CompanyID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Company [CompanyID=" + CompanyID + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", coupons=" + coupons + "]";
	}
}
