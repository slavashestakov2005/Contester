package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/create_task")
public class CreateTask extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int contestId = Integer.parseInt(request.getParameter("contest"));
        /** work with they **/
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        final String status;
        if (Admin.checkUser(name, surname)) status = "Ok";
        else status = "Fail";
        System.out.println(name + " - " + surname + " - " + contestId + " -> " + status);
        if (status.equals("Fail")) pw.print(status);
        else generatePage(pw, contestId);
        System.out.println(pw.toString());
    }

    private void generatePage(PrintWriter pw, int contestId) {
        pw.print("<center><h3>Название:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_small\" id=\"task_name\"></textarea>\n");
        pw.print("<center><h3>Условие:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_large\" id=\"task_description\"></textarea>\n");
        pw.print("<center><h3>Входные данные:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_middle\" id=\"task_input\"></textarea>\n");
        pw.print("<center><h3>Выходные данные:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_middle\" id=\"task_output\"></textarea>\n");
        pw.print("<center>\n" +
                "   <button onclick=\"AddTask(document, " + contestId + ");\">Сохранить всё</button>\n" +
                "</center>\n" +
                "<div id=\"down2\"></div>");
    }
}
