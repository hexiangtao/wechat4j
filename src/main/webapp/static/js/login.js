var uuid="";
function showQrCode() {
	$.get("uuid").done(function(resp) {
		if (!resp || resp.code != 0) {console.log("error");}
		uuid = resp.data;
		var d = new Date();
		$("#codeImg").attr("src","qrcode?uuid=" + uuid + "&_t=" + d.getTime());
		$(".login").show();
	});
}

function waitlogin(){
	 if(!uuid){
		 return;
	 }
	$.get("waitlogin?uuid="+uuid).done(function(resp) {if (!resp || resp.code != 0) {console.log("error");}
		var code = resp.data;
		 if(code=="200"){
			 window.location.href="main.jsp";
		 }
	});
	 
	
}

$(function() {
	showQrCode();
	setInterval(showQrCode, 6000);
	setInterval(waitlogin, 2000);
	
});