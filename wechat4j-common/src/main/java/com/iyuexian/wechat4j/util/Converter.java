package com.iyuexian.wechat4j.util;

public interface Converter<S, T> {
	public T convert(S s);

}
