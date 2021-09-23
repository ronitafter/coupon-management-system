package com.ronit.facades;

import com.ronit.dao.CompaniesDao;
import com.ronit.dao.CouponsDao;
import com.ronit.dao.CustomersDao;
import com.ronit.exceptions.CouponSystemException;

public abstract class ClientFacade {
	protected CompaniesDao companiesDao;
	protected CustomersDao customersDao;
	protected CouponsDao couponsDao;

	public ClientFacade(CompaniesDao companiesDao, CustomersDao customersDao, CouponsDao couponsDao) {
		this.companiesDao = companiesDao;
		this.customersDao = customersDao;
		this.couponsDao = couponsDao;
	}

	/*
	 * return true if the email and password exists in the database and the user can
	 * log in email - the user's email password - the user's password
	 * 
	 */

//	@Override
	public abstract boolean login(String email, String passwaord) throws CouponSystemException;
}
