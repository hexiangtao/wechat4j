package com.iyuexian.wechat4j.webweixin.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iyuexian.wechat4j.webweixin.entity.RestResponse;
import com.iyuexian.wechat4j.webweixin.service.UserAccountService;
import com.iyuexian.wechat4j.webweixin.util.StrUtils;

@WebServlet("/account/setting")
public class AccountConfigServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	UserAccountService userAccountService = new UserAccountService();

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mobile = getParam(request, "mobile", "");

		String name = getParam(request, "name", "");
		String value = getParam(request, "val", "");
		if (StrUtils.isBlank(mobile) || StrUtils.isBlank(name) || StrUtils.isBlank(value)) {
			renderJson(response, RestResponse.fail("invalid param"));
			return;
		}
		RestResponse restResponse = userAccountService.updateConfig(mobile, name, value);
		renderJson(response, restResponse);

	}

}
