package com.example;

public enum Lang {
    CPP("cpp", "exe"),
    PY("py", "pyc");

    private String end1, end2;

    Lang(String end1, String end2) {
        this.end1 = end1;
        this.end2 = end2;
    }

    public String programName(){
        return "C:\\Users\\Public\\Documents\\Contester\\Programs\\task." + end2;
    }

    public static Lang fromString(String end1){
        switch (end1){
            case "cpp": return CPP;
            case "py": return PY;
        }
        return null;
    }

    public String toString(){
        return end1;
    }
}
