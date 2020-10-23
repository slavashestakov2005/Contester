<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
            var page_type = "task";
            var page_number = 1;
            var page_contest = 1;
            Check(document, page_contest);
        </script>
        <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
        <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
        <title>Контест №1</title>
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
                    <a href="../index.jsp">Выйти</a>
                </p>
            </div>
            <div id="nav3"><center><h1><a href="contest.jsp">Контест №1</a></h1></center></div>
        </div>
        <div id="page">
            <div id="sidebar">
                <h2><a href="1.jsp">A+B</a></h2>
                <h2><a href="2.jsp">Задача №1</a></h2>
                <br/>
                <h2><a href="../main.jsp">Вернуться</a></h2>
                <div id="down"></div>
            </div>
            <div id="content">
                <center><h2>A+B</h2></center>
                <h3>Условие:</h3>
                <p>Вводятся числа A и B. Выведите их сумму.</p>
                <h3>Входные данные:</h3>
                <p>Два целых числа, по модулю не провосходящих 10^9.</p>
                <p>Для умных: \( |a| \leq 10^9 \) и \( |b| \leq 10^9 \).</p>
                <h3>Выходные данные:</h3>
                <p>Выведите одно число, сумму входных чисел.</p>
                <table border="1" width="95%">
                    <tr>
                        <td width="50%"><center>Input</center></td>
                        <td width="50%"><center>Output</center></td>
                    </tr>
                    <tr>
                        <td>
                            <p>2 3</p>
                        </td>
                        <td>
                            <p>5</p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p>0 5</p>
                        </td>
                        <td>
                            <p>5</p>
                        </td>
                    </tr>
                </table>
                <div id="code">
                    <p>Решение:</p>
                    <% out.print("<form action=\"../run?contest=1&task=1&name="); %>${cookie['name'].getValue()}<% out.print("&surname="); %>${cookie['surname'].getValue()}<% out.print("\" method=\"post\">"); %>
                        <textarea id="code_text" name="code" placeholder="Введите код" oninput="textInput(document)"></textarea>
                        <br/>
                        <input id="code_file" type="file" oninput="codeInput(document, 'file');" onchange="readFile(document);"/>
                        <select name="lang">
                            <option value="cpp">C++</option>
                            <option value="py">Python</option>
                        </select>
                        <input type="submit" value="Отправить" onclick="return Start(this);" />
                    </form>
                </div>
                <div id="down2"></div>
            </div>
        </div>
    </body>
</html>