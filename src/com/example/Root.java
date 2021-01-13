package com.example;

import java.io.File;

public class Root {
    public static final String rootDirectory, webDirectory;

    static {
        rootDirectory = new File("").getAbsoluteFile().getParentFile().getParent();
        webDirectory = rootDirectory + "\\apache-tomcat-9.0.27\\webapps\\Contester";
    }
}
