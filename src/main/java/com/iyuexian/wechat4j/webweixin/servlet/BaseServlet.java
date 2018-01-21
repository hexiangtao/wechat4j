package com.iyuexian.wechat4j.webweixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iyuexian.wechat4j.webweixin.entity.RestResponse;

public abstract class BaseServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;
	static final Logger logger = LoggerFactory.getLogger(BaseServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			logger.debug("begin process " + req.getRequestURI());
			process(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			RestResponse restResponse = new RestResponse(500, e.getMessage(), "");
			renderJson(resp, restResponse);
		}
	}

	protected abstract void process(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void renderJson(HttpServletResponse response, RestResponse restResponse) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write(JSON.toJSONString(restResponse));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}

	public String getParam(HttpServletRequest request, String name, String defaultValue) {

		String value = request.getParameter(name);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	public int getParam(HttpServletRequest request, String name, int defaultValue) {
		String value = getParam(request, name, String.valueOf(defaultValue));
		try {
			return Integer.parseInt(value);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public double getParam(HttpServletRequest request, String name, double defaultValue) {
		String value = getParam(request, name, String.valueOf(defaultValue));
		try {
			return Double.parseDouble(value);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public Float getParam(HttpServletRequest request, String name, Float defaultValue) {
		String value = getParam(request, name, String.valueOf(defaultValue));
		try {
			return Float.parseFloat(value);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public long getParam(HttpServletRequest request, String name, Long defaultValue) {
		String value = getParam(request, name, String.valueOf(defaultValue));
		try {
			return Long.parseLong(value);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

}
