package com.ronit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ronit.beans.Coupon;
import com.ronit.enums.Category;
import com.ronit.exceptions.CouponSystemException;
import com.ronit.utils.ConnectionPool;

public class CouponsDbDao implements CouponsDao {

	@Override
	public int addCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into coupons (company_id, category_id, title, description, start_date, end_date, amount, price, image) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sqlStatement,
					PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, coupon.getCompanyID());
			statement.setInt(2, coupon.getCategory().ordinal() + 1);
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStart_date());
			statement.setDate(6, coupon.getEnd_date());
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());

			statement.executeUpdate();

			ResultSet rsKeys = statement.getGeneratedKeys();
			rsKeys.next();
			return rsKeys.getInt(1);
		} catch (SQLException e) {
			throw new CouponSystemException("addCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "update coupons set company_id=?, category_id=?, title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=? where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, coupon.getCompanyID()); 
			statement.setInt(2, coupon.getCategory().ordinal());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStart_date());
			statement.setDate(6, coupon.getEnd_date());
			statement.setInt(7, coupon.getAmount());
			statement.setDouble(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.setInt(10, coupon.getId()); 
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("updateCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "Delete from coupons where id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, couponID);
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("deleteCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		Coupon coupon = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "select * from coupons where id=?";
			statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, couponID);
			result = statement.executeQuery();
			if (result.next()) {
				coupon = new Coupon();
				coupon.setId(result.getInt("id"));
				coupon.setCompanyID(result.getInt("company_id"));
				coupon.setPrice(result.getDouble("price"));
				Category[] categories = Category.values();
				int index = result.getInt("category_id") - 1;
				coupon.setCategory(categories[index]);
				coupon.setTitle(result.getString("title"));
				coupon.setDescription(result.getString("description"));
				coupon.setImage(result.getString("image"));
				coupon.setAmount(result.getInt("amount"));
				coupon.setStart_date(result.getDate("start_date"));
				coupon.setEnd_date(result.getDate("end_date"));

				return coupon;
			} else {
				throw new CouponSystemException("getOneCoupon faild - not found");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "insert into customers_coupons values(?, ?)";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerID);
			statement.setInt(2, couponID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public void deletCouponPurchase(int couponID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "Delete from customers_coupons where coupon_id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, couponID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("getOneCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public void deletCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "Delete from customers_coupons, where customer_id=? AND coupon_id=?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, couponID);
			statement.setInt(2, customerID);

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("getOneCoupon faild", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
	}

	@Override
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		List<Coupon> list = new ArrayList<>();
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "select * from coupons";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
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
			throw new CouponSystemException("getAllCompanies failed", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	

	@Override
	public void deletCouponPurchaseOfCompany(int companyID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "delete from customers_coupons where coupon_id in (select id from coupons where company_id = ?)";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, companyID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deletCouponPurchaseOfCompany failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

	}

	@Override
	public void deletCompanyCoupons(int companyID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String sqlStatement = "delete from coupons where company_id = ?";
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, companyID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deletCompanyCoupons failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}

		
	}

	@Override
	public void deleteExpiredCoupons() throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			// 1. delete all purchases of the expired coupons
			String sqlStatement = "delete from customers_coupons where coupon_id in (select id from coupons where end_date < ? )";		
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setDate(1, new Date(System.currentTimeMillis()));
			statement.executeUpdate();
			// 2. delete all expired coupons
			sqlStatement = "delete from coupons where end_date < ? ";		
			statement = connection.prepareStatement(sqlStatement);
			statement.setDate(1, new Date(System.currentTimeMillis()));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("deleteExpiredCoupons failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
		
	}

	@Override
	public void deletCouponPurchaseOfCustomer(int customerID) throws CouponSystemException {
		Connection connection = null;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			// 1. delete all purchases of the customers
			String sqlStatement = "delete from customers_coupons where customer_id=?";		
			PreparedStatement statement = connection.prepareStatement(sqlStatement);
			statement.setInt(1, customerID);
			statement.executeUpdate();		
		} catch (SQLException e) {
			throw new CouponSystemException("deletCouponPurchaseOfCustomer failed", e);
		} finally {
			if (connection != null) {
				ConnectionPool.getInstance().returnConnection(connection);
			}
		}
		
	}
}
