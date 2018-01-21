function reg() {

	
	var mobile = $.trim($('#mobile').val());
	var pwd = $.trim($('#pwd').val());
	
	$.post('account', {
		'mobile' : mobile,
		'pwd' : pwd,
		'action' : 'reg'
	}, function(data) {
		if (data.code != 0) {
			layer.msg(data.message);
			return;
		}else{
			  layer.msg("注册成功");
		window.location.href = "index.jsp";
		}
	});

}

function login() {
	var mobile = $.trim($('#mobile').val());
	var pwd = $.trim($('#pwd').val());

	$.post('account', {
		'mobile' : mobile,
		'pwd' : pwd,
		'action' : 'login'
	}, function(data) {
		if (data.code != 0) {
			layer.msg(data.message);
			return;
		}else{
			
		window.location.href = "wxlogin.jsp";
		}
	});

}

