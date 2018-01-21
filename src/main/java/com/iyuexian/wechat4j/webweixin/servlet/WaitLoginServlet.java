package com.iyuexian.wechat4j.webweixin.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iyuexian.wechat4j.util.Matchers;
import com.iyuexian.wechat4j.webweixin.WechatApiUtils;
import com.iyuexian.wechat4j.webweixin.entity.RestResponse;
import com.iyuexian.wechat4j.webweixin.service.UserAccountService;

@WebServlet("/waitlogin")
public class WaitLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = LoggerFactory.getLogger(WaitLoginServlet.class);
	private UserAccountService userAccountService = new UserAccountService();

	public RestResponse login(String mobile, String pwd) {

		return null;

	}

	public void process(HttpServletRequest req, HttpServletResponse resp) {

		String mobile = (String) req.getSession().getAttribute("mobile");

		mobile = mobile == null ? req.getParameter("mobile") : mobile;

		String uuid = req.getParameter("uuid");
		int tip = 0;
		String res = WechatApiUtils.waitLogin(tip, uuid);

		if (null == res) {
			RestResponse restResponse = new RestResponse(100, "扫描二维码验证失败", "");
			renderJson(resp, restResponse);
			return;
		}

		RestResponse restResponse = null;
		String code = Matchers.match("window.code=(\\d+);", res);
		if (null == code) {
			restResponse = new RestResponse(100, "扫描二维码验证失败", "");
		} else if (code.equals("201")) {
			restResponse = new RestResponse(0, "成功扫描,请在手机上点击确认以登录", code);
		} else if (code.equals("200")) {
			LOGGER.info("确认登陆");
			userAccountService.initWechat(res,mobile);
			restResponse = new RestResponse(0, "登陆成功", code);
		} else if (code.equals("408")) {
			restResponse = new RestResponse(102, "登录超时", code);
		} else {
			restResponse = new RestResponse(103, "登录失败，请重新扫描二维码", code);
		}
		renderJson(resp, restResponse);
	}

}
