package com.iyuexian.wechat4j.webweixin.dao;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.iyuexian.wechat4j.config.Constant;

public class BaseDao {
	protected static final Sql2o sql2o;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url=  Constant.configReader.get("db.url");
		String username=Constant.configReader.get("db.username");
		String pass=Constant.configReader.get("db.pass");
		sql2o = new Sql2o(url, username, pass);
	}
	private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();

	public Connection beginTrans() {

		try {
			Connection conn = connections.get();
			if (conn != null) {
				return conn;
			}
			connections.set(sql2o.beginTransaction());
			return connections.get();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}

	public void update(Sql2oCallback back) {
		Connection conn = beginTrans();
		back.execute(conn);
	}

	public void commint() {
		Connection conn = null;
		try {
			conn = connections.get();
			conn.commit();
			connections.remove();
		} catch (Exception ex) {
			conn.rollback();
		}

	}

	public Object query(Sql2oCallback callback) {
		try (Connection con = sql2o.open()) {
			return callback.execute(con);
		}
	}

	public interface Sql2oCallback {
		public Object execute(Connection conn);
	}
}