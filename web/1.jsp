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
        <title>Контест №1</title>
    </head>
    <body>
        <div id="header">
            <div id="nav2">
                <p align="right">
                    <script> document.write(getCookie(document, "name")); </script>
                    <br/>
                    <script> document.write(getCookie(document, "surname")); </script>
                    <br/>
                    <a href="index.jsp">Выйти</a>
                </p>
            </div>
            <div id="nav3"><center><h1><a href="main.jsp">Контестер</a></h1></center></div>
        </div>
        <div id="page">
            <div id="sidebar">
                <h2><a href="1.jsp">Контест №1</a></h2>
                <div id="down"></div>
            </div>
            <div id="content">
                <center><h2>Контест №1</h2></center>
                <h3>Описание:</h3>
                <p>Здесь расположено описание контеста.</p>
                <p>Оно может отсутствовать, тогда по умолчанию.</p>
                <h3>Время старта:</h3>
                <p>Нет в базе данных</p>
                <h3>Время окончания:</h3>
                <p>Нет в базе данных</p>
                <h3>Продолжительность:</h3>
                <p>Нет в базе данных</p>
                <center>
                    <form action="1/contest.jsp" method="GET">
                        <input type="password" id="password" name="password" placeholder="Введите пароль для входа">
                        <input type="submit" value="Решать" onclick="return Start(this);" />
                    </form>
                </center>
                <div id="down2"></div>
            </div>
        </div>
    </body>
</html>