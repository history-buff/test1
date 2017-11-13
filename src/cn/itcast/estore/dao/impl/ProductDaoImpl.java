package cn.itcast.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.dao.ProductDao;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.utils.JDBCUtils;
import cn.itcast.estore.utils.TransactionManager;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findAll() {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product";
		try {
			return queryRunner.query(sql, new BeanListHandler<Product>(
					Product.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Product findProductDatail(String pid) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where pid=?";
		try {
			return queryRunner.query(sql, new BeanHandler<Product>(
					Product.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	// 生成订单修改数据库时先查询
	public Product findProductDatailByPidTransaction(String pid) {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "select * from product where pid=?";
		try {
			return queryRunner.query(TransactionManager.getCurrentConnection(),
					sql, new BeanHandler<Product>(Product.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	// 生成订单时修改库存，需要事物管理
	public void updateNumByTransaction(OrderItem o) {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "update product set pnum=(pnum-?) where pid=?";
		try {
			queryRunner.update(TransactionManager.getCurrentConnection(), sql,
					o.getBuynum(), o.getPid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
