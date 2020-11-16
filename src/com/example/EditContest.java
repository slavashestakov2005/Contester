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
        if (status.equals("Fail")) pw.print(status);
        else generatePage(pw, contestId);
    }

    private void generatePage(PrintWriter pw, int contestId) {
        Contest contest = ContestsTable.selectContestByID(contestId);
        pw.print("<center><h3>Название:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_small\" id=\"task_name\">" + contest.getName() + "</textarea>\n");
        pw.print("<center><h3>Условие:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_large\" id=\"task_description\">" + contest.getAbout() + "</textarea>\n");
        pw.print("<center><h3>Время старта:</h3></center>\n");
        pw.print("<input type=\"datetime-local\" id=\"start_datetime\" " + TimeHelper.getDateTimeAttributes() + " value=\"" + TimeHelper.toJS(contest.getStart()) + "\">\n");
        pw.print("<center><h3>Время окончания:</h3></center>\n");
        pw.print("<input type=\"datetime-local\" id=\"finish_datetime\" " + TimeHelper.getDateTimeAttributes() + " value=\"" + TimeHelper.toJS(contest.getFinish()) + "\">\n");
        pw.print("<center><h3>Пароль:</h3></center>\n");
        pw.print("<textarea class=\"tasks_data_small\" id=\"contest_password\">" + contest.getPassword() + "</textarea>\n");
        pw.print("<center><h3>Задания:</h3></center>\n");
        pw.print("<table border=\"1\" width=\"100%\" id=\"task\">\n" +
                "            <tr>\n" +
                "                <td width=\"40%\"><center>Задача</center></td>\n" +
                "                <td width=\"30%\"><center>Редактировать</center></td>\n" +
                "                <td width=\"30%\"><center>Удалить</center></td>\n" +
                "            </tr>\n");
        ArrayList<Task> tasks = ContestsTasksTable.getTasksForContest(contestId);
        for(int i = 0; i < tasks.size(); ++i){
            pw.print("<tr>\n" +
                    "   <td>" + tasks.get(i).getName() + "</td>\n" +
                    "   <td><center><button onclick=\"Edit(document, 'task', " + tasks.get(i).getId() + ")\">Редактировать</button></center></td>\n" +
                    "   <td><center><button onclick=\"Delete(document, " + tasks.get(i).getId() + ", page_number)\">Удалить</button></center></td>\n" +
                    "</tr>\n");
        }
        pw.print("</table>\n" +
                "<br/>\n" +
                "<center>\n" +
                "   <button onclick=\"Save(document, cnt, page_type, page_number);\">Сохранить всё</button>\n" +
                "   <button onclick=\"Create(document, page_number)\">Новое задание</button>\n" +
                "   <button onclick=\"Generate(document, page_contest)\">Сгенерировать</button>\n" +
                "   <button onclick=\"DeleteContest(document, page_contest)\">Удалить</button>\n" +
                "</center>\n" +
                "<br/>\n" +
                "<div id=\"down2\"></div>");
    }
}
