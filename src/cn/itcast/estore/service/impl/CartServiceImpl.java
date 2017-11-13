package cn.itcast.estore.service.impl;

import java.util.List;

import cn.itcast.estore.dao.CartDao;
import cn.itcast.estore.domain.Cart;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.CartService;
import cn.itcast.estore.utils.FactoryUtils;

public class CartServiceImpl implements CartService {
	CartDao cartDao = FactoryUtils.getInstance(CartDao.class);

	@Override
	public void buyToCart(User existUser, int buynum, String pid) {
		Cart cart = cartDao.findCartById(existUser.getId(), pid);

		System.out.println(existUser.getId());
		if (cart == null) {
			// cart为空之后重新new一个，不然报空指针
			cart = new Cart();
			cart.setUid(existUser.getId());
			cart.setPid(pid);
			cart.setBuynum(buynum);
			cartDao.addToCart(cart);
		} else {
			int oldnum = cart.getBuynum();
			int newnum = oldnum + buynum;
			cart.setBuynum(newnum);
			cartDao.updateToCart(cart);

		}

	}

	@Override
	public List<Product> findCartInfoByUid(int uid) {
		// TODO Auto-generated method stub
		return cartDao.findCartInfoByUid(uid);
	}

	@Override
	public List<Product> changeCartNum(int uid, int buynum, String pid) {
		Cart cart = new Cart();
		cart.setUid(uid);
		cart.setBuynum(buynum);
		cart.setPid(pid);
		cartDao.updateToCart(cart);
		return cartDao.findCartInfoByUid(uid);
	}

}
