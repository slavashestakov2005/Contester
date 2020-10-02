<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    Cookie[] cookies = request.getCookies();
    if (cookies != null){
        for (int i = 0; i < cookies.length; ++i) {
            cookies[i].setMaxAge(0);
            response.addCookie(cookies[i]);
        }
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Контестер</title>
    </head>
    <body>
        <center>
            <h2>Введите данные, чтобы войти на сайт</h2>
            <form action="contest.jsp" method="GET">
                Имя: <input type="text" name="name">
                <br/>
                Фамилия: <input type="text" name="surname" />
                <input type="submit" value="Войти" onclick="return Start(this);" />
            </form>
        </center>
    </body>
</html>