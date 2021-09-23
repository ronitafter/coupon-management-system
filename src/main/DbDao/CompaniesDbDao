package com.ronit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ronit.beans.Company;
import com.ronit.beans.Coupon;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.utils.ConnectionPool;

public class CompaniesDbDao implements CompaniesDao {

	@Override
	public boolean isCompanyExistsByEmailAndPassword(String email, String password) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where email=? and password=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			return rs.next(); // will return true if the customer found
		} catch (SQLException e) {
			throw new CouponSystemException("isCustomerExists failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public boolean isCompanyExistsByName(String name) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where name=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			return rs.next(); // will return true if the customer found
		} catch (SQLException e) {
			throw new CouponSystemException("isCustomerExists failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public boolean isCompanyExistsByEmail(String email) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where email=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			return rs.next(); // will return true if the customer found
		} catch (SQLException e) {
			throw new CouponSystemException("isCustomerExists failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public int addCompany(Company company) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into companies (name, email, password) values(?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sqlStatement,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.executeUpdate();
			// the id of the newly created is in the ResultSet below
			ResultSet rsId = statement.getGeneratedKeys();
			rsId.next(); // go to the first line of the ResultSet
			int id = rsId.getInt(1);
			return id;
		} catch (SQLException e) {
			throw new CouponSystemException("addCompany faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public void updateCompany(Company company) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "update companies set name=?, email=?, password=? where id=?;";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPassword());
			statement.setInt(4, company.getCompanyID());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	/**
	 * delete a company
	 * 
	 * @param companyID the id of the company to delete
	 */
	@Override
	public void deleteCompany(int companyID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "Delete from companies where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, companyID);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	/**
	 * get all companies from the database
	 * 
	 * @return the list of companies
	 * 
	 * @throws CouponSystemException if an SQL error occurs
	 * 
	 */
	@Override
	public List<Company> getAllCompanies() throws CouponSystemException {
		// prepare a list of companies to be returned
		List<Company> list = new ArrayList<>();
		Connection con = null;
		try {
			// get a connection from pool
			con = ConnectionPool.getInstance().getConnection();
			// set the sql command
			String sql = "select * from companies";
			// create the statement object
			Statement stmt = con.createStatement();
			// run the sql command (select) and get the result set
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) { // run over the results and create company object from each
				// create one company object
				Company company = new Company();
				// set the company state from the current result of the ResultSet
				company.setCompanyID(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				// add the company to the list
				list.add(company);
			}
			// after all companies are in the list - return the list
			return list;
		} catch (Exception e) {
			// if something went wrong the method throws an exception with message and cause
			throw new CouponSystemException("getAllCompanies failed", e);
		} finally {
			// finally we return the connection to the pool
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Company company = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, companyID);
			result = statement.executeQuery();
			if (result.next()) {
				company = new Company();
				company.setCompanyID(result.getInt("id"));
				company.setName(result.getString("name"));
				company.setEmail(result.getString("email"));
				company.setPassword(result.getString("password"));
				System.out.println(company.toString());
				return company;
			} else {
				throw new CouponSystemException("getOneCompany failed, not found");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCompany failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public List<Coupon> getCompanyCoupons(int companyId, Category categoryId) throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupons where company_id=? and category_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, companyId);
			stmt.setInt(2, categoryId.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setCategory((com.ronit.enums.Category.values()[rs.getInt("category_id")]));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setId(rs.getInt("id"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setAmount(rs.getInt("Amount"));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setImage(rs.getString("image"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				list.add(coupon);
			}
			return list;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomerCoupons failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	public List<Coupon> getCompanyCoupons(int companyId, double maxPrice) throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where company_id=? and price<=?";
			// create a statemt object with sql inside
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			// set the sql parameters of the statement
			statement.setInt(1, companyId);
			statement.setDouble(2, maxPrice);
			// execute the sql command
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Coupon coupon = new Coupon();
				coupon.setCompanyID(result.getInt("compay_id"));
				coupon.setPrice(result.getDouble("price"));
				coupon.setId(result.getInt("id"));
				coupon.setAmount(result.getInt("amount"));
				coupon.setCategory(Category.values()[result.getInt("category_id")]);
				coupon.setDescription(result.getString("description"));
				coupon.setEnd_date(result.getDate("end_date"));
				coupon.setStart_date(result.getDate("start_date"));
				coupon.setImage(result.getString("image"));
				coupon.setTitle(result.getString("title"));
				list.add(coupon);
			}
			return list;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanies failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public boolean isCouponExistsByCompanyIdAndTitle(int companyID, String couponTitle ) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where company_id=? and title=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, companyID);
			statement.setString(2, couponTitle);
			ResultSet rs = statement.executeQuery();
			return rs.next(); // will return true if the customer found
		} catch (SQLException e) {
			throw new CouponSystemException("isCouponTitleExistsByCompanyId failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	/**
	 * returns the company or throws CouponSystemException if not found
	 */
	@Override
	public Company getCompanyByEmailAndPassword(String email, String password) throws CouponSystemException {
		Company company = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from companies where email=? and password=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, email);
			statement.setString(2, password);
			result = statement.executeQuery();
			if (result.next()) {
				company = new Company();
				company.setCompanyID(result.getInt("id"));
				company.setName(result.getString("name"));
				company.setEmail(result.getString("Email"));
				company.setPassword(result.getString("Password"));
				
				System.out.println(company.toString());
				return company;
			} else {
				throw new CouponSystemException("cannot find any company Email....\"");
			}
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyByEmailAndPassword faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where company_id=?";
			// create a statemt object with sql inside
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			// set the sql parameters of the statement
			statement.setInt(1, companyId);
			// execute the sql command
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Coupon coupon = new Coupon();
				coupon.setCompanyID(result.getInt("compay_id"));
				coupon.setPrice(result.getDouble("price"));
				coupon.setId(result.getInt("id"));
				coupon.setAmount(result.getInt("amount"));
				coupon.setCategory(Category.values()[result.getInt("category_id")]);
				coupon.setDescription(result.getString("description"));
				coupon.setEnd_date(result.getDate("end_date"));
				coupon.setStart_date(result.getDate("start_date"));
				coupon.setImage(result.getString("image"));
				coupon.setTitle(result.getString("title"));
				list.add(coupon);
			}
			return list;
		} catch (SQLException e) {
			throw new CouponSystemException("getC ompany Coupons failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}
}
