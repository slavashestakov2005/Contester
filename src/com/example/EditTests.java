package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/edit_tests")
public class EditTests extends HttpServlet {
    private static String adminNameFile = "C:\\Users\\Public\\Documents\\Contester\\admin_name.txt";
    private static String adminName, adminSurname;

    static {
        try {
            FileInputStream in = new FileInputStream(adminNameFile);
            String lines[] = new String(in.readAllBytes()).split("[\r\n]+");
            adminName = lines[0];
            adminSurname = lines[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        Integer taskId = Integer.parseInt(request.getParameter("task"));
        /** work with they **/
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        final String status;
        if (adminName.equals(name) && adminSurname.equals(surname)) status = "Ok";
        else status = "Fail";
        System.out.println(name + " - " + surname + " - " + taskId + " -> " + status);
        if (status.equals("Fail")) pw.print(status);
        else generatePage(pw);
        System.out.println(pw.toString());
    }

    private void generatePage(PrintWriter pw) {
        pw.print("<table border=\"1\" width=\"100%\" id=\"task\">\n" +
                "            <tr>\n" +
                "                <td width=\"5%\">№</td>\n" +
                "                <td width=\"40%\">Ввод</td>\n" +
                "                <td width=\"40%\">Вывод</td>\n" +
                "                <td width=\"5%\">Пример</td>\n" +
                "                <td width=\"5%\">Открытый</td>\n" +
                "                <td width=\"5%\">Изменено</td>\n" +
                "            </tr>\n" +
                "            <tr id=\"1\">\n" +
                "                <td>1</td>\n" +
                "                <td>2 3</td>\n" +
                "                <td>5</td>\n" +
                "                <td>Пример</td>\n" +
                "                <td>Открытый</td>\n" +
                "                <td><button id=\"btn1\" disabled>Изменено</button></td>\n" +
                "            </tr>\n" +
                "            <tr id=\"2\">\n" +
                "                <td>2</td>\n" +
                "                <td><textarea id=\"input2\" class=\"input_output\" oninput=\"Change(document, 2);\"></textarea></td>\n" +
                "                <td><textarea id=\"output2\" class=\"input_output\" oninput=\"Change(document, 2);\"></textarea></td>\n" +
                "                <td><input id=\"example2\" type=\"checkbox\" onchange=\"Change(document, 2);\"></td>\n" +
                "                <td><input id=\"public2\" type=\"checkbox\" onchange=\"Change(document, 2);\"></td>\n" +
                "                <td><button id=\"btn2\" disabled>Изменено</button></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <script>var cnt = 2;</script>\n" +
                "        <center>\n" +
                "            <button onclick=\"Save(document, cnt);\">Сохранить всё</button>\n" +
                "            <button onclick=\"++cnt; NewRow(document, cnt);\">Новый тест</button>\n" +
                "        </center>");
    }
}