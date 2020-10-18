package com.example.runner;

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
    private static final String fileNameOutput = root + "\\Programs";
    private static final String commandStart = "cmd /c ";
    private static AnswerWriter answer = new AnswerWriter();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** get Parameters **/
        request.setCharacterEncoding("utf-8");
        String name = request.getParameterValues("name")[0];
        String surname = request.getParameterValues("surname")[0];
        String code = request.getParameterValues("code")[0];
        Lang lang = Lang.fromString(request.getParameterValues("lang")[0]);
        String path = request.getParameterValues("contest")[0] + "\\" + request.getParameterValues("task")[0];
        /** save file **/
        System.out.println("POST запрос от " + name + " " + surname + " : " + lang);
        String fileName = root + "\\Contests\\" + path + "\\sendings\\" + Languages.generateFileName(lang, name, surname);
        saveFile(code, fileName);
        /** compile and run program **/
        compileFile(Languages.getCompileCommand(lang, fileName));
        answer.start();
        runProgram(path, lang);
        answer.finish();
        /** write answer **/
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(answer.getAnswer());
        answer.clear();
    }

    protected void saveFile(String text, String fileName) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8"));
        out.write(text);
        out.close();
    }

    protected void compileFile(String command) {
        try {
            Runtime.getRuntime().exec(commandStart + command);
        } catch (IOException ex){
            answer.fail();
        }
    }

    protected void runProgram(String path, Lang lang) {
        File[] files = new File(root + "\\Contests\\" + path + "\\tests").listFiles();
        try {
            Thread.sleep(10000);    /** for program compile **/
            for (int i = 0; i < files.length; i += 2) {
                String input = files[i].getPath();
                String output = fileNameOutput + "\\" + (i / 2 + 1) + "_out.txt";
                executeFile(Languages.getExecuteCommand(lang, input, output));
            }
            Thread.sleep(10000);    /** for program running **/
            for (int i = 0; i < files.length; i += 2) {
                String output = fileNameOutput + "\\" + (i / 2 + 1) + "_out.txt";
                String correctOutput = files[i + 1].getPath();
                System.out.println((i / 2 + 1) + " " + isCorrect(output, correctOutput));
                Files.delete(Paths.get(fileNameOutput + "\\" + (i / 2 + 1) + "_out.txt"));
            }
            deleteFile(lang);
            Thread.sleep(10000);    /** for files delete **/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isCorrect(String fileName1, String fileName2){
        if (Checker.equals(fileName1, fileName2)){
            answer.addTask(true);
            return true;
        }
        answer.addTask(false);
        return false;
    }

    protected void executeFile(String command){
        System.out.println(command);
        try {
            Runtime.getRuntime().exec(commandStart + command);
        } catch (IOException ex){
            answer.fail();
        }
    }

    protected void deleteFile(Lang lang) {
        try{
            Files.delete(Paths.get(lang.programName()));
        } catch(IOException e) {
            answer.fail();
        }
    }
}