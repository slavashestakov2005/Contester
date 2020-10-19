package com.example.database.tables;

import com.example.database.Columns;

public class TasksTable {
    private static final String table = "tasks";
    public static final Columns columns;

    static {
        columns = new Columns();
        columns.add("ID", 1, "id");             // int      NOT NULL    PK      AI      UNIQUE
        columns.add("NAME", 2, "name");         // text     NOT NULL
        columns.add("ABOUT", 3, "description"); // text
        columns.add("INPUT", 3, "input");       // text
        columns.add("OUTPUT", 3, "output");     // text
    }
}
