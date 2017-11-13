package cn.itcast.estore.web.servlet.order;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.CartService;
import cn.itcast.estore.service.OrderService;
import cn.itcast.estore.utils.FactoryUtils;
import cn.itcast.estore.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	// 获取日志记录器对象
	private static final Logger logger = Logger.getLogger(OrderServlet.class);

	// 购物车页面去结算跳转时查询所有购物车信息显示在订单页面栏
	public String orderList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User existUser = (User) request.getSession().getAttribute("existUser");
		if (existUser == null) {
			return "/tpslogin.jsp";
		}
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		List<Product> list = orderService.findAll(existUser.getId());
		request.setAttribute("products", list);
		if (list == null) {
			return "/index.jsp";
		}

		return "/orders_submit.jsp";
		// TODO Auto-generated method stub
	}

	/*
	 * private String id;// uuid生成主键 private int uid; private double totalprice;
	 * private String address; private int status = 0;// 0 未支付 1 已经支付 private
	 * Timestamp createtime;
	 * 
	 * private String acceptperson;// 收货人名称 private String telephone;// 收货人电话
	 * 
	 * private List<OrderItem> orderItems;// 封装该订单对应的商品信息
	 */
	// 提交订单，生成订单，更新库存，删除购物车信息
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String generateOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User existUser = (User) request.getSession().getAttribute("existUser");
		// 使用动态代理完成save,在其中增加日志，记录方法执行时间
		if (existUser == null) {
			return "/tpslogin.jsp";
		}
		Order order = new Order();
		order.setUid(existUser.getId());

		String order_id = UUID.randomUUID().toString();
		// 封装所有数据到order中
		order.setId(order_id);// 生成订单编号
		order.setTotalprice(Double.parseDouble(request
				.getParameter("totalprice")));
		order.setAddress(request.getParameter("ssq")
				+ request.getParameter("detailAddress"));
		order.setAcceptperson(request.getParameter("acceptPerson"));
		order.setTelephone(request.getParameter("phone"));
		order.setPostcode(request.getParameter("postcode"));
		CartService cartService = FactoryUtils.getInstance(CartService.class);
		List<Product> products = cartService.findCartInfoByUid(existUser
				.getId());// 查询购物车，找到购物车中所有选定的物品
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		if (products != null && products.size() != 0) {
			for (Product product : products) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOid(order_id);
				orderItem.setPid(product.getPid());
				orderItem.setBuynum(product.getBuynum());
				orderItems.add(orderItem);
			}
			order.setOrderItems(orderItems);
		}

		final OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);

		OrderService proxyOrderService = (OrderService) Proxy.newProxyInstance(
				orderService.getClass().getClassLoader(), orderService
						.getClass().getInterfaces(), new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						if ("save".equals(method.getName())) {
							long starttime = System.currentTimeMillis();
							Object obj = method.invoke(orderService, args);
							long endtime = System.currentTimeMillis();
							logger.info("订单生成执行，耗时" + (endtime - starttime));
							return obj;

						}
						return method.invoke(orderService, args);
					}
				});
		proxyOrderService.save(order);// 完成订单生成，物品数量更新等操作
		return "/index.jsp";
	}

	// 点击“我的订单”--》查看个人订单列表
	public String findMyOrderInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User existUser = (User) request.getSession().getAttribute("existUser");
		if (existUser == null) {
			return "/tpslogin.jsp";
		}
		logger.info("点击查询'我的订单'，开始时间" + System.currentTimeMillis());
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		List<Order> list = orderService.findMyOrderInfo(existUser.getId());
		request.setAttribute("orders", list);
		if (list == null) {
			return "/index.jsp";
		}
		logger.error("查询完成，查询结束时间" + System.currentTimeMillis());

		return "/orders.jsp";
		// TODO Auto-generated method stub
	}

	// 查看订单详情，将商品名称、价格封装到orderitem中去
	public String findDetailsOfOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");

		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		Order orderDetail = orderService.findDetailsOfOrder(oid);
		request.setAttribute("orderDetail", orderDetail);
		return "/orders_detail.jsp";
		// TODO Auto-generated method stub
	}

}
