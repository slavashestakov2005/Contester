package com.example.runner;

import com.example.Root;
import com.example.database.rows.ContestLang;
import com.example.database.rows.Lang;
import com.example.database.rows.Task;
import com.example.database.rows.Test;
import com.example.database.tables.ContestsLangsTable;
import com.example.database.tables.LangsTable;
import com.example.database.tables.TasksTable;
import com.example.database.tables.TestsTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RunQuery {
    private static final long compileLimit = 10000;


    private HttpServletRequest request;
    private HttpServletResponse response;
    private String name, surname, code, path, fileName;
    private Task task;
    private int contestId;
    private Lang lang;
    private AnswerWriter answer;
    private long time;
    private ExecutedResult result;


    public RunQuery(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void execute(){
        try {
            init();
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();
            if (!ContestsLangsTable.isExists(new ContestLang(contestId, lang.getId()))){
                pw.print("<center><font color=\"red\" size=\"13\">Запрещённый язык!</font></center>\n" +
                        "<center><button onclick=\"move()\">Назад</button></center>\n" +
                        "<script>\n" +
                        "\tfunction move(){\n" +
                        "\t\tdocument.location.replace(\"" + contestId + "/contest.jsp\");\n" +
                        "\t\treturn false;\n" +
                        "\t}\n" +
                        "</script>");
                System.out.println("Попытка обмана от " + name + " " + surname + "!");
                return;
            }
            System.out.println("POST запрос от " + name + " " + surname + " : " + lang.getEnd1());
            saveFile();
            answer.start();
            if (!compileFile()) result.status = Status.CE;
            else runProgram();
            answer.finish(contestId);
            pw.print(answer.getAnswer());
            Results.add(surname + " " + name, task.getId(), result.status);
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
        task = TasksTable.selectTaskByID(Integer.parseInt(request.getParameterValues("task")[0]));
        path = request.getParameterValues("contest")[0] + "\\" + task.getId();
        fileName = Root.rootDirectory + "\\Contests\\" + path + "\\sendings\\" + Languages.generateFileName(lang, name, surname, time);
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

    protected void runProgram() {
        ArrayList<Test> tests = TestsTable.getTestsForTask(task.getId());
        for (Test test : tests){
            result.start();
            String command = Languages.getExecuteCommand(lang, time);
            System.out.println(command + " -> " + test.getId());
            try {
                if (executeFile(command, test)) {
                    isCorrect(test);
                }
            } catch (Exception ex){
                result.status = Status.CE;
            }
            long runTime = Math.max(lang.getMinTime(), result.endTime - result.startTime - lang.getFreeTime());
            long runMemory = Math.max(lang.getMinMemory(), result.memory - lang.getFreeMemory());
            answer.addTest(result.status, runTime, runMemory);
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
        }
        br.close();

        boolean res = process.waitFor(task.getTimeLimit() + lang.getFreeTime(), TimeUnit.MILLISECONDS);
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
                if (result.memory - lang.getFreeMemory() > 1024 * task.getMemoryLimit()) {
                    result.status = Status.MLE;
                    process.destroy();
                }
                if (result.startTime + lang.getFreeTime() + task.getTimeLimit() < result.endTime){
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
