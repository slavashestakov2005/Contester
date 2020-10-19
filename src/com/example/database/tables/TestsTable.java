package com.example.database.tables;

import com.example.database.Columns;
import com.example.database.DataBaseHelper;
import com.example.database.rows.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Test> getTestsForTask(int taskId){
        ArrayList<Test> tests = new ArrayList<>();
        try {
            ResultSet resultSet = DataBaseHelper.executeQuery("SELECT * FROM " + table + " WHERE " + columns.getName("TASK") + " = " + taskId);
            while (resultSet.next()) {
                tests.add(Test.parseSQL(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
}
