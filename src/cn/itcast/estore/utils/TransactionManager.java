package cn.itcast.estore.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
	// 集中管理事务代码 事务开启 回滚 提交 conn获取
	static ThreadLocal<Connection> local = new ThreadLocal<Connection>();

	public static Connection getCurrentConnection() {
		Connection con = local.get();// 当前线程中获取con
		if (con == null) {
			con = JDBCUtils.getConnection();
			local.set(con);// 绑定到线程中
		}
		return con;
	}

	// 事务管理代码 开启事务
	public static void begin() {
		Connection con = getCurrentConnection();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback() {
		Connection con = getCurrentConnection();
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit() {
		Connection con = getCurrentConnection();
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 一定 将对象从线程中移除
			local.remove();
		}
	}

}
