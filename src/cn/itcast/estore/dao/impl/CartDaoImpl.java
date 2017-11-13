package cn.itcast.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.dao.CartDao;
import cn.itcast.estore.domain.Cart;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.utils.JDBCUtils;
import cn.itcast.estore.utils.TransactionManager;

public class CartDaoImpl implements CartDao {

	@Override
	public Cart findCartById(int uid, String pid) {
		try {
			String sql = "select * from cart where uid = ? and pid = ?";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			return runner.query(sql, new BeanHandler<Cart>(Cart.class), uid,
					pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void addToCart(Cart cart) {
		// 用户第一次购买
		try {
			String sql = "insert into cart values(?,?,?)";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			runner.update(sql, cart.getUid(), cart.getPid(), cart.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateToCart(Cart cart) {
		// 修改用户购买的商品数量
		try {
			String sql = "update cart set buynum =? where uid = ? and pid = ?";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			runner.update(sql, cart.getBuynum(), cart.getUid(), cart.getPid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Product> findCartInfoByUid(int uid) {
		try {
			String sql = "select p.*,c.buynum from cart as c,product as p where c.uid = ? and p.pid =c.pid ";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			return runner.query(sql,
					new BeanListHandler<Product>(Product.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	// 生成订单时清空登录账户的购物车数据
	public void clearCartByUserId(int uid) {

		try {
			QueryRunner queryRunner = new QueryRunner();
			String sql = "delete from cart where uid=?";
			queryRunner.update(TransactionManager.getCurrentConnection(), sql,
					uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
