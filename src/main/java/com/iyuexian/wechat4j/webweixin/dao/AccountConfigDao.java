package com.iyuexian.wechat4j.webweixin.dao;

import java.util.Date;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.iyuexian.wechat4j.webweixin.entity.AccountConfig;
import com.iyuexian.wechat4j.webweixin.entity.Cache;
import com.iyuexian.wechat4j.webweixin.entity.CacheItem;
import com.iyuexian.wechat4j.webweixin.util.StrUtils;

public class AccountConfigDao extends BaseDao {
	private static Cache cache = new Cache();
	static final int persistSeconds = 60;

	public String getKey(String mobile, String name) {
		return mobile + "&" + name;
	}

	public void insert(final String mobile, final String name, final String value) {
		final String sql = SqlBuilder.withTable(Table.accountConfig.toString()).insert("mobile", "name", "val", "refreshDate").build();
		this.update(new Sql2oCallback() {

			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql).addParameter("mobile", mobile);
				query.addParameter("name", name);
				query.addParameter("val", value);
				query.addParameter("refreshDate", new Date());
				CacheItem cacheItem = new CacheItem(getKey(mobile, name), value, System.currentTimeMillis(), persistSeconds);
				cache.put(cacheItem.getKey(), cacheItem);
				return query.executeUpdate().getResult();
			}
		});

	}

	public void update(final String mobile, final String name, final String val) {

		String oldVal = selectOne(mobile, name);
		if (StrUtils.isBlank(oldVal)) {
			insert(mobile, name, val);
			return;
		}
		String sql = " UPDATE  " + Table.accountConfig + "  SET  val=:val WHERE  mobile=:mobile AND name=:name ";
		final String sql2 = sql;
		this.update(new Sql2oCallback() {
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql2).addParameter("mobile", mobile);
				query.addParameter("name", name);
				query.addParameter("val", val);
				CacheItem cacheItem = new CacheItem(getKey(mobile, name), val, System.currentTimeMillis(), persistSeconds);
				cache.put(cacheItem.getKey(), cacheItem);
				return query.executeUpdate().getResult();
			}
		});

	}

	public void on(String mobile, String name) {
		update(mobile, name, AccountConfig.ON);
	}

	public void off(String mobile, String name) {
		update(mobile, name, AccountConfig.OFF);
	}

	public String selectOne(final String mobile, final String name) {
		CacheItem cacheItem = cache.get(getKey(mobile, name));
		if (cacheItem != null) {
			return cacheItem.getValue();
		}

		String sql = " SELECT  id,mobile,name,val FROM  " + Table.accountConfig + " WHERE  mobile=:mobile ORDER BY id DESC LIMIT 0,1  ";
		final String sql2 = sql;

		return (String) this.query(new Sql2oCallback() {

			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql2).addParameter("mobile", mobile);
				AccountConfig config = query.executeAndFetchFirst(AccountConfig.class);
				if (config != null) {
					CacheItem item = new CacheItem(getKey(mobile, name), config.getVal(), System.currentTimeMillis(), persistSeconds);
					cache.put(item.getKey(), item);
				}
				return config != null ? config.getVal() : "";
			}
		});

	}

}
