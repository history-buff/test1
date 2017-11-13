package cn.itcast.estore.web.servlet.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.CartService;
import cn.itcast.estore.utils.FactoryUtils;
import cn.itcast.estore.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// 商品详情中添加到购物车
	public String buyToCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int buynum = Integer.parseInt(request.getParameter("buynum"));
		String pid = request.getParameter("pid");
		User existUser = (User) request.getSession().getAttribute("existUser");
		// 业务逻辑，添加到购物车时，若尚未登录，跳转到登录页面
		if (existUser == null) {
			return "/login.jsp";
		}
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		cartService.buyToCart(existUser, buynum, pid);
		return "/tpsbuyorcart.jsp";

	}

	// 购物车中点击去购物车结算时，查询所有购物车中商品显示
	public String findAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User existUser = (User) request.getSession().getAttribute("existUser");
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		List<Product> list = cartService.findCartInfoByUid(existUser.getId());
		if (list == null) {
			return "/index.jsp";
		}
		request.setAttribute("cart", list);
		return "/cart.jsp";

	}

	// ajax实现购物车物品数量修改相应数据及时响应
	public String changeCartNum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User existUser = (User) request.getSession().getAttribute("existUser");
		int buynum = Integer.parseInt(request.getParameter("buynum"));
		String pid = request.getParameter("pid");
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		List<Product> list = cartService.changeCartNum(existUser.getId(),
				buynum, pid);
		double xiaoji = 0.0;
		double totalshop = 0.0;
		double totalmarket = 0.0;
		double totalsave = 0.0;
		if (list != null & list.size() != 0) {

			for (Product product : list) {
				if (product.getPid().equals(pid)) {
					xiaoji = product.getShop_price() * product.getBuynum();
				}
				totalshop = totalshop + product.getShop_price()
						* product.getBuynum();
				totalmarket = totalmarket + product.getMarket_price()
						* product.getBuynum();

			}
			totalsave = totalmarket - totalshop;
			response.getWriter().print(
					xiaoji + "-" + totalshop + "-" + totalsave);
			System.out.println(xiaoji);
		}
		return null;

	}

}
