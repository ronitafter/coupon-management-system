package com.ronit.dao;

import java.util.List;

import com.ronit.beans.Company;
import com.ronit.beans.Coupon;
import com.ronit.beans.Customer;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;


public interface CustomersDao {
	
	/**
	 * isCustomerExists - checks if a customer exists in the db,
	 * if it return false, customer is added to the db
	 *  getOneCustomer - get a customer's details from the db, by customerID
	 * @param customerID, email,password
	 * @throws CouponSystemException
	 */
	boolean isCustomerExists(String email, String password) throws CouponSystemException;
	boolean isCustomerExistsByEmail(String email) throws CouponSystemException;
	int addCustomer(Customer customer) throws CouponSystemException;
	void updateCustomer(Customer customer) throws CouponSystemException;;
	void deleteCustomer(int customerID) throws CouponSystemException;
	List <Customer> getAllCustomers() throws CouponSystemException;
	Customer getOneCustomer(int customerID) throws CouponSystemException;;
	Customer getCustomerByEmailAndPassword(String email, String password) throws CouponSystemException;
	boolean isPurchaseExist(int customerID, int couponID) throws CouponSystemException;
	List <Coupon> getAllCustomerCoupons(int customerId) throws CouponSystemException;
	/**
	 * @param customerId
	 * @param category
	 * @return all coupons of the specified customer and category
	 * @throws CouponSystemException
	 */
	List <Coupon> getAllCustomerCoupons(int customerId, Category category) throws CouponSystemException;
	/**
	 * @param customerId
	 * @param maxPrice
	 * @return all coupons of the specified customer and up to the specified price
	 * @throws CouponSystemException
	 */
	List <Coupon> getAllCustomerCoupons(int customerId, double maxPrice) throws CouponSystemException;




}
