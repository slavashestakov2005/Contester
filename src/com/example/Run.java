package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/run")
public class Run extends HttpServlet {
    private static final String root = "C:\\Users\\Public\\Documents\\Contester";
    private static final String fileNameCpp = root + "\\Programs\\task.exe",
                                fileNamePy = root + "\\Programs\\task.pyc",
                                fileNameOutput = root + "\\Programs";
    private static final String commandStart = "cmd /c ",
            commandCpp = "g++ -static -fno-strict-aliasing -DACMP -lm -s -x c++ -std=c++17 -Wl,--stack=67108864 -O2 -o ",
            commandPy1 = "python -c \"import py_compile; py_compile.compile(r'", commandPy2 = "',r'", commandPy3 = "')\"";
    private static final String executeCpp = fileNameCpp + " > ",
                                executePy = "python " + fileNamePy + " > ";
    private String result;
    private int cnt;

    protected void addLineToTable(boolean ok){
        result += "\t<tr>\n" +
                "\t\t<td>\n" +
                "\t\t\t<p>" + cnt + "</p>\n" +
                "\t\t</td>\n" +
                "\t\t<td>\n" +
                "\t\t\t<p><font color=" + (ok ? "\"green\">Ok" : "\"red\">Fail") + "</font></p>\n" +
                "\t\t</td>\n" +
                "\t\t<td />\n" +
                "\t\t<td />\n" +
                "\t</tr>\n";
        ++cnt;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        String name = request.getParameterValues("name")[0];
        String surname = request.getParameterValues("surname")[0];
        String code = request.getParameterValues("code")[0];
        String lang = request.getParameterValues("lang")[0];
        String path = request.getParameterValues("contest")[0] + "\\" + request.getParameterValues("task")[0];
        /** save file **/
        System.out.println("POST запрос от " + name + " " + surname + " : " + lang);
        String fileName = getFilePath(path) + "\\" + generateFileName(name, surname, lang);
        saveFile(code, fileName);
        /** compile program **/
        String command = getCompileCommand(fileName, lang);
        System.out.println(command);
        compileFile(command);
        result = "<table border=\"1\" width=\"95%\">\n" +
                "\t<tr>\n" +
                "\t\t<td width=\"25%\"><center>Тест</center></td>\n" +
                "\t\t<td width=\"25%\"><center>Статус</center></td>\n" +
                "\t\t<td width=\"25%\"><center>Время</center></td>\n" +
                "\t\t<td width=\"25%\"><center>Память</center></td>\n" +
                "\t</tr>\n";
        cnt = 1;
        try {
            runProgram(path, lang);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result += "</table>\n" +
                "<br>\n" +
                "<center><a href=\"contest.jsp\">Назад</a></center>";
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(result);
    }

    protected String getFilePath(String path){
        return root + "\\Contests\\" + path + "\\sendings";
    }

    protected String generateFileName(String name, String surname, String lang){
        String fileName = name + "_" + surname;
        Sendings.addSending(fileName);
        fileName += "_" + Sendings.getCountSendings(fileName) + "." + lang;
        return fileName;
    }

    protected void saveFile(String text, String fileName) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(text.getBytes());
        out.close();
    }

    protected String getCompileCommand(String fileName, String lang){
        switch (lang){
            case "cpp": return commandCpp + fileNameCpp + " \"" + fileName + "\"";
            case "py": return commandPy1 + fileName + commandPy2 + fileNamePy + commandPy3;
            default: return "";
        }
    }

    protected void compileFile(String command) throws IOException {
        try {
            Runtime.getRuntime().exec(commandStart + command);
        } catch (IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    protected void runProgram(String path, String lang) throws IOException, InterruptedException {
        File[] files = new File(root + "\\Contests\\" + path + "\\tests").listFiles();
        Thread.sleep(10000);    /** for program compile **/
        for(int i = 0; i < files.length; i += 2){
            String input = files[i].getPath();
            String output = fileNameOutput + "\\" + (i / 2 + 1) + "_out.txt";
            executeFile(input, output, lang);
        }
        Thread.sleep(10000);    /** for program running **/
        for(int i = 0; i < files.length; i += 2){
            String output = fileNameOutput + "\\" + (i / 2 + 1) + "_out.txt";
            String correctOutput = files[i + 1].getPath();
            FileInputStream in1 = new FileInputStream(output), in2 = new FileInputStream(correctOutput);
            output = new String(in1.readAllBytes());
            correctOutput = new String(in2.readAllBytes());
            in1.close(); in2.close();
            System.out.println((i / 2 + 1) + " " + isCorrect(output, correctOutput));
            Files.delete(Paths.get(fileNameOutput + "\\" + (i / 2 + 1) + "_out.txt"));
        }
        Thread.sleep(10000);    /** for files delete **/
    }

    protected boolean isCorrect(String output, String correctOutput){
        System.out.println("'" + output + "' === '" + correctOutput + "'");
        if (output.equals(correctOutput)){
            addLineToTable(true);
            return true;
        }
        addLineToTable(false);
        return false;
    }

    protected void executeFile(String input, String output, String lang){
        String command;
        switch (lang){
            case "cpp": command = executeCpp; break;
            case "py": command = executePy; break;
            default: command = "";
        }
        command += output + " < \"" + input + "\"";
        System.out.println(command);
        try {
            Runtime.getRuntime().exec(commandStart + command);
        } catch (IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
}