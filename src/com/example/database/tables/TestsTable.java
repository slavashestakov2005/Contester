package com.example.database.tables;

import com.example.database.Columns;

public class TestsTable {
    private static final String table = "tests";
    public static final Columns columns;

    static {
        columns = new Columns();
        columns.add("TASK", 1, "id_task");      // int      NOT NULL    PK
        columns.add("INPUT", 2, "input");       // text                 PK
        columns.add("OUTPUT", 3, "output");     // text
        columns.add("EXAMPLE", 4, "example");   // text     NOT NULL
        columns.add("PUBLIC", 5, "public");     // text     NOT NULL
    }
}
