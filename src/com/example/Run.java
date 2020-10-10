package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet("/run")
public class Run extends HttpServlet {
    private static final String root = "C:\\Users\\Public\\Documents\\Contester";
    private static final String fileNameCpp = root + "\\Programs\\task.exe",
                                fileNamePy = root + "\\Programs\\task.pyc",
                                fileNameOutput = root + "\\Programs\\out.txt";
    private static final String commandStart = "cmd /c ",
            commandCpp = "g++ -static -fno-strict-aliasing -DACMP -lm -s -x c++ -std=c++17 -Wl,--stack=67108864 -O2 -o ",
            commandPy1 = "python -c \"import py_compile; py_compile.compile(r'", commandPy2 = "',r'", commandPy3 = "')\"";
    private static final String executeCpp = fileNameCpp + " > " + fileNameOutput,
                                executePy = "python " + fileNamePy + " > " + fileNameOutput;

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
        runProgram(path, lang);
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

    protected void runProgram(String path, String lang){
        File[] files = new File(root + "\\Contests\\" + path + "\\tests").listFiles();
        for(int i = 0; i < files.length; i += 2){
            executeFile(files[i].getPath(), lang);
        }
    }

    protected void executeFile(String input, String lang){
        String command;
        switch (lang){
            case "cpp": command = executeCpp; break;
            case "py": command = executePy; break;
            default: command = "";
        }
        command += " < \"" + input + "\"";
        System.out.println(command);
        try {
            Runtime.getRuntime().exec(commandStart + command);
        } catch (IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
}