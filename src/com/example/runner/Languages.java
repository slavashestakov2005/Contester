package com.example.runner;

public class Languages {
    public static String getCompileCommand(Lang lang, String fileName, long time){
        switch (lang){
            case CPP: return "g++ -static -fno-strict-aliasing -DACMP -lm -s -x c++ -std=c++17 -Wl,--stack=67108864 -O2 -o " + lang.programName(time) + " \"" + fileName + "\"";
            case PY: return "python -c \"import py_compile; py_compile.compile(r'" + fileName + "', r'" + lang.programName(time) + "')\"";
        }
        return null;
    }

    public static String getExecuteCommand(Lang lang, String input, String output, long time){
        switch (lang) {
            case CPP: return lang.programName(time) + " > " + output + " < \"" + input + "\"";
            case PY: return "python " + lang.programName(time) + " > " + output + " < \"" + input + "\"";
        }
        return null;
    }

    public static String generateFileName(Lang lang, String name, String surname, long time){
        return time + "_" + name + "_" + surname + "." + lang;
    }
}
