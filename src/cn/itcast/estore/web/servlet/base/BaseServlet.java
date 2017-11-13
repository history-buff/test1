package cn.itcast.estore.web.servlet.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String methodName = request.getParameter("method");
		if (methodName == null) {
			request.getRequestDispatcher("/index.jsp").forward(request,
					response);
		} else {
			try {
				Method method = this.getClass().getMethod(methodName,
						HttpServletRequest.class, HttpServletResponse.class);
				String result = (String) method.invoke(this, request, response);
				if (result != null) {
					if (result.startsWith("/tps")) {
						result = result.substring(4);
						response.sendRedirect(request.getContextPath() + "/"
								+ result);
					} else {
						request.getRequestDispatcher(result).forward(request,
								response);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
