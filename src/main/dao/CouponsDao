package com.ronit.dao;

import java.util.List;

import com.ronit.beans.Coupon;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;

public interface CouponsDao {
	
	void deleteExpiredCoupons()throws CouponSystemException;
	int addCoupon(Coupon coupon) throws CouponSystemException;
	void updateCoupon(Coupon coupon) throws CouponSystemException;
	void deleteCoupon(int couponID) throws CouponSystemException;
	
	
	/**
	 * @return all coupons in the system
	 * @throws CouponSystemException
	 */
	List <Coupon> getAllCoupons() throws CouponSystemException;
	Coupon getOneCoupon(int couponID) throws CouponSystemException;
	void addCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	/**
	 * deletCouponPurchase - delete a customer's coupons from the db, by customerID and couponID
	 *  deletCompanyCoupons - delete a company's coupons from the db, by companyID
	 * @param couponID, companyID,customerID
	 * @throws CouponSystemException
	 */
	void deletCouponPurchase(int customerID, int couponID) throws CouponSystemException;
	void deletCouponPurchase(int couponID) throws CouponSystemException;
	void deletCompanyCoupons(int companyID)throws CouponSystemException;
	void deletCouponPurchaseOfCompany(int companyID) throws CouponSystemException;
	void deletCouponPurchaseOfCustomer(int customerID) throws CouponSystemException;

	



}
