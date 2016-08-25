<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>欢迎</title>



<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">

function getPersonData() {

     $.ajax({
         url: "demo1getPersonData.mvc", 
         success: function (result) {

             var content = "";

            for (var i = 0; i < result.rows.length; i++) {

                var item = result.rows[i];

                content += item.name+","+item.age+","+item.description+"<br>"; 

            }

            $("#personData").after(content);

         }

     });

}

</script>

</head>

<body>

	你好！${nickname}
	<br>

	<button onclick="getPersonData()" id="submitButton">获取人员信息</button>

	<div id="personData"></div>

</body>

</html>
<%
org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger("aaaaaaaa");
logger.error("Did it again!");
%>
