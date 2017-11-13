package cn.itcast.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.dao.UserDao;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User checkEmail(String email) {
		try {
			QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from users where email=?";
			return queryRunner.query(sql, new BeanHandler<User>(User.class),
					email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void register(User user) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into users values(null,?,?,?,?,?,null,?)";
		try {
			queryRunner.update(sql, user.getNickname(), user.getEmail(),
					user.getPassword(), user.getActivecode(), user.getStatus(),
					user.getRole());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public User findUserByActivecode(String activecode) {
		try {
			QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from users where activecode=?";
			return queryRunner.query(sql, new BeanHandler<User>(User.class),
					activecode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void activeUser(String activecode) {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update users set status=1 where activecode=?";
		try {
			queryRunner.update(sql, activecode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delUserByActivecode(String activecode) {// 超过两小时未激活，清除数据库数据
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "delete from users where activecode=?";
		try {
			queryRunner.update(sql, activecode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public User login(User user) {
		try {
			QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from users where email=? and password=?";
			return queryRunner.query(sql, new BeanHandler<User>(User.class),
					user.getEmail(), user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> findNoActiveUser() {// 监听器中查询到废弃数据的方法
		try {
			QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
			String sql = "select * from users where status=0";
			return queryRunner
					.query(sql, new BeanListHandler<User>(User.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
