package com.example.runner;

import com.example.Root;
import com.example.database.rows.Lang;
import com.example.database.rows.Test;
import com.example.database.tables.LangsTable;
import com.example.database.tables.TestsTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class RunQuery {
    private static final String fileNameOutput = Root.rootDirectory + "\\Programs";
    private static final long freeTime = 290;


    private HttpServletRequest request;
    private HttpServletResponse response;
    private String name, surname, code, path, fileName;
    private int task, contestId;
    private Lang lang;
    private AnswerWriter answer;
    private long time, timeLimit = 1000 + freeTime, memoryLimit = 16 * 1024, compileLimit = 10000;
    private ExecutedResult result;


    public RunQuery(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void execute(){
        try {
            init();
            System.out.println("POST запрос от " + name + " " + surname + " : " + lang.getEnd1());
            saveFile();
            answer.start();
            if (!compileFile()) result.status = Status.CE;
            else runProgram();
            answer.finish(contestId);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.print(answer.getAnswer());
            Results.add(surname + " " + name, task, result.status);
            answer.clear();
        } catch (Exception e) {
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
        lang = LangsTable.getLang(request.getParameterValues("lang")[0]);
        contestId = Integer.parseInt(request.getParameterValues("contest")[0]);
        task = Integer.parseInt(request.getParameterValues("task")[0]);
        path = request.getParameterValues("contest")[0] + "\\" + task;
        fileName = Root.rootDirectory + "\\Contests\\" + path + "\\sendings\\" + Languages.generateFileName2(lang, name, surname, time);
        result = new ExecutedResult();
    }

    private void saveFile() throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8"));
        out.write(code);
        out.close();
    }

    private boolean compileFile() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(Languages.getCompileCommand(lang, fileName, time));
        return process.waitFor(compileLimit, TimeUnit.MILLISECONDS);
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
        for (File file : files) {
            result.start();
            String input = file.getPath();
            String output = fileNameOutput + "\\" + time + "_" + file.getName();
            String command = Languages.getExecuteCommand(lang, time);
            System.out.println(command + " > " + output + " < " + input);
            int testId = Integer.parseInt(file.getName().split("\\.")[0]);
            Test test = TestsTable.selectTestByID(testId);
            if (executeFile(command, test)) {
                System.out.println(output + " " + isCorrect(test));
            }
            answer.addTest(result.status, result.endTime - result.startTime - freeTime, result.memory);
        }
        deleteFile();
    }

    protected boolean executeFile(String command, Test test) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        long pid = process.pid();
        System.out.println("PID : " + pid);
        RunTask runTask = new RunTask(process);
        runTask.start();

        OutputStream outputStream = process.getOutputStream();
        outputStream.write(test.getInput().getBytes());
        outputStream.close();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
            result.memory = Math.max(result.memory, TaskListParser.getMemory(pid));
        }
        br.close();

        boolean res = process.waitFor(timeLimit, TimeUnit.MILLISECONDS);
        runTask.join();
        result.text = lines.toArray(new String[0]);
        if (result.status == Status.OK) {
            if (!res) {
                result.status = Status.TLE;
                process.destroy();
            } else if (process.exitValue() != 0) result.status = Status.RE;
        }
        return result.status == Status.OK;
    }

    protected boolean isCorrect(Test test){
        String[] correctAnswer = test.getOutput().split("\\s+");
        if (Checker.check(correctAnswer, result.text)){
            result.status = Status.OK;
            return true;
        }
        else{
            if (test.isPublic()){
                answer.setError(test.getInput(), test.getOutput(), fromArray());
            }
            result.status = Status.WA;
            return false;
        }
    }

    private String fromArray(){
        StringBuilder builder = new StringBuilder();
        for(String line : result.text) builder.append(line).append("<br/>\n");
        return builder.toString();
    }

    protected void deleteFile() {
        safetyDeleteFile(Languages.generateProgramName(lang, time));
    }

    protected void safetyDeleteFile(String name){
        try {
            Files.delete(Paths.get(name));
        } catch (IOException e) {
            System.err.println("Нет удаляемого файла!");
        }
    }

    private static class ExecutedResult{
        public long startTime, endTime, memory;
        public Status status;
        public String[] text;

        public void start(){
            startTime = endTime = memory = 0;
            status = Status.OK;
            text = null;
        }
    }

    private class RunTask extends Thread{
        Process process;
        long pid;

        public RunTask(Process process) {
            this.process = process;
            this.pid = process.pid();
        }

        public void run() {
            while (!process.isAlive()) try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.startTime = System.currentTimeMillis();
            System.out.println("#pid | start " + pid + " | " + result.startTime);
            TaskListParser.addNew();
            while (process.isAlive()) try {
                result.memory = Math.max(result.memory, TaskListParser.getMemory(pid));
                result.endTime = System.currentTimeMillis();
                System.out.println("#pid | end " + pid + " | " + result.endTime);
                if (result.memory > memoryLimit) {
                    result.status = Status.MLE;
                    process.destroy();
                }
                if (result.startTime + timeLimit < result.endTime){
                    result.status = Status.TLE;
                    process.destroy();
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
