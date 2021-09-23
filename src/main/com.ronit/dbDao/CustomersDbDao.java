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
import com.ronit.beans.Customer;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.utils.ConnectionPool;

public class CustomersDbDao implements CustomersDao {

	@Override
	public boolean isCustomerExists(String email, String password) throws CouponSystemException {

		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers where email=? and password=?";
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
	public int addCustomer(Customer customer) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into customers (first_name, last_name, email, password) values(?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sqlStatement,
					PreparedStatement.RETURN_GENERATED_KEYS);
//			statement.setInt(1, newCustomer.getId());
			statement.setString(1, customer.getFirst_name());
			statement.setString(2, customer.getLast_name());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
//			statement.setObject(5, customer.getCoupons());

			statement.executeUpdate();

			ResultSet rsId = statement.getGeneratedKeys();
			rsId.next();
			int theId = rsId.getInt(1);
			return theId;

		} catch (SQLException e) {
			throw new CouponSystemException("addCustomer fail", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "update customers set first_name=?, last_name=?, email=?, password=? where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, customer.getFirst_name());
			statement.setString(2, customer.getLast_name());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			statement.setInt(5, customer.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("updateCustomer failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public void deleteCustomer(int customerID) {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "Delete from customers where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerID);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public List<Customer> getAllCustomers() throws CouponSystemException {
		List<Customer> list = new ArrayList<>();
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from customers";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setFirst_name(rs.getString("first_name"));
				customer.setLast_name(rs.getString("last_name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				list.add(customer);
				System.out.println(customer);
			}
			return list;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCompanies failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		
	}

	@Override
	public Customer getOneCustomer(int customerID) {
		Customer customer = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers where id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerID);
			result = statement.executeQuery();
			if (result.next()) {
				customer = new Customer();
				customer.setId(result.getInt("id"));
				customer.setFirst_name(result.getString("First_name"));
				customer.setLast_name(result.getString("Last_name"));
				customer.setEmail(result.getString("Email"));
				customer.setPassword(result.getString("Password"));
				System.out.println(customer.toString());
			} else {
				System.out.println("cannot find any customers");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
		return customer;
	}

	@Override
	public boolean isCustomerExistsByEmail(String email) throws CouponSystemException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers where email=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, email);
			result = statement.executeQuery();
			return result.next();
		} catch (SQLException e) {
			throw new CouponSystemException("getCustomerByEmailAndPassword failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) throws CouponSystemException {
		Customer customer = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from customers where email=? and password=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setString(1, email);
			statement.setString(2, password);
			result = statement.executeQuery();
			
			if (result.next()) {
				customer = new Customer();
				customer.setId(result.getInt("id"));
				customer.setFirst_name(result.getString("First_name"));
				customer.setLast_name(result.getString("Last_name"));
				customer.setEmail(result.getString("Email"));
				customer.setPassword(result.getString("Password"));
				System.out.println(customer.toString());
				return customer;
			} else {
				throw new CouponSystemException("getCustomerByEmailAndPassword not found");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("getCustomerByEmailAndPassword failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public boolean isPurchaseExist(int customerID, int couponID) throws CouponSystemException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from customers_coupons where customer_id=? and coupon_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerID);
			stmt.setInt(2, couponID);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
	
		} catch (SQLException e) {
			throw new CouponSystemException("isPurchaseExist failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	@Override
	public List<Coupon> getAllCustomerCoupons(int customerId) throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupons where id in(select coupon_id from customers_coupons where customer_id=?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, customerId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setAmount(rs.getInt("Amount"));
				coupon.setCompanyID(rs.getInt("Company_id"));
				coupon.setCategory((com.ronit.enums.Category.values()[rs.getInt("Category_id")]));
				coupon.setTitle(rs.getString("Title"));
				coupon.setDescription(rs.getString("Description"));
				coupon.setImage(rs.getString("Image"));
				coupon.setStart_date(rs.getDate("Start_date"));
				coupon.setEnd_date(rs.getDate("End_date"));
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
	public List<Coupon> getAllCustomerCoupons(int customerId, Category category) throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupons where category_id=? and id in(select coupon_id from customers_coupons where customer_id=?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, category.getId());
			stmt.setInt(2, customerId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setAmount(rs.getInt("Amount"));
				coupon.setCompanyID(rs.getInt("Company_id"));
				coupon.setCategory((com.ronit.enums.Category.values()[rs.getInt("Category_id")]));
				coupon.setTitle(rs.getString("Title"));
				coupon.setDescription(rs.getString("Description"));
				coupon.setImage(rs.getString("Image"));
				coupon.setStart_date(rs.getDate("Start_date"));
				coupon.setEnd_date(rs.getDate("End_date"));
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
	public List<Coupon> getAllCustomerCoupons(int customerId, double maxPrice) throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupons where price<=? and id in(select coupon_id from customers_coupons where customer_id=?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setDouble(1, maxPrice);
			stmt.setInt(2, customerId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setPrice(rs.getDouble("Price"));
				coupon.setAmount(rs.getInt("Amount"));
				coupon.setCompanyID(rs.getInt("Company_id"));
				coupon.setCategory((com.ronit.enums.Category.values()[rs.getInt("category_id")]));
				coupon.setTitle(rs.getString("Title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setImage(rs.getString("Image"));
				coupon.setStart_date(rs.getDate("Start_date"));
				coupon.setEnd_date(rs.getDate("End_date"));
				list.add(coupon);
			}
			return list;
		} catch (SQLException e) {
			throw new CouponSystemException("getAllCustomerCoupons failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
}
