package com.iyuexian.wechat4j.util;

public class GenericConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String s) {
		return Integer.parseInt(s);
	}

	public static void main(String[] args) {
		Converter<String, Integer> converter = new GenericConverter();
		converter.convert("3");
	}

}
