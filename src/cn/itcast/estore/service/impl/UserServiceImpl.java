package cn.itcast.estore.service.impl;

import java.util.List;

import cn.itcast.estore.dao.UserDao;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.service.UserService;
import cn.itcast.estore.utils.FactoryUtils;

public class UserServiceImpl implements UserService {
	private UserDao userDao = FactoryUtils.getInstance(UserDao.class);

	@Override
	public User checkEmail(String email) {
		return userDao.checkEmail(email);
	}

	@Override
	public void register(User user) {
		userDao.register(user);

	}

	@Override
	public User findUserByActivecode(String activecode) {
		return userDao.findUserByActivecode(activecode);
	}

	@Override
	public void activeUser(String activecode) {// 用户激活
		userDao.activeUser(activecode);

	}

	@Override
	public void delUserByActivecode(String activecode) {// 激活时间超过两小时，清除数据库数据
		userDao.delUserByActivecode(activecode);

	}

	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public List<User> findNoActiveUser() {
		// TODO Auto-generated method stub
		return userDao.findNoActiveUser();
	}

}
