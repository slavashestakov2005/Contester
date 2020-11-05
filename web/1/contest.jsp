<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="Windows-1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	try{
		String pwd = request.getParameter("password");
		if (pwd != null) {
			Cookie password = new Cookie("password", pwd);
			password.setMaxAge(60 * 60 * 10);
			// Add both the cookies in the response header.
			response.addCookie(password);
		}
	}catch(Exception e){}
%>

<html lang="ru">
	<head>
		<meta charset="Windows-1251">
		<link rel="shortcut icon" href="../Images/image.ico" type="image/x-icon">
		<link rel="stylesheet" href="../CSS/users.css">
		<link rel="stylesheet" href="../CSS/admin.css">
		<script src="../JS/users.js" type="text/javascript"> </script>
		<script src="../JS/admin.js" type="text/javascript"> </script>
		<script>
			var cnt = -1;
			var page_type = "contest";
			var page_number = 1;
			var page_contest = 1;
			Check(document, page_contest);
		</script>
		<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
		<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
		<title>������� �1</title>
	</head>
	<body>
		<div id="header">
			<div id="nav1"><center><button id="image" onclick="Edit(document, page_type, page_number); "><img src="../Images/edit.png"></button></center></div>
			<div id="nav2">
				<p align="right">
					<script> document.write(getCookie(document, "name")); </script>
					<br/>
					<script> document.write(getCookie(document, "surname")); </script>
					<br/>
					<a href="../index.jsp">�����</a>
				</p>
			</div>
			<div id="nav3"><center><h1><a href="contest.jsp">������� �1</a></h1></center></div>
		</div>
		<div id="page">
			<iframe src="sidebar.html" width="150px" height="100%" scrolling="no" frameborder="no" style="position: absolute;">������ �����</iframe>"
			<div id="content">
				<center><h2>������� �1</h2></center>
				<h3>��������:</h3>
				<p>����� ����������� �������� ��������. ��� ����� �������������, ����� �� ���������.</p>
				<h3>����� ������:</h3>
				<p>21.10.2020 21:56</p>
				<h3>����� ���������:</h3>
				<p>01.11.2020 23:59</p>
				<h3>�����������������:</h3>
				<p>11:02:03</p>
				<div id="down2"></div>
			</div>
		</div>
	</body>
</html>