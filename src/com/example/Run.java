package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet("/run")
public class Run extends HttpServlet {
    private static final String commandStart = "cmd /c cd \"C:\\\\MinGW-5.1.0\\bin\" && g++ ";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameterValues("name")[0];
        String surname = request.getParameterValues("surname")[0];
        String code = request.getParameterValues("code")[0];
        String lang = request.getParameterValues("lang")[0];
        String path = request.getParameterValues("contest")[0] + "\\" + request.getParameterValues("task")[0];
        System.out.println("POST запрос от " + name + " " + surname + " : " + lang);
        saveFile(code, getFilePath(path) + "\\" + generateFileName(name, surname, lang));
        // compileFile();
    }

    protected String getFilePath(String path){
        return "C:\\\\Users\\Слава\\Desktop\\Работы\\Контестер\\Пример\\" + path + "\\sendings";
    }

    protected String generateFileName(String name, String surname, String lang){
        String fileName = name + "_" + surname;
        Sendings.addSending(fileName);
        fileName += "_" + Sendings.getCountSendings(fileName) + "." + lang;
        return fileName;
    }

    protected void saveFile(String text, String file_name) throws IOException {
        FileOutputStream out = new FileOutputStream(file_name);
        out.write(text.getBytes());
        out.close();
    }

    protected void compileFile() throws IOException {
        String command = commandStart + "-o \"C:\\\\Users\\Слава\\Desktop\\Работы\\Контестер\\program.exe\" \"C:\\\\Users\\Слава\\Desktop\\Работы\\Контестер\\code.cpp\"";
        Runtime.getRuntime().exec(command);
    }
}