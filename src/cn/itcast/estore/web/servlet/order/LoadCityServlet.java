package cn.itcast.estore.web.servlet.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.China;
import cn.itcast.estore.service.OrderService;
import cn.itcast.estore.utils.FactoryUtils;
import cn.itcast.estore.web.servlet.base.BaseServlet;
import flexjson.JSONSerializer;

/**
 * Servlet implementation class LoadCityServlet
 */
public class LoadCityServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String loadCity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int pid = Integer.parseInt(request.getParameter("pid"));
		OrderService orderService = FactoryUtils
				.getInstance(OrderService.class);
		List<China> list = orderService.loadCity(pid);
		String serialize = new JSONSerializer().serialize(list);
		response.setContentType("text/json;charset=utf-8");
		// System.out.println(serialize);
		response.getWriter().print(serialize);
		return null;
		// TODO Auto-generated method stub
	}

}
