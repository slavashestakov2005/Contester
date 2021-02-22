package com.example.runner;

import com.example.Root;
import com.example.database.rows.Lang;

import java.text.MessageFormat;

public class Languages {
    public static String generateProgramName(Lang lang, long time){
        return Root.rootDirectory +  "\\Programs\\" + time + "." + lang.getEnd2();
    }

    public static String getCompileCommand(Lang lang, String fileName, long time){
        return MessageFormat.format(lang.getCompileCommand(), fileName, generateProgramName(lang, time));
    }

    public static String getExecuteCommand(Lang lang, long time){
        return MessageFormat.format(lang.getExecuteCommand(), generateProgramName(lang, time));
    }

    public static String generateFileName2(Lang lang, String name, String surname, long time){
        return time + "_" + name + "_" + surname + "." + lang.getEnd1();
    }
}
