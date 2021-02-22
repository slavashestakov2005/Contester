package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/res_load")
public class ResLoad extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        System.out.println("Res_Load");
        for (Iterator<String> it = request.getHeaderNames().asIterator(); it.hasNext(); ) {
            String s = it.next();
            System.out.println("!!! " + s);
            System.out.println(request.getHeader(s));
        }
        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(test);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(Root.rootDirectory + "file.png"), "UTF-8"));
        out.write(test);
        out.close();
        request.setCharacterEncoding("utf-8");
        for(Map.Entry<String, String[]> now : request.getParameterMap().entrySet()){
            System.out.println(now.getKey());
            System.out.println(Arrays.toString(now.getValue()));
        }

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        int contestId = Integer.parseInt(request.getParameter("contest"));
        /** work with they **/
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        final String status;
        if (Admin.checkUser(name, surname) || InputToContestChecker.check(password, contestId)) status = "Ok";
        else status = "Fail";
        pw.write(status);
    }
}
