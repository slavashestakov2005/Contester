package com.example;

public class Languages {
    public static String getCompileCommand(Lang lang, String fileName){
        switch (lang){
            case CPP: return "g++ -static -fno-strict-aliasing -DACMP -lm -s -x c++ -std=c++17 -Wl,--stack=67108864 -O2 -o " + lang.programName() + " \"" + fileName + "\"";
            case PY: return "python -c \"import py_compile; py_compile.compile(r'" + fileName + "', r'" + lang.programName() + "')\"";
        }
        return null;
    }

    public static String getExecuteCommand(Lang lang, String input, String output){
        switch (lang) {
            case CPP: return lang.programName() + " > " + output + " < \"" + input + "\"";
            case PY: return "python " + lang.programName() + " > " + output + " < \"" + input + "\"";
        }
        return null;
    }

    public static String generateFileName(Lang lang, String name, String surname){
        String fileName = name + "_" + surname;
        Sendings.addSending(fileName);
        fileName += "_" + Sendings.getCountSendings(fileName) + "." + lang;
        return fileName;
    }
}
