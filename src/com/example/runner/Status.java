package com.example.runner;

public enum Status{
    OK("Accepted", "green"),
    WA("Wrong answer"),
    CE("Compilation error"),
    RE("Runtime error"),
    MLE("Memory limit exceeded"),
    TLE("Time limit exceeded"),
    FAIL("Fail");

    private String text, color;

    Status(String text, String color) {
        this.text = text;
        this.color = color;
    }

    Status(String text){
        this(text, "red");
    }

    @Override
    public String toString() {
        return text;
    }

    public String htmlString(){
        return String.format("<font color=\"%s\">%s</font>", color, text);
    }
}