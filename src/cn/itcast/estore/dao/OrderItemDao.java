package cn.itcast.estore.dao;

import java.util.List;

import cn.itcast.estore.domain.OrderItem;

public interface OrderItemDao {

	void save(OrderItem o);

	List<OrderItem> findDetailsOfOrder(String oid);

}
