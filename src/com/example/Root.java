package com.example;

import java.io.FileInputStream;
import java.io.IOException;

public class Root {
    private static String rootFile = "C:\\Users\\Public\\Documents\\Contester\\root.txt";
    public static String rootDirectory, webDirectory;

    static {
        try {
            FileInputStream in = new FileInputStream(rootFile);
            String lines[] = new String(in.readAllBytes()).split("[\r\n]+");
            rootDirectory = lines[0];
            webDirectory = rootDirectory + "\\apache-tomcat-9.0.27\\webapps\\Contester";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
