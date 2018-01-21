package com.iyuexian.wechat4j.webweixin.dao;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.iyuexian.wechat4j.webweixin.entity.Message;

public class MessageDao extends BaseDao {

	public void insert(final Message msg) {
		final String sql = SqlBuilder.withTable(Table.message.toString()).insert("mobile", "content", "type", "remark", "createDate").build();
		
		 this.update(new Sql2oCallback() {
			
			@Override
			public Object execute(Connection conn) {
				Query query = conn.createQuery(sql).addParameter("mobile", msg.getMobile());
				query.addParameter("content", msg.getContent());
				query.addParameter("type", msg.getType());
				query.addParameter("remark", msg.getRemark());
				query.addParameter("createDate", msg.getCreateDate());
				return query.executeUpdate().getResult();
			}
		});


	}
}
