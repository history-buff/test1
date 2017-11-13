package cn.itcast.estore.dao;

import java.util.List;

import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.Product;

public interface ProductDao {

	List<Product> findAll();

	Product findProductDatail(String pid);

	Product findProductDatailByPidTransaction(String pid);

	void updateNumByTransaction(OrderItem o);

}
