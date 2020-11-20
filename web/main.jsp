<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="Images/image.ico" type="image/x-icon">
        <link rel="stylesheet" href="CSS/users.css">
        <link rel="stylesheet" href="CSS/admin.css">
        <script src="JS/users.js" type="text/javascript"> </script>
        <script src="JS/admin.js" type="text/javascript"> </script>
        <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
        <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
        <script> var lang = -1; </script>
        <title>Контестер</title>
    </head>
    <body>
        <div id="header">
            <div id="nav1"><center><button id="image" onclick="CreateContest(document)"><img src="Images/add.png"></button></center></div>
            <div id="nav2">
                <p align="right">
                    <script>printNameSurname(document);</script>
                    <br/>
                    <a href="index.jsp">Выйти</a>
                </p>
            </div>
            <div id="nav3"><center><h1><a href="main.jsp">Контестер</a></h1></center></div>
        </div>
        <div id="page">
            <iframe src="sidebar.html" width="150px" height="100%" scrolling="no" frameborder="no" style="position: absolute; ">Список контестов</iframe>
            <div id="content">
                <center><h2>Контестер</h2></center>
                <p>Контестер — web приложение для создания контестов по информатике.</p>
                <p>Контестер — web приложение для создания контестов по информатике.</p>
                <p>Автор: Шестаков Вячеслав, ученик Инженерной Школы гимназии "Униврс №1".</p>
                <p>Руководитель: Вахитова Екатерина Юрьевна, учитель гимназии "Униврс №1".</p>
                <p>Идея: возникла благодаря ОЦ Сириусу и Канухину Александру в частности.</p>
                <div id="down2"></div>
            </div>
        </div>
    </body>
</html>