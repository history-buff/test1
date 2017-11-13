package cn.itcast.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.dao.OrderDao;
import cn.itcast.estore.domain.China;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.utils.JDBCUtils;
import cn.itcast.estore.utils.TransactionManager;

public class OrderDaoImpl implements OrderDao {

	@Override
	public List<China> loadCity(int pid) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from china where pid=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<China>(
					China.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findAll(int uid) {
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
	// 订单提交时生成订单数据，写入订单表中
	public void createOrder(Order order) {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,null,?,?,?)";
		try {
			queryRunner.update(TransactionManager.getCurrentConnection(), sql,
					order.getId(), order.getUid(), order.getTotalprice(),
					order.getAddress(), order.getStatus(), order.getPostcode(),
					order.getAcceptperson(), order.getTelephone());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Order> findMyOrderInfo(int uid) {
		try {
			String sql = "select * from orders where uid=? ";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			return runner.query(sql, new BeanListHandler<Order>(Order.class),
					uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	// 查看订单详情
	public Order findDetailsOfOrder(String oid) {
		try {
			String sql = "select * from orders where id=? ";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			return runner.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
