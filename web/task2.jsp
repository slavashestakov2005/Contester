<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
                <h2><a href="task1.jsp">A+B</a></h2>
                <h2><a href="task2.jsp">Задача №1</a></h2>
                <div id="down"></div>
            </div>
            <div id="content">
                <center><h2>Задача №1</h2></center>
                <h3>Условие:</h3>
                <p>Условие задачи</p>
                <h3>Входные данные:</h3>
                <p>Описание входных данных</p>
                <h3>Выходные данные:</h3>
                <p>Описание выходных данных</p>
                <table border="1" width="95%">
                    <tr>
                        <td width="50%"><center>Input</center></td>
                        <td width="50%"><center>Output</center></td>
                    </tr>
                    <tr>
                        <td>
                            <p>Входные данные примера 1.</p>
                        </td>
                        <td>
                            <p>Выходные данные примера 1.</p>
                        </td>
                    </tr>
                </table>
                <div id="code">
                    <p>Решение:</p>
                    <% out.print("<form action=\"run?lang=cpp&name="); %>${cookie['name'].getValue()}<% out.print("&surname="); %>${cookie['surname'].getValue()}<% out.print("\" method=\"post\">"); %>
                        <textarea id="code_text" name="code" placeholder="Введите код" oninput="textInput(document)"></textarea>
                        <br/>
                        <input id="code_file" type="file" oninput="codeInput(document, 'file');" onchange="readFile(document);"/>
                        <input type="submit" value="Отправить" onclick="return Start(this);" />
                    </form>
                </div>
                <div id="down2"></div>
            </div>
        </div>
    </body>
</html>