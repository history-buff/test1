package cn.itcast.estore.web.servlet.user;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.UserService;
import cn.itcast.estore.utils.ApacheMailUtils;
import cn.itcast.estore.utils.FactoryUtils;
import cn.itcast.estore.utils.MD5Utils;
import cn.itcast.estore.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void checkEmail(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// System.out.println("shsihi");
		String email = request.getParameter("email");
		UserService userService = FactoryUtils.getInstance(UserService.class);
		User existUser = userService.checkEmail(email);
		if (existUser == null) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");

		}
	}

	public void checkCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// System.out.println("ooo");
		String checkcode = request.getParameter("checkcode");
		String code = (String) request.getSession().getAttribute("code");
		// UserService userService =
		// FactoryUtils.getInstance(UserService.class);
		// User existUser = userService.checkEmail(email);
		if (checkcode.equals(code)) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");
		}
	}

	public String register(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			String activecode = UUID.randomUUID().toString().replace("-", "");
			user.setActivecode(activecode);
			String password = MD5Utils.md5(user.getPassword());
			user.setPassword(password);
			UserService userService = FactoryUtils
					.getInstance(UserService.class);
			userService.register(user);
			ApacheMailUtils
					.sendMail(
							user.getEmail(),
							"xx网站账号密码激活通知",
							"<a href='http://localhost:8080/estore/userServlet?method=activeUser&activecode="
									+ activecode + "'>2小时内，点击此处激活激活</a>");
			return "/tpslogin.jsp";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "register.jsp";
		}
	}

	public String activeUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String activecode = request.getParameter("activecode");
		UserService userService = FactoryUtils.getInstance(UserService.class);
		User existUser = userService.findUserByActivecode(activecode);
		// 判断数据库有无数据
		if (existUser == null) {
			request.setAttribute("activecode_error", "激活失败，请重新注册");
			return "/register.jsp";
		} else {// 拿到数据，判断状态、时间
			long registerTime = existUser.getRegistertime().getTime();// 拿到注册时间，转化为毫秒表示
			long currentTime = System.currentTimeMillis();// 当前时间
			if ((currentTime - registerTime) > 1000 * 3600 * 2
					&& existUser.getStatus() == 0) {
				userService.delUserByActivecode(activecode);
				request.setAttribute("overtime_error", "激活超时，请重新注册");
				return "/register.jsp";
			} else if (existUser.getStatus() == 1) {// 已经注册，跳转到登陆页面
				return "/tpslogin.jsp";
			} else {
				userService.activeUser(activecode);
				return "/tpslogin.jsp";

			}

		}
	}

	public String userLogin(HttpServletRequest request,// 登陆验证
			HttpServletResponse response) throws IOException {
		System.out.println("ooo");
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		User user = new User();
		user.setEmail(email);
		user.setPassword(MD5Utils.md5(password));// 注册时已将密码通过MD5加密
		UserService userService = FactoryUtils.getInstance(UserService.class);
		User existUser = userService.login(user);
		if (existUser == null) {
			request.setAttribute("email_password_error", "账号密码错误，请重新登陆");
			return "/login.jsp";
		} else if (existUser.getStatus() == 0) {
			response.getWriter().print("账号尚未激活，请激活后登陆");
			return null;
		} else {
			request.getSession().setAttribute("existUser", existUser);
			// System.out.println(request.getSession().getAttribute("existUser"));
			return "/index.jsp";
		}

	}
}
