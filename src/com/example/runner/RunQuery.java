package com.example.runner;

import com.example.Root;
import com.example.database.rows.Test;
import com.example.database.tables.TestsTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class RunQuery {
    private static final String fileNameOutput = Root.rootDirectory + "\\Programs";
    private static final String commandStart = "cmd /c ";


    private HttpServletRequest request;
    private HttpServletResponse response;
    private String name, surname, code, path, fileName;
    private int contestId;
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
            answer.finish(contestId);
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
        contestId = Integer.parseInt(request.getParameterValues("contest")[0]);
        path = request.getParameterValues("contest")[0] + "\\" + request.getParameterValues("task")[0];
        fileName = Root.rootDirectory + "\\Contests\\" + path + "\\sendings\\" + Languages.generateFileName(lang, name, surname, time);
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
        File[] files = new File(Root.rootDirectory + "\\Contests\\" + path + "\\tests").listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                String name1 = o1.getName().replaceFirst("[.][^.]+$", "");
                String name2 = o2.getName().replaceFirst("[.][^.]+$", "");
                return Long.compare(Long.parseLong(name1), Long.parseLong(name2));
            }
        });
        Thread.sleep(10000);    /** for program compile **/
        for (int i = 0; i < files.length; ++i) {
            String input = files[i].getPath();
            String output = fileNameOutput + "\\" + time + "_" + files[i].getName();
            executeFile(Languages.getExecuteCommand(lang, input, output, time));
        }
        Thread.sleep(10000);    /** for program running **/
        for (int i = 0; i < files.length; ++i) {
            String output = fileNameOutput + "\\" + time + "_" + files[i].getName();
            System.out.println(output + " " + isCorrect(output, files[i].getName().split("\\.")[0]));
            Files.delete(Paths.get(output));
        }
        deleteFile();
    }

    protected boolean isCorrect(String outputFile, String testId){
        Test test = TestsTable.selectTestByID(Integer.parseInt(testId));
        String[] correctAnswer = test.getOutput().split("\\s+");
        if (Checker.equals(correctAnswer, outputFile)){
            answer.addTest(true);
            return true;
        }
        else{
            if (test.isPublic()){
                String output = Checker.read(outputFile);
                answer.setError(test.getInput(), test.getOutput(), output);
            }
            answer.addTest(false);
            return false;
        }
    }

    protected void executeFile(String command) throws IOException {
        System.out.println(command);
        Runtime.getRuntime().exec(commandStart + command);
    }

    protected void deleteFile() throws IOException {
        Files.delete(Paths.get(lang.programName(time)));
    }
}
