<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<title>login</title>
<link rel="stylesheet" type="text/css" href="./static/css/normalize.css" />
<link rel="stylesheet" type="text/css" href="./static/css/index.css" />
<!--必要样式-->
<link rel="stylesheet" type="text/css" href="./static/css/component.css" />
<!--[if IE]>
<script src="./static/js/html5.js"></script>
<![endif]-->
<%@ include file="header.jsp" %>
</head>
<body>
		<div class="container demo-1">
			<div class="content">
				<div id="large-header" class="large-header">
					<canvas id="demo-canvas"></canvas>
					<div class="logo_box">
						<h3></h3>
						<form action="#" name="f" method="post">
							<div class="input_outer">
								<span class="u_user"></span>
								<input name="mobile"  class="text"  id="mobile"  style="color: #FFFFFF !important" type="text" placeholder="请输入手机号">
							</div>
							<div class="input_outer">
								<span class="us_uer"></span>
								<input name="pwd"  id="pwd" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
							</div>
							<div class="mb2"><a class="act-but submit" href="javascript:login()" style="color: #FFFFFF">登录</a></div>
							<a style="color:#DDDDDD;margin-left:75px;font-size:13px;" href="reg.jsp">没有帐号? 点击这里注册</a>
						</form>
					</div>
				</div>
			</div>
		</div><!-- /container -->
		<script src="./static/js/TweenLite.min.js"></script>
		<script src="./static/js/EasePack.min.js"></script>
		<script src="./static/js/rAF.js"></script>
		<script src="./static/js/index.js"></script>
		<script src="./static/js/account.js"></script>
	</body>
</html>