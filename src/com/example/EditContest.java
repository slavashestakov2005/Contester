package com.example;

import com.example.database.rows.Contest;
import com.example.database.rows.Lang;
import com.example.database.rows.Task;
import com.example.database.tables.ContestsTable;
import com.example.database.tables.ContestsTasksTable;
import com.example.database.tables.LangsTable;

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
                "\t<tr>\n" +
                "\t\t<td width=\"40%\"><center>Задача</center></td>\n" +
                "\t\t<td width=\"30%\"><center>Редактировать</center></td>\n" +
                "\t\t<td width=\"30%\"><center>Удалить</center></td>\n" +
                "\t</tr>\n");
        ArrayList<Task> tasks = ContestsTasksTable.getTasksForContest(contestId);
        for(int i = 0; i < tasks.size(); ++i){
            pw.print("\t<tr>\n" +
                    "\t\t<td>" + tasks.get(i).getName() + "</td>\n" +
                    "\t\t<td><center><button onclick=\"Edit(document, 'task', " + tasks.get(i).getId() + ")\">Редактировать</button></center></td>\n" +
                    "\t\t<td><center><button onclick=\"Delete(document, " + tasks.get(i).getId() + ", page_number)\">Удалить</button></center></td>\n" +
                    "\t</tr>\n");
        }
        pw.print("</table>\n" +
                "<br/>\n" +
                "<center><h3>Языки:</h3></center>\n" +
                "<table border=\"1\" width=\"100%\" id=\"langs_table\">\n" +
                "\t<tr>\n" +
                "\t\t<td width=\"5%\"><center>№</center></td>\n" +
                "\t\t<td width=\"35%\"><center>Язык</center></td>\n" +
                "\t\t<td width=\"30%\"><center>Добавлен</center></td>\n" +
                "\t\t<td width=\"30%\"><center>Удалить</center></td>\n" +
                "\t</tr>\n" +
                "</table>\n" +
                "<br/>\n" +
                "<center>\n" +
                "\t<select id=\"langs_list\">\n");
        ArrayList<Lang> langs = LangsTable.getAll();
        for (int i = 0; i < langs.size(); ++i){
            pw.print("\t\t<option id=\"l_" + langs.get(i).getId() + "\" value=\"l_" + langs.get(i).getId() + "\">" + langs.get(i).getName() + "</option>\n");
        }
        pw.print("\t</select>\n" +
                "\t<button onclick=\"AddLangToContest(document)\">Добавить</button>" +
                "\t<br/>\n" +
                "\t<button onclick=\"Save(document, cnt, page_type, page_number);\">Сохранить всё</button>\n" +
                "\t<button onclick=\"Create(document, page_number)\">Новое задание</button>\n" +
                "\t<button onclick=\"Generate(document, page_contest)\">Сгенерировать</button>\n" +
                "\t<button onclick=\"DeleteContest(document, page_contest)\">Удалить</button>\n" +
                "</center>\n" +
                "<br/>\n" +
                "<div id=\"down2\"></div>");
    }
}
