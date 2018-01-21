<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>请用微信扫码登陆</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"	src="https://cdn.bootcss.com/jquery/3.2.0/jquery.min.js"></script>
<script type="text/javascript" src="./static/js/login.js"></script>
<link rel="stylesheet" href="./static/css/login.css">
</head>


<body draggable="false">
	<canvas
		style="position: absolute; top: 0; left: 0; bottom: 0; right: 0; z-index: -1; width: 100%; height: 100%;"
		id="heroCanvas"></canvas>

	<!--[if lt IE 8]>
    <p class="browsehappy">
      你正在使用一个<strong>过时</strong>的浏览器。请<a class="link" href="http://browsehappy.com" target="_blank">升级你的浏览器</a>以查看微信网页版。</p>
    </p>
    <![endif]-->

	<!--BEGIN login-->
	<div class="login" ng-controller="loginController" ng-if="true">

		<!--BEGIN logo-->
		<div class="logo">
			<i class="web_wechat_login_logo"></i>
		</div>
		<!--END logo-->
		<div class="login_box">
			<div class="qrcode">
				<img class="img" id="codeImg"/>
				<div class="">
					<p class="sub_title" style="display:none; ">使用手机微信扫码登录</p>
					<p class="sub_desc" style="display:none; ">网页版微信需要配合手机使用</p>
				</div>
				<div>
					<div class="refresh_qrcode_mask" style="display: none;">
						<i class="icon-refresh"
							ng-class="{rotateLoading: isRotateLoading}"
							ng-click="refreshQrcode()"></i>
					</div>
					<p class="refresh_tips" style="display: none;">二维码失效，点击刷新</p>
				</div>
			</div>
		</div>


		<!--END lang-->
		<!--BEGIN copyright-->
		<div class="copyright">
			<p class="desc">&copy; 1998 - 2017 Tencent Inc. All Rights
				Reserved</p>
		</div>
		<!--END copyright-->
	</div>

</body>