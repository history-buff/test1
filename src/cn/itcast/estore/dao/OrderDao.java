package cn.itcast.estore.dao;

import java.util.List;

import cn.itcast.estore.domain.China;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.Product;

public interface OrderDao {

	List<China> loadCity(int pid);

	List<Product> findAll(int uid);

	void createOrder(Order order);

	List<Order> findMyOrderInfo(int uid);

	Order findDetailsOfOrder(String oid);

}
