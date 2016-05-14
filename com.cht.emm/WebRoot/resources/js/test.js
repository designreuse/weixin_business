    //重新加载资源列表测试
	$.getJSON("user/rest/refreshMemoryResource",function(result) {
		if(result.successful ==true){
			return true;
		}else{
			return false;
		}
	});
		
	//设备登陆测试
	$.ajax({
		
		type:"POST",
		url:"rest/mobile/login",
		data:{deviceID:"123456",username:"cht",password:"cht"},
		dataType:"json",
        async:false,
		success:function(data){
			
		},
		error : function(p_request, p_status, p_err) {
			successful = false;
		}
		
	});
	
