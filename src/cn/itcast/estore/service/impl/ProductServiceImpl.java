package cn.itcast.estore.service.impl;

import java.util.List;

import cn.itcast.estore.dao.ProductDao;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.service.ProductService;
import cn.itcast.estore.utils.FactoryUtils;

public class ProductServiceImpl implements ProductService {
	ProductDao productDao = FactoryUtils.getInstance(ProductDao.class);

	@Override
	public List<Product> findAll() {
		return productDao.findAll();
	}

	@Override
	public Product findProductDatail(String pid) {
		// TODO Auto-generated method stub
		return productDao.findProductDatail(pid);
	}

	@Override
	// 生成订单表时，更新库存
	public void updateNum(OrderItem o) {
		// 业务上先从数据库中查询到商品数量，比对，如果购买数量超过库存量，返回
		// 由于加了事物管理，不使用之前现成的方法
		Product product = productDao.findProductDatailByPidTransaction(o
				.getPid());
		if (product.getPnum() < o.getBuynum()) {
			throw new RuntimeException("您购买数量超出库存，请重新输入购买数量");
		}
		productDao.updateNumByTransaction(o);

	}

}
