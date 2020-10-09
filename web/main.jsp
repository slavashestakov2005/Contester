<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" href="style.css">
        <script src="script.js" type="text/javascript" />
        <script src="https://snipp.ru/cdn/jquery/2.1.1/jquery.min.js" />
        <title>Контестер 1</title>
    </head>
    <body>
        <script>
            var name = getCookie(document, "name");
            var surname = getCookie(document, "surname");
            alert(name);
            alert(surname);
            if (name == "" || surname == ""){
                alert("Bad name");
                document.location.replace("index.jsp");
            }
        </script>
        <b>Имя:</b>
        <script> document.write(getCookie(document, "name")); </script>
        <b>Фамилия:</b>
        <script> document.write(getCookie(document, "surname")); </script>
        <br/>
        <a href="index.jsp"> Вернуться </a>
        <br/>
        <div id="code">
            <p>Решение:</p>
            <form action="ctx.html" enctype="multipart/form-data" method="post">
                <textarea id="code_text" placeholder="Введите код" oninput="textInput(document)"></textarea>
                <br/>
                <input type="file" name="fcode">
                <input type="submit" value="Отправить" onclick="return Start(this);" />
            </form>
        </div>
    </body>
</html>