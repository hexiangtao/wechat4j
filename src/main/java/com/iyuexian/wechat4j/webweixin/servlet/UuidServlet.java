package com.iyuexian.wechat4j.webweixin.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iyuexian.wechat4j.webweixin.WechatApiUtils;
import com.iyuexian.wechat4j.webweixin.entity.RestResponse;

@WebServlet("/uuid")
public class UuidServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uuid = WechatApiUtils.getUUID();
		RestResponse restResponse = RestResponse.success(uuid);
		renderJson(response, restResponse);
	}

}
