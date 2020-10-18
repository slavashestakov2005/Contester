package com.example.runner;

import java.util.HashMap;

public class Sendings {
    private static HashMap<String, Integer> sendings = new HashMap<>();

    public static int getCountSendings(String name){
        Integer value = sendings.get(name);
        return value == null ? 0 : value;
    }

    public static void addSending(String name){
        sendings.put(name, getCountSendings(name) + 1);
    }
}
