<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="Windows-1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	try{
		Cookie name = new Cookie("name", request.getParameter("name").trim());
		Cookie surname = new Cookie("surname", request.getParameter("surname").trim());
		name.setMaxAge(60*60*10);
		surname.setMaxAge(60*60*10);
		// Add both the cookies in the response header.
		response.addCookie(name);
		response.addCookie(surname);
	}catch(Exception e){}
%>

<html lang="ru">
	<head>
		<meta charset="Windows-1251">
		<link rel="shortcut icon" href="image.ico" type="image/x-icon">
		<link rel="stylesheet" href="style.css">
		<script src="script.js" type="text/javascript"> </script>
		<title>Контест №1</title>
	</head>
	<body>
		<div id="header">
			<div id="nav1"><h1><a href="contest.jsp">Контест №1</a></h1></div>
			<div id="nav2">
				<p align="right">
					<script> document.write(getCookie(document, "name")); </script>
					<br/>
					<script> document.write(getCookie(document, "surname")); </script>
					<br/>
					<a href="index.jsp">Выйти</a>
				</p>
			</div>
		</div>
		<div id="page">
			<div id="sidebar">
				<h2><a href="task1.html">A+B</a></h2>
				<h2><a href="task2.html">Задача №1</a></h2>
				<div id="down"></div>
			</div>
			<div id="content">
				<center><h2>Контест №1</h2></center>
				<p>Здесь расположено описание контеста.</p>
				<p>Оно может отсутствовать, тогда по умолчанию.</p>
				<div id="down2"></div>
			</div>
		</div>
	</body>
</html>