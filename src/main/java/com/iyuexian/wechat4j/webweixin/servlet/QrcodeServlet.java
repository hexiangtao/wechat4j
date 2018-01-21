package com.iyuexian.wechat4j.webweixin.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.DateKit;
import com.blade.kit.StringKit;
import com.blade.kit.http.HttpRequest;
import com.iyuexian.wechat4j.config.Constant;
import com.iyuexian.wechat4j.webweixin.entity.RestResponse;

@WebServlet("/qrcode")
public class QrcodeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = LoggerFactory.getLogger(QrcodeServlet.class);

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uuid = request.getParameter("uuid");
		if (StringKit.isBlank(uuid)) {
			RestResponse restResponse = RestResponse.fail("uuid was empty");
			renderJson(response, restResponse);
			return;
		}
		String url = Constant.QRCODE_URL + uuid;
		HttpRequest.post(url, true, "t", "webwx", "_", DateKit.getCurrentUnixTime()).receive(response.getOutputStream());
	}

}
