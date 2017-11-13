package cn.itcast.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.dao.OrderItemDao;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.utils.JDBCUtils;
import cn.itcast.estore.utils.TransactionManager;

public class OrderItemDaoImpl implements OrderItemDao {

	@Override
	// 生成订单时，完成中间表的插入，要有事物管理
	public void save(OrderItem o) {

		try {
			QueryRunner queryRunner = new QueryRunner();
			String sql = "insert into orderitems values(?,?,?)";
			queryRunner.update(TransactionManager.getCurrentConnection(), sql,
					o.getOid(), o.getPid(), o.getBuynum());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	// 查看订单详情，将商品名称、市价、货柜价封装到orderitem中
	public List<OrderItem> findDetailsOfOrder(String oid) {

		try {
			QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select p.*,o.buynum from orderitems as o,product as p where o.pid=p.pid and o.oid=?";
			return queryRunner.query(sql, new BeanListHandler<OrderItem>(
					OrderItem.class), oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
