package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet("/run")
public class Run extends HttpServlet {
    private static final String commandStart = "cmd /c cd \"C:\\\\MinGW-5.1.0\\bin\" && g++ ";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("POST запрос от " + request.getParameterValues("name")[0] + " " + request.getParameterValues("surname")[0]);
        String code = request.getParameterValues("code")[0];
        saveFile(code);
        compileFile();
    }

    protected void saveFile(String text) throws IOException {
        String file_name = "C:\\\\Users\\Слава\\Desktop\\Работы\\Контестер\\code.cpp";
        File file = new File(file_name);
        //write content
        FileWriter writer = new FileWriter (file);
        writer.write(text);
        writer.close();
    }

    protected void compileFile(){
        try {
            String command = commandStart + "-o \"C:\\\\Users\\Слава\\Desktop\\Работы\\Контестер\\program.exe\" \"C:\\\\Users\\Слава\\Desktop\\Работы\\Контестер\\code.cpp\"";
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {}
    }
}