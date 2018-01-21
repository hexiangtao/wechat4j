package com.iyuexian.wechat4j.webweixin.dao;

public class SqlBuilder {

	private StringBuilder container;

	private String tableName;

	public static final int INSERT = 1;
	public static final int DELETE = 2;
	public static final int UPDATE = 3;
	public static final int SELECT = 4;

	private SqlBuilder() {
	}

	public static SqlBuilder withTable(String tableName) {
		SqlBuilder b = new SqlBuilder();
		b.container = new StringBuilder();
		b.tableName = tableName;
		return b;

	}

	public SqlBuilder insert(String... columns) {

		if (columns == null || columns.length == 0) {
			throw new IllegalArgumentException("columns must not be empty");
		}
		if (tableName == null || tableName.trim().length() == 0) {

			throw new IllegalArgumentException("tableName must not be empty");
		}

		StringBuilder values = new StringBuilder();
		container.append(" INSERT INTO " + tableName + "(");
		for (String column : columns) {
			container.append(column + ",");
			values.append(":" + column + ",");
		}

		values = values.deleteCharAt(values.length() - 1);
		container.deleteCharAt(container.length() - 1).append(") VALUES(");
		container.append(values + ")");

		return this;
	}

	public String build() {
		return container.toString();
	}

	@Override
	public String toString() {
		return build();
	}

}