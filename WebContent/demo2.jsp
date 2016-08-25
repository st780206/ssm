<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>登录</title>

</head>

<body>

	<form id="loginFrom" name="loginFrom" method="post" action="demo2login.mvc">

		<label>请输入昵称:</label> <input id="username" name="username" type="text" />

		<label>请输入密码:</label> <input id="password" name="password" type="text" />

		<button id="login" type="submit">登录</button>

	</form>

	<h2 id="errorMsg">${errorMsg}</h2>

</body>

</html>