package com.iyuexian.wechat4j.webweixin.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iyuexian.wechat4j.webweixin.entity.RestResponse;
import com.iyuexian.wechat4j.webweixin.service.UserAccountService;
import com.iyuexian.wechat4j.webweixin.util.StrUtils;

@WebServlet("/account")
public class AccountServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private UserAccountService UserAccountService = new UserAccountService();
	public static final String actions = "login|reg";

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String action = request.getParameter("action");
		if (action == null || action.trim().length() == 0 || !actions.contains(action)) {
			RestResponse restResponse = RestResponse.fail("action invalid");
			renderJson(response, restResponse);
			return;
		}
		if (action.equals("login")) {
			login(request, response);
			return;
		}
		if (action.equals("reg")) {
			reg(request, response);
			return;
		}

	}

	public void login(HttpServletRequest request, HttpServletResponse response) {

		String mobile = getParam(request, "mobile", "");
		String pwd = getParam(request, "pwd", "");
		if (StrUtils.isBlank(mobile) || StrUtils.isBlank(pwd)) {
			RestResponse restResponse = RestResponse.fail("手机号或密码不能为空");
			renderJson(response, restResponse);
			return;
		}
		RestResponse restResponse = UserAccountService.login(mobile, pwd);
		if (restResponse.isOk()) {
			request.getSession().setAttribute("mobile", mobile);
		}
		renderJson(response, restResponse);

	}

	public void reg(HttpServletRequest request, HttpServletResponse response) {

		String mobile = getParam(request, "mobile", "");
		String pwd = getParam(request, "pwd", "");
		if (StrUtils.isBlank(mobile) || StrUtils.isBlank(pwd)) {
			RestResponse restResponse = RestResponse.fail("手机号或密码不能为空");
			renderJson(response, restResponse);
			return;
		}
		RestResponse restResponse = UserAccountService.reg(mobile, pwd);
		renderJson(response, restResponse);

	}

}
