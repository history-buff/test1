package cn.itcast.estore.service;

import java.util.List;

import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.Product;

public interface ProductService {

	List<Product> findAll();

	Product findProductDatail(String pid);

	void updateNum(OrderItem o);

}
