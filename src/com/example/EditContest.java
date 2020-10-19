package com.example;

import com.example.database.rows.Contest;
import com.example.database.rows.Task;
import com.example.database.tables.ContestsTable;
import com.example.database.tables.ContestsTasksTable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/edit_contest")
public class EditContest extends HttpServlet {
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
        Contest contest = ContestsTable.selectContestByID(contestId);
        System.out.println(contest);
        pw.print("<center><h3>Название:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_small\" id=\"task_name\">" + contest.getName() + "</textarea>\n");
        pw.print("<center><h3>Условие:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_large\" id=\"task_description\">" + contest.getAbout() + "</textarea>\n");
        pw.print("<center><h3>Задания:</h3></center>\n");
        pw.print("<table border=\"1\" width=\"100%\" id=\"task\">\n" +
                "            <tr>\n" +
                "                <td width=\"50%\">Задача</td>\n" +
                "                <td width=\"50%\">Удалить</td>\n" +
                "            </tr>\n");
        ArrayList<Task> tasks = ContestsTasksTable.getTasksForContest(contestId);
        for(int i = 0; i < tasks.size(); ++i){
            pw.print("<tr>\n" +
                    "   <td>" + tasks.get(i).getName() + "</td>\n" +
                    "   <td><button>Удалить</button></td>\n" +
                    "</tr>\n");
        }
        pw.print("</table>\n" +
                "<center>\n" +
                "   <button onclick=\"Save(document, cnt, page_type, page_number);\">Сохранить всё</button>\n" +
                "</center>\n" +
                "<div id=\"down2\"></div>");
    }
}
