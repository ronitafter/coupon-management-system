package com.ronit.facades;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.ronit.beans.Company;
import com.ronit.beans.Coupon;
import com.ronit.dao.CompaniesDao;
import com.ronit.dao.CompaniesDbDao;
import com.ronit.dao.CouponsDao;
import com.ronit.dao.CouponsDbDao;
import com.ronit.dao.CustomersDao;
import com.ronit.dao.CustomersDbDao;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;

public class CompanyFacade extends ClientFacade {
	private int companyId;
	
	/**
	 * facade which is received when a company logs in 
	 *  @param email and password - the company email and password
	 * @return true if email and passwaord exists in the data base and the company can  log in
	 * @throws CouponSystemException
	 */
	
	public CompanyFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao) {
		super(companiesDao, customersDao, couponsDao);
	}

	public boolean login(String email, String passwaord) {
		try {
			Company company = null;
			company = companiesDao.getCompanyByEmailAndPassword(email, passwaord);
			this.companyId = company.getCompanyID();
			return true;
		} catch (CouponSystemException e) {
			return false;
		}
	}

	public int addCoupon(Coupon coupon) throws CouponSystemException {
		if (this.companiesDao.isCouponExistsByCompanyIdAndTitle(coupon.getCompanyID(), coupon.getTitle())) {
			throw new CouponSystemException("addCoupon failed the Title " + coupon.getTitle() + "Company_id"
					+ coupon.getCompanyID() + " already exists");
		}
		if (coupon.getEnd_date().before(new Date(System.currentTimeMillis()))) {
			throw new CouponSystemException("addCoupon failed this coupon already expired");
		}
		if (coupon.getEnd_date().before(coupon.getStart_date())) {
			throw new CouponSystemException("addCoupon failed the end date is before start date");

		}
		return this.couponsDao.addCoupon(coupon);
	}

	public void UpdateCoupon(Coupon coupon) throws CouponSystemException {
		// 1. get the coupon from db
		Coupon couponFromDb = this.couponsDao.getOneCoupon(coupon.getId());
		// 2. check that the coupon belongs to this company
		if (couponFromDb.getCompanyID() != this.companyId) {
			throw new CouponSystemException("UpdateCoupon failed - this coupon does not belong to this company");
		}
		// 3. update the fields that can be updated
		couponFromDb.setAmount(coupon.getAmount());
		couponFromDb.setCategory(coupon.getCategory());
		couponFromDb.setDescription(coupon.getDescription());
		couponFromDb.setStart_date(coupon.getStart_date());
		couponFromDb.setEnd_date(coupon.getEnd_date());
		couponFromDb.setImage(coupon.getImage());
		couponFromDb.setPrice(coupon.getPrice());
		couponFromDb.setTitle(coupon.getTitle());
		couponsDao.updateCoupon(couponFromDb);
	}

	public void deleteCoupon(int couponId) throws CouponSystemException {
		// is coupon exist in system
		Coupon couponToDelete = this.couponsDao.getOneCoupon(couponId);
		// is coupon belong to this company
		if (couponToDelete.getCompanyID() == this.companyId) {
			// 1. delete all coupon purchases
			this.couponsDao.deletCouponPurchase(couponId);
			// 2. delete the coupon
			this.couponsDao.deleteCoupon(couponId);
		} else {
			throw new CouponSystemException("deleteCoupon failed - not found in this company");
		}
	}
	public List<Coupon> getCompanyCoupons(int companyId, Category category) throws CouponSystemException {
		return companiesDao.getCompanyCoupons(companyId, category);

	}

	public List<Coupon> getCompanyCoupons(int companyId, double maxPrice) throws CouponSystemException {
		return companiesDao.getCompanyCoupons(companyId, maxPrice);

	}
	
	public List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException {
		return companiesDao.getCompanyCoupons(companyId);

	}

	public Company getCompanyDetails() throws CouponSystemException {
		return companiesDao.getOneCompany(companyId);

	}

}
