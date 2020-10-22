<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="Windows-1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="ru">
    <head>
        <meta charset="Windows-1251">
        <link rel="shortcut icon" href="Images/image.ico" type="image/x-icon">
        <link rel="stylesheet" href="CSS/users.css">
        <link rel="stylesheet" href="CSS/admin.css">
        <script src="JS/users.js" type="text/javascript"> </script>
        <script src="JS/admin.js" type="text/javascript"> </script>
        <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
        <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
        <title>������� �1</title>
    </head>
    <body>
        <div id="header">
            <div id="nav2">
                <p align="right">
                    <script> document.write(getCookie(document, "name")); </script>
                    <br/>
                    <script> document.write(getCookie(document, "surname")); </script>
                    <br/>
                    <a href="index.jsp">�����</a>
                </p>
            </div>
            <div id="nav3"><center><h1><a href="main.jsp">���������</a></h1></center></div>
        </div>
        <div id="page">
            <div id="sidebar">
                <h2><a href="1.jsp">������� �1</a></h2>
                <div id="down"></div>
            </div>
            <div id="content">
                <center><h2>������� �1</h2></center>
                <h3>��������:</h3>
                <p>����� ����������� �������� ��������.</p>
                <p>��� ����� �������������, ����� �� ���������.</p>
                <h3>����� ������:</h3>
                <p>��� � ���� ������</p>
                <h3>����� ���������:</h3>
                <p>��� � ���� ������</p>
                <h3>�����������������:</h3>
                <p>��� � ���� ������</p>
                <center>
                    <form action="1/contest.jsp" method="GET">
                        <input type="password" id="password" name="password" placeholder="������� ������ ��� �����">
                        <input type="submit" value="������" onclick="return Start(this);" />
                    </form>
                </center>
                <div id="down2"></div>
            </div>
        </div>
    </body>
</html>