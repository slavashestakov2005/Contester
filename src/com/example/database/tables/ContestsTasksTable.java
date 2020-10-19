package com.example.database.tables;

import com.example.database.Columns;

public class ContestsTasksTable {
    private static final String table = "contests_tasks";
    public static final Columns columns;

    static {
        columns = new Columns();
        columns.add("CONTEST", 1, "id_contest");   // int      NOT NULL    PK
        columns.add("TASK", 2, "id_task");         // text     NOT NULL    PK
    }
}
