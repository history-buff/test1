package cn.itcast.estore.service;

import java.util.List;

import cn.itcast.estore.domain.China;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.Product;

public interface OrderService {

	List<China> loadCity(int pid);

	List<Product> findAll(int id);

	void save(Order order);

	List<Order> findMyOrderInfo(int id);

	Order findDetailsOfOrder(String oid);

}
