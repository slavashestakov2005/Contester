package com.example.runner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RunQuery {
    private static final String root = "C:\\Users\\Public\\Documents\\Contester";
    private static final String fileNameOutput = root + "\\Programs";
    private static final String commandStart = "cmd /c ";


    private HttpServletRequest request;
    private HttpServletResponse response;
    private String name, surname, code, path, fileName;
    private Lang lang;
    private AnswerWriter answer;
    private long time;


    public RunQuery(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void execute(){
        try {
            init();
            System.out.println("POST запрос от " + name + " " + surname + " : " + lang);
            saveFile();
            compileFile();
            answer.start();
            runProgram();
            answer.finish();
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(answer.getAnswer());
            answer.clear();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() throws UnsupportedEncodingException {
        time = System.currentTimeMillis();
        request.setCharacterEncoding("utf-8");
        answer = new AnswerWriter();
        name = request.getParameterValues("name")[0];
        surname = request.getParameterValues("surname")[0];
        code = request.getParameterValues("code")[0];
        lang = Lang.fromString(request.getParameterValues("lang")[0]);
        path = request.getParameterValues("contest")[0] + "\\" + request.getParameterValues("task")[0];
        fileName = root + "\\Contests\\" + path + "\\sendings\\" + Languages.generateFileName(lang, name, surname);
    }

    private void saveFile() throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8"));
        out.write(code);
        out.close();
    }

    private void compileFile() throws IOException {
        Runtime.getRuntime().exec(commandStart + Languages.getCompileCommand(lang, fileName, time));
    }

    protected void runProgram() throws InterruptedException, IOException {
        File[] files = new File(root + "\\Contests\\" + path + "\\tests").listFiles();
        Thread.sleep(10000);    /** for program compile **/
        for (int i = 0; i < files.length; i += 2) {
            String input = files[i].getPath();
            String output = fileNameOutput + "\\" + time + "_" + (i / 2 + 1) + "_out.txt";
            executeFile(Languages.getExecuteCommand(lang, input, output, time));
        }
        Thread.sleep(10000);    /** for program running **/
        for (int i = 0; i < files.length; i += 2) {
            String output = fileNameOutput + "\\" + time + "_" + (i / 2 + 1) + "_out.txt";
            String correctOutput = files[i + 1].getPath();
            System.out.println(output + " " + isCorrect(output, correctOutput));
            Files.delete(Paths.get(output));
        }
        deleteFile();
        Thread.sleep(10000);    /** for files delete **/
    }

    protected boolean isCorrect(String fileName1, String fileName2){
        if (Checker.equals(fileName1, fileName2)){
            answer.addTask(true);
            return true;
        }
        answer.addTask(false);
        return false;
    }

    protected void executeFile(String command) throws IOException {
        System.out.println(command);
        Runtime.getRuntime().exec(commandStart + command);
    }

    protected void deleteFile() throws IOException {
        Files.delete(Paths.get(lang.programName(time)));
    }
}
