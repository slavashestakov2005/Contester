package com.example.runner;

public class AnswerWriter {
    private StringBuilder builder;
    private String error, MESSAGE_1 = "Не правильный вывод на тесте №", MESSAGE_2 = ".\n" +
            "<table border=\"1\" width=\"95%\">\n" +
            "\t<tr>\n" +
            "\t\t<td width=\"34%\"><center>Ввод:</center></td>\n" +
            "\t\t<td width=\"33%\"><center>Коректный вывод:</center></td>\n" +
            "\t\t<td width=\"33%\"><center>Ваш вывод:</center></td>\n" +
            "\t</tr>\n";
    private int cnt;
    private boolean status = true;

    public AnswerWriter(){
        clear();
    }

    public void fail(){
        status = false;
        clear();
        builder.append("<center><font color=\"red\" size=\"13\">Running failed!</font></center>");
    }

    public void start(){
        builder.append("<table border=\"1\" width=\"95%\">\n")
                .append("\t<tr>\n")
                .append("\t\t<td width=\"25%\"><center>Тест</center></td>\n")
                .append("\t\t<td width=\"25%\"><center>Статус</center></td>\n")
                .append("\t\t<td width=\"25%\"><center>Время</center></td>\n")
                .append("\t\t<td width=\"25%\"><center>Память</center></td>\n")
                .append("\t</tr>\n");
    }

    public void finish(int contestId){
        builder.append("</table>\n")
                .append("<br>\n")
                .append("<center><button onclick=\"move()\">Назад</button></center>\n")
                .append("<script>\n")
                .append("\tfunction move(){\n")
                .append("\t\tdocument.location.replace(\"" + contestId + "/contest.jsp\");\n")
                .append("\t\treturn false;\n")
                .append("\t}\n")
                .append("</script>");
    }

    public void addTest(boolean result){
        status &= result;
        builder.append("\t<tr>\n")
                .append("\t\t<td>\n")
                .append("\t\t\t<p>").append(cnt).append("</p>\n")
                .append("\t\t</td>\n")
                .append("\t\t<td>\n")
                .append("\t\t\t<p><font color=").append(result ? "\"green\">Ok" : "\"red\">Fail").append("</font></p>\n")
                .append("\t\t</td>\n")
                .append("\t\t<td />\n")
                .append("\t\t<td />\n")
                .append("\t</tr>\n");
        ++cnt;
    }

    public void setError(String input, String correct, String out){
        if (error.length() == 0){
            error = MESSAGE_1 + cnt + MESSAGE_2;
            error += "\t<tr>\n" +
                    "\t\t<td>" + input + "</td>\n" +
                    "\t\t<td>" + correct + "</td>\n" +
                    "\t\t<td>" + out + "</td\n>" +
                    "\t</tr>\n" +
                    "</table>\n";
        }
    }

    public String getAnswer(){
        return builder.append(error).toString();
    }

    public void clear(){
        builder = new StringBuilder();
        error = "";
        cnt = 1;
    }

    public boolean getStatus() {
        return status;
    }
}
