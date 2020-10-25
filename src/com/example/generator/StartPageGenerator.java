package com.example.generator;

import com.example.Root;
import com.example.TimeHelper;
import com.example.database.rows.Contest;

import java.io.*;
import java.util.ArrayList;

public class StartPageGenerator {
    public static void generate(Contest contest, ArrayList<Contest> contests) throws IOException {
        String pageName = Root.webDirectory + "\\" + contest.getId() + ".jsp";
        StringBuilder text = new StringBuilder();
        text.append(part1).append(contest.getName())
                .append(part2);
        for(Contest now : contests){
            text.append("\t\t\t\t<h2><a href=\"").append(now.getId()).append(".jsp\">").append(now.getName()).append("</a></h2>\n");
        }
        text.append(part3).append(contest.getName())
                .append(part4).append(Generator.toHTML(contest.getAbout(), 4))
                .append(part5).append(TimeHelper.toWeb(contest.getStart()))
                .append(part6).append(TimeHelper.toWeb(contest.getFinish()))
                .append(part7).append(TimeHelper.toDuration(contest.getFinish() - contest.getStart()))
                .append(part8).append(contest.getId())
                .append(part9);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(pageName), "UTF-8"));
        out.write(text.toString());
        out.close();
    }

    private static String part1 = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\n" +
            "\t\t pageEncoding=\"UTF-8\"%>\n" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
            "\n" +
            "<html lang=\"ru\">\n" +
            "\t<head>\n" +
            "\t\t<meta charset=\"UTF-8\">\n" +
            "\t\t<link rel=\"shortcut icon\" href=\"Images/image.ico\" type=\"image/x-icon\">\n" +
            "\t\t<link rel=\"stylesheet\" href=\"CSS/users.css\">\n" +
            "\t\t<link rel=\"stylesheet\" href=\"CSS/admin.css\">\n" +
            "\t\t<script src=\"JS/users.js\" type=\"text/javascript\"> </script>\n" +
            "\t\t<script src=\"JS/admin.js\" type=\"text/javascript\"> </script>\n" +
            "\t\t<script src=\"https://polyfill.io/v3/polyfill.min.js?features=es6\"></script>\n" +
            "\t\t<script id=\"MathJax-script\" async src=\"https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js\"></script>\n" +
            "\t\t<title>";
    private static String part2 = "</title>\n" +
            "\t</head>\n" +
            "\t<body>\n" +
            "\t\t<div id=\"header\">\n" +
            "\t\t\t<div id=\"nav1\"><center><button id=\"image\" onclick=\"CreateContest(document)\"><img src=\"Images/add.png\"></button></center></div>\n" +
            "\t\t\t<div id=\"nav2\">\n" +
            "\t\t\t\t<p align=\"right\">\n" +
            "\t\t\t\t\t<script> document.write(getCookie(document, \"name\")); </script>\n" +
            "\t\t\t\t\t<br/>\n" +
            "\t\t\t\t\t<script> document.write(getCookie(document, \"surname\")); </script>\n" +
            "\t\t\t\t\t<br/>\n" +
            "\t\t\t\t\t<a href=\"index.jsp\">Выйти</a>\n" +
            "\t\t\t\t</p>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div id=\"nav3\"><center><h1><a href=\"main.jsp\">Контестер</a></h1></center></div>\n" +
            "\t\t</div>\n" +
            "\t\t<div id=\"page\">\n" +
            "\t\t\t<div id=\"sidebar\">\n";
    private static String part3 = "\t\t\t\t<div id=\"down\"></div>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div id=\"content\">\n" +
            "\t\t\t\t<center><h2>";
    private static String part4 = "</h2></center>\n" +
            "\t\t\t\t<h3>Описание:</h3>\n";
    private static String part5 = "\t\t\t\t<h3>Время старта:</h3>\n\t\t\t\t<p>";
    private static String part6 = "</p>\n\t\t\t\t<h3>Время окончания:</h3>\n\t\t\t\t<p>";
    private static String part7 = "</p>\n\t\t\t\t<h3>Продолжительность:</h3>\n\t\t\t\t<p>";
    private static String part8 = "</p>\n\t\t\t\t<center>\n" +
            "\t\t\t\t\t<form action=\"";
    private static String part9 = "/contest.jsp\" method=\"GET\">\n" +
            "\t\t\t\t\t\t<input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Введите пароль для входа\">\n" +
            "\t\t\t\t\t\t<input type=\"submit\" value=\"Решать\" onclick=\"return Start(this);\" />\n" +
            "\t\t\t\t\t</form>\n" +
            "\t\t\t\t</center>\n" +
            "\t\t\t\t<div id=\"down2\"></div>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\t</body>\n" +
            "</html>";
}
