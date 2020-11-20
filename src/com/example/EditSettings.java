package com.example;

import com.example.database.rows.Lang;
import com.example.database.tables.LangsTable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/edit_settings")
public class EditSettings extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        /** work with they **/
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        final String status;
        if (Admin.checkUser(name, surname)) status = "Ok";
        else status = "Fail";
        if (status.equals("Fail")) pw.print(status);
        else generatePage(pw);
    }

    private void generatePage(PrintWriter pw) {
        pw.print("<center><h3>Доступные языки:</h3></center>\n");
        pw.print("<table border=\"1\" width=\"100%\" id=\"langs\">\n" +
                "            <tr>\n" +
                "                <td width=\"5%\">№</td>\n" +
                "                <td width=\"10%\">Название</td>\n" +
                "                <td width=\"10%\">Расширение 1</td>\n" +
                "                <td width=\"10%\">Расширение 2</td>\n" +
                "                <td width=\"35%\">Компиляция</td>\n" +
                "                <td width=\"20%\">Запуск</td>\n" +
                "                <td width=\"5%\">Изменено</td>\n" +
                "                <td width=\"5%\">Удалить</td>\n" +
                "            </tr>\n");
        /** get all langs */
        ArrayList<Lang> langs = LangsTable.getAll();
        for(int i = 0; i < langs.size(); ++i){
            pw.print("<tr id=\"" + (i + 1) + "\">\n" +
                    "   <td><p id=\"index" + (i + 1) + "\">" + langs.get(i).getId() + "</p></td>\n" +
                    "   <td><textarea id=\"name" + (i + 1) + "\" class=\"input_output\" oninput=\"Change(document, " + (i + 1) + ");\">" + langs.get(i).getName() + "</textarea></td>\n" +
                    "   <td><textarea id=\"end1" + (i + 1) + "\" class=\"input_output\" oninput=\"Change(document, " + (i + 1) + ");\">" + langs.get(i).getEnd1() + "</textarea></td>\n" +
                    "   <td><textarea id=\"end2" + (i + 1) + "\" class=\"input_output\" oninput=\"Change(document, " + (i + 1) + ");\">" + langs.get(i).getEnd2() + "</textarea></td>\n" +
                    "   <td><textarea id=\"compile" + (i + 1) + "\" class=\"input_output\" oninput=\"Change(document, " + (i + 1) + ");\">" + langs.get(i).getCompileCommand() + "</textarea></td>\n" +
                    "   <td><textarea id=\"execute" + (i + 1) + "\" class=\"input_output\" oninput=\"Change(document, " + (i + 1) + ");\">" + langs.get(i).getExecuteCommand() + "</textarea></td>\n" +
                    "   <td><button id=\"btn" + (i + 1) + "\" disabled>Изменено</button></td>\n" +
                    "   <td><button onclick=\"DeleteLang(document, " + (i + 1) + ")\">Удалить</button></td>\n" +
                    "</tr>\n");
        }
        pw.print("</table>\n" +
                "<br/>\n" +
                "<center>\n" +
                "   <button onclick=\"if(lang_c === -1) lang_c = " + langs.size() + "; SaveLangs(document, lang_c);\">Сохранить всё</button>\n" +
                "   <button onclick=\"if(lang_c === -1) lang_c = " + langs.size() + "; ++lang_c; NewRowLang(document, lang_c);\">Новый язык</button>\n" +
                "</center>\n" +
                "<div id=\"down2\"></div>");
        System.out.println("Lang => " + langs.size());
    }
}
