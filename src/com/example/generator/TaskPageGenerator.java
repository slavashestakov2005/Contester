package com.example.generator;

import com.example.Root;
import com.example.database.rows.Contest;
import com.example.database.rows.Task;
import com.example.database.rows.Test;

import java.io.*;
import java.util.ArrayList;

public class TaskPageGenerator {
    public static void generate(Contest contest, Task task, ArrayList<Task> tasks, ArrayList<Test> tests) throws IOException {
        String pageName = Root.webDirectory + "\\" + contest.getId() + "\\" + task.getId() + ".jsp";
        StringBuilder text = new StringBuilder();
        text.append(part1).append(task.getId())
                .append(part2).append(contest.getId())
                .append(part3).append(contest.getName())
                .append(part4).append(contest.getName())
                .append(part5);
        for(Task task1 : tasks){
            text.append("\t\t\t\t<h2><a href=\"").append(task1.getId()).append(".jsp\">").append(task1.getName()).append("</a></h2>\n");
        }
        text.append(part6).append(task.getName())
                .append(part7).append(Generator.toHTML(task.getAbout(), 4))
                .append(part8).append(Generator.toHTML(task.getInput(), 4))
                .append(part9).append(Generator.toHTML(task.getOutput(), 4))
                .append(part10);
        for(Test test : tests){
            text.append("\t\t\t\t\t<tr>\n").append("\t\t\t\t\t\t<td>\n");
            text.append(Generator.toHTML(test.getInput(), 7));
            text.append("\t\t\t\t\t\t</td>\n").append("\t\t\t\t\t\t<td>\n");
            text.append(Generator.toHTML(test.getOutput(), 7));
            text.append("\t\t\t\t\t\t</td>\n").append("\t\t\t\t\t</tr>\n");
        }
        text.append(part11).append(contest.getId()).append(part12).append(task.getId()).append(part13);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(pageName), "UTF-8"));
        out.write(text.toString());
        out.close();
    }

    private static String part1 = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\n" +
            "\t\tpageEncoding=\"UTF-8\"%>\n" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
            "\n" +
            "<html lang=\"ru\">\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"UTF-8\">\n" +
            "\t\t<link rel=\"shortcut icon\" href=\"../Images/image.ico\" type=\"image/x-icon\">\n" +
            "\t\t<link rel=\"stylesheet\" href=\"../CSS/users.css\">\n" +
            "\t\t<link rel=\"stylesheet\" href=\"../CSS/admin.css\">\n" +
            "\t\t<script src=\"../JS/users.js\" type=\"text/javascript\"> </script>\n" +
            "\t\t<script src=\"../JS/admin.js\" type=\"text/javascript\"> </script>\n" +
            "\t\t<script>\n" +
            "\t\t\tvar cnt = -1;\n" +
            "\t\t\tvar page_type = \"task\";\n" +
            "\t\t\tvar page_number = ";
    private static String part2 = ";\n\t\t\tvar page_contest = ";
    private static String part3 = ";\n" +
            "\t\t\tCheck(document, page_contest);\n" +
            "\t\t</script>\n" +
            "\t\t<script src=\"https://polyfill.io/v3/polyfill.min.js?features=es6\"></script>\n" +
            "\t\t<script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js\"></script>\n" +
            "\t\t<title>";
    private static String part4 = "</title>\n" +
            "\t</head>\n" +
            "\t<body>\n" +
            "\t\t<div id=\"header\">\n" +
            "\t\t\t<div id=\"nav1\"><center><button id=\"image\" onclick=\"Edit(document, page_type, page_number); \"><img src=\"../Images/edit.png\"></button></center></div>\n" +
            "\t\t\t<div id=\"nav2\">\n" +
            "\t\t\t\t<p align=\"right\">\n" +
            "\t\t\t\t\t<script> document.write(getCookie(document, \"name\")); </script>\n" +
            "\t\t\t\t\t<br/>\n" +
            "\t\t\t\t\t<script> document.write(getCookie(document, \"surname\")); </script>\n" +
            "\t\t\t\t\t<br/>\n" +
            "\t\t\t\t\t<a href=\"../index.jsp\">Выйти</a>\n" +
            "\t\t\t\t</p>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div id=\"nav3\"><center><h1><a href=\"contest.jsp\">";
    private static String part5 = "</a></h1></center></div>\n" +
            "\t\t</div>\n" +
            "\t\t<div id=\"page\">\n" +
            "\t\t\t<div id=\"sidebar\">\n";
    private static String part6 = "\t\t\t\t<br/>\n" +
            "\t\t\t\t<h2><a href=\"../main.jsp\">Вернуться</a></h2>\n" +
            "\t\t\t\t<div id=\"down\"></div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div id=\"content\">\n" +
            "\t\t\t\t<center><h2>";
    private static String part7 = "</h2></center>\n" +
            "\t\t\t\t<h3>Условие:</h3>\n";
    private static String part8 = "\t\t\t\t<h3>Входные данные:</h3>\n";
    private static String part9 = "\t\t\t\t<h3>Выходные данные:</h3>\n";
    private static String part10 = "\t\t\t\t<table border=\"1\" width=\"95%\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td width=\"50%\"><center>Input</center></td>\n" +
            "\t\t\t\t\t\t<td width=\"50%\"><center>Output</center></td>\n" +
            "\t\t\t\t\t</tr>\n";
    private static String part11 = "\t\t\t\t</table>\n" +
            "\t\t\t\t<div id=\"code\">\n" +
            "\t\t\t\t\t<p>Решение:</p>\n" +
            "\t\t\t\t\t<% out.print(\"<form action=\\\"../run?contest=";
    private static String part12 = "&task=";
    private static String part13 = "&name=\"); %>${cookie['name'].getValue()}<% out.print(\"&surname=\"); %>${cookie['surname'].getValue()}<% out.print(\"\\\" method=\\\"post\\\">\"); %>\n" +
            "\t\t\t\t\t\t<textarea id=\"code_text\" name=\"code\" placeholder=\"Введите код\" oninput=\"textInput(document)\"></textarea>\n" +
            "\t\t\t\t\t\t<br/>\n" +
            "\t\t\t\t\t\t<input id=\"code_file\" type=\"file\" oninput=\"codeInput(document, 'file');\" onchange=\"readFile(document);\"/>\n" +
            "\t\t\t\t\t\t<select name=\"lang\">\n" +
            "\t\t\t\t\t\t\t<option value=\"cpp\">C++</option>\n" +
            "\t\t\t\t\t\t\t<option value=\"py\">Python</option>\n" +
            "\t\t\t\t\t\t</select>\n" +
            "\t\t\t\t\t\t<input type=\"submit\" value=\"Отправить\" onclick=\"return Start(this);\" />\n" +
            "\t\t\t\t\t</form>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t<div id=\"down2\"></div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\t</body>\n" +
            "</html>";
}
