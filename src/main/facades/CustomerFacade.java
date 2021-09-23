package com.ronit.facades;


import java.util.Date;
import java.util.List;

import com.ronit.beans.Coupon;
import com.ronit.beans.Customer;
import com.ronit.dao.CompaniesDao;
import com.ronit.dao.CouponsDao;
import com.ronit.dao.CustomersDao;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;

public class CustomerFacade extends ClientFacade {

	private int customerId;
	
	/**
	 * facade which is received when a customer logs in 
	 *  @param email and password - the customer email and password
	 * @return true if email and passwaord exists in the data base and the customer can log in
	 * @throws CouponSystemException
	 */
	
	public CustomerFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao) {
		super(companiesDao, customersDao, couponsDao);
	}

	public boolean login(String email, String passwaord) throws CouponSystemException {
		Customer customer = null;
		try {
			customer = customersDao.getCustomerByEmailAndPassword(email, passwaord);
			customerId = customer.getId();
			return true;
		} catch (Exception e) {
			throw new CouponSystemException("login faild", e);
		}
	}

	public void PurchaseCoupon(int couponId) throws CouponSystemException {
		// check if this customer don't have this coupon;
		try {
			Coupon coupon = couponsDao.getOneCoupon(couponId);
//			couponsDao.addCouponPurchase(this.customerId, CouponId);
			if(customersDao.isPurchaseExist(customerId, couponId)) {
				throw new CouponSystemException("PurchaseCoupon failed - customer already have  this coupon");
			}
//			couponsDao.addCoupon(coupon);
			if (coupon.getAmount() == 0) {
				throw new CouponSystemException("PurchaseCoupon failed - amount is 0");
			}
			if (coupon.getEnd_date().before(new Date())) {
				throw new CouponSystemException("coupon is expired");
			}
			couponsDao.addCouponPurchase(this.customerId, couponId);
			coupon.setAmount(coupon.getAmount() - 1);
			couponsDao.updateCoupon(coupon);
		} catch (Exception e) {
			throw new CouponSystemException("PurchaseCoupon failed", e);
		}

	}

	public List<Coupon> getAllCustomerCoupons() throws CouponSystemException {
		return customersDao.getAllCustomerCoupons(customerId);	

	}

	public List<Coupon> getCustomerCoupons(Category category) throws CouponSystemException {
		return customersDao.getAllCustomerCoupons(customerId, category);
		
	}
	public List<Coupon> getCustomerCoupons(int customerId ,int maxPrice) throws CouponSystemException {
		return customersDao.getAllCustomerCoupons(customerId, maxPrice);

		}
	
	public Customer getAllCustomerDetails() throws CouponSystemException {
		return customersDao.getOneCustomer(customerId);

	}

}
