package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/tests")
public class Tests extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        String input = request.getParameter("input");
        String output = request.getParameter("output");
        boolean isExample = Boolean.parseBoolean(request.getParameter("example"));
        boolean isPublic = Boolean.parseBoolean(request.getParameter("public"));
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        /** work with they **/
        System.out.println(input + " : " + output + " : " + isExample + " : " + isPublic + " : " + id);
        System.out.println("From : " + name + " - " + surname);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        final String status;
        if (Admin.checkUser(name, surname)) status = "Ok";
        else status = "Fail";
        System.out.println(status);
        pw.print(status);
    }
}
