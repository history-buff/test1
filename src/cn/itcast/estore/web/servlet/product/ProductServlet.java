package cn.itcast.estore.web.servlet.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Product;
import cn.itcast.estore.service.ProductService;
import cn.itcast.estore.utils.FactoryUtils;
import cn.itcast.estore.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ProductService productService = FactoryUtils
				.getInstance(ProductService.class);
		List<Product> products = productService.findAll();
		request.setAttribute("products", products);
		// System.out.println("shishi");
		return "/goods.jsp";

	}

	public String findProductDatail(HttpServletRequest request,// 点击查询商品详细信息
			HttpServletResponse response) throws ServletException, IOException {
		ProductService productService = FactoryUtils
				.getInstance(ProductService.class);
		String pid = request.getParameter("pid");
		Product product = productService.findProductDatail(pid);
		request.setAttribute("product", product);
		return "/goods_detail.jsp";

	}

}
