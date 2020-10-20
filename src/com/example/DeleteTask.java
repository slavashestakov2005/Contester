package com.example;

import com.example.database.rows.ContestTask;
import com.example.database.tables.ContestsTasksTable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete_task")
public class DeleteTask extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        int taskId = Integer.parseInt(request.getParameter("test"));
        int contestId = Integer.parseInt(request.getParameter("contest"));
        /** work with they **/
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        final String status;
        if (Admin.checkUser(name, surname)) status = "Ok";
        else status = "Fail";
        System.out.println(name + " - " + surname + " - " + taskId + " -> " + status);
        pw.print(status);
        if (status.equals("Ok")) ContestsTasksTable.delete(new ContestTask(contestId, taskId));
        System.out.println(pw.toString());
    }
}
