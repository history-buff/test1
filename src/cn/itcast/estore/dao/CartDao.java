package cn.itcast.estore.dao;

import java.util.List;

import cn.itcast.estore.domain.Cart;
import cn.itcast.estore.domain.Product;

public interface CartDao {

	Cart findCartById(int id, String pid);

	void addToCart(Cart cart);

	void updateToCart(Cart cart);

	List<Product> findCartInfoByUid(int uid);

	void clearCartByUserId(int uid);

}
