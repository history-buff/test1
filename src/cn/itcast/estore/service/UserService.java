package cn.itcast.estore.service;

import java.util.List;

import cn.itcast.estore.domain.User;

public interface UserService {

	User checkEmail(String email);

	void register(User user);

	User findUserByActivecode(String activecode);

	void activeUser(String activecode);

	void delUserByActivecode(String activecode);

	User login(User user);

	List<User> findNoActiveUser();

}
