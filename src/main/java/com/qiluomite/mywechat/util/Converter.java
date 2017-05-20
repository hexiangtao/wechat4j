package com.qiluomite.mywechat.util;

public interface Converter<S, T> {
	public T convert(S s);

}
