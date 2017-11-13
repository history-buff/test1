package cn.itcast.estore.web.listner;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.UserService;
import cn.itcast.estore.utils.FactoryUtils;

public class ActiveUserListner implements ServletContextListener {

	@Override
	// 使用监听器、任务调度来循环监控失效数据，
	public void contextInitialized(ServletContextEvent sce) {
		final UserService userService = FactoryUtils
				.getInstance(UserService.class);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("扫描一次");
				List<User> users = userService.findNoActiveUser();// 找到未激活状态的数据
				for (User user : users) {
					long registerTime = user.getRegistertime().getTime();
					long currentTime = System.currentTimeMillis();
					if (currentTime - registerTime > 1000 * 3600 * 2) {
						userService.delUserByActivecode(user.getActivecode());
					}

				}

			}
		}, 1000 * 3600 * 200, 1000 * 3600 * 200);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
