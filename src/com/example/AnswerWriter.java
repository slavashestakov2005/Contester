package com.example;

public class AnswerWriter {
    private StringBuilder builder;
    private int cnt;

    public AnswerWriter(){
        clear();
    }

    public void fail(){
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

    public void finish(){
        builder.append("</table>\n")
                .append("<br>\n")
                .append("<center><button onclick=\"move()\">Назад</button></center>\n")
                .append("<script>\n")
                .append("\tfunction move(){\n")
                .append("\t\tdocument.location.replace(\"contest.jsp\");\n")
                .append("\t\treturn false;\n")
                .append("\t}\n")
                .append("</script>");
    }

    public void addTask(boolean result){
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

    public String getAnswer(){
        return builder.toString();
    }

    public void clear(){
        builder = new StringBuilder();
        cnt = 1;
    }
}
