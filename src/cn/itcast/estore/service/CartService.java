package cn.itcast.estore.service;

import java.util.List;

import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;

public interface CartService {

	void buyToCart(User existUser, int buynum, String pid);

	List<Product> findCartInfoByUid(int id);

	List<Product> changeCartNum(int id, int buynum, String pid);

}
