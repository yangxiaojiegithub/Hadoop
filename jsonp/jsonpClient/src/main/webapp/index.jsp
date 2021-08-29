<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsonp客户端</title>
<script type="text/javascript" src="http://localhost:8082/js/jquery-1.6.4.js"></script>
<script type="text/javascript">
$(function(){
	$("button").click(function(){
		/* $.post("jsonpClient", function(data){
			alert(data);
		}); */
		/* $.post("http://localhost:8082/jsonpServer", function(data){
			alert(data);
		}, "script");  */
		$.ajax({
			url:"http://localhost:8082/jsonpServer",
			type:"post",
			dataType:"jsonp",
			/* jsonpCallback:"abc",      //参数值,回调方法名
			jsonp:"callback",           //参数名 */
			
			success:function(data){
				alert(data + "success");
			} 
		})
	})
});

/* function abc(data){   如果把  function 拿到外面写，则必须加  jsonpCallback， jsonp
	alert(data);
} */
</script>
</head>
<body>
	<button>jsonpClient</button>
</body>
</html>