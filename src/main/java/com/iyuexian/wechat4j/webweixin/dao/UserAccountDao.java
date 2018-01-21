package com.iyuexian.wechat4j.webweixin.dao;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.iyuexian.wechat4j.webweixin.entity.UserAccount;

public class UserAccountDao extends BaseDao {

	public void insert(final UserAccount account) {

		final String sql = SqlBuilder.withTable(Table.userAccount.toString())
				.insert("mobile", "pwd", "status", "email", "emailPwd", "type", "createDate", "refreshDate").build();
		this.update(new Sql2oCallback() {
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql).addParameter("mobile", account.getMobile());
				query.addParameter("pwd", account.getPwd());
				query.addParameter("email", account.getEmail());
				query.addParameter("emailPwd", account.getEmailpwd());
				query.addParameter("status", account.getStatus());
				query.addParameter("type", account.getType());
				query.addParameter("createDate", account.getCreateDate());
				query.addParameter("refreshDate", account.getCreateDate());
				return query.executeUpdate().getResult();
			}
		});

	}

	public void updateLoginData(final String mobile, final String loginData) {

		final String sql = " UPDATE  " + Table.userAccount + "  SET  loginData=:loginData WHERE  mobile=:mobile";
		this.update(new Sql2oCallback() {
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql).addParameter("mobile", mobile);
				query.addParameter("loginData", loginData);
				query.addParameter("mobile", mobile);
				return query.executeUpdate().getResult();
			}
		});

	}

	public void setEmail(final String email, final String emailPwd, final String mobile) {
		final String sql = " UPDATE  " + Table.userAccount + "  SET  emial=:email,emailPwd=:emailPwd WHERE  mobile=:mobile";
		this.update(new Sql2oCallback() {
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql).addParameter("email", email);
				query.addParameter("emailPwd", emailPwd);
				query.addParameter("mobile", mobile);
				return query.executeUpdate().getResult();
			}
		});

	}

	@SuppressWarnings("unchecked")
	public List<UserAccount> list(int page, int pagesize) {
		int begin = (page - 1) * pagesize, end = page * pagesize;
		final String sql = " SELECT  id,mobile,pwd,email,emailPwd,status,type,loginData,createDate FROM  " + Table.userAccount + " LIMIT " + begin
				+ "," + end;
		return  (List<UserAccount>) this.query(new Sql2oCallback() {
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql);
				List<UserAccount> list = query.executeAndFetch(UserAccount.class);
				return list;
			}
		});
	}

	public UserAccount selectOne(final String mobile, final String pwd) {
		String sql = " SELECT  id,mobile,pwd,email,emailPwd,status,type,createDate FROM  " + Table.userAccount + " WHERE  mobile=:mobile ";
		if (pwd != null && pwd.trim().length() > 0) {
			sql += " AND  pwd=:pwd";
		}
		sql += " LIMIT 0,1";
		final String sql2 = sql;
		return (UserAccount) this.query(new Sql2oCallback() {
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql2).addParameter("mobile", mobile);
				if (pwd != null && pwd.trim().length() > 0) {
					query.addParameter("pwd", pwd);
				}
				return query.executeAndFetchFirst(UserAccount.class);
			}
		});
	}

}