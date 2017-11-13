package cn.itcast.estore.service.impl;

import java.util.List;

import cn.itcast.estore.dao.CartDao;
import cn.itcast.estore.dao.OrderDao;
import cn.itcast.estore.dao.OrderItemDao;
import cn.itcast.estore.domain.China;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.service.OrderService;
import cn.itcast.estore.service.ProductService;
import cn.itcast.estore.utils.FactoryUtils;
import cn.itcast.estore.utils.TransactionManager;

public class OrderServiceImpl implements OrderService {
	OrderDao orderDao = FactoryUtils.getInstance(OrderDao.class);
	OrderItemDao orderItemDao = FactoryUtils.getInstance(OrderItemDao.class);
	ProductService productService = FactoryUtils
			.getInstance(ProductService.class);
	CartDao cartDao = FactoryUtils.getInstance(CartDao.class);

	@Override
	public List<China> loadCity(int pid) {
		// TODO Auto-generated method stub
		return orderDao.loadCity(pid);
	}

	@Override
	public List<Product> findAll(int uid) {
		// TODO Auto-generated method stub
		return orderDao.findAll(uid);
	}

	@Override
	// 完成订单表插入，商品表更新，中间表生成，购物车清空操作
	public void save(Order order) {

		try {
			TransactionManager.begin();
			orderDao.createOrder(order);// 在订单表中生成订单数据
			// 同步进行更新商品数量
			List<OrderItem> orderItems = order.getOrderItems();
			if (orderItems != null && orderItems.size() != 0) {
				for (OrderItem o : orderItems) {
					// 循环减少商品数量
					productService.updateNum(o);// 修改，在productService做判断后再进行更新操作
					// 循环插入orderItem表中数据
					orderItemDao.save(o);
				}
			}
			// 清除购物车数据
			cartDao.clearCartByUserId(order.getUid());

		} catch (Exception e) {
			TransactionManager.rollback();
			throw new RuntimeException(e);
		} finally {
			TransactionManager.commit();
		}

	}

	@Override
	// 我的订单查询
	public List<Order> findMyOrderInfo(int uid) {
		// TODO Auto-generated method stub
		return orderDao.findMyOrderInfo(uid);
	}

	@Override
	// 订单详情查询
	public Order findDetailsOfOrder(String oid) {
		Order order = orderDao.findDetailsOfOrder(oid);
		List<OrderItem> list = orderItemDao.findDetailsOfOrder(oid);
		order.setOrderItems(list);
		return order;
	}

}
