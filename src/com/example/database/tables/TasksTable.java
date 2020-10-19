package com.example.database.tables;

import com.example.database.Columns;
import com.example.database.DataBaseHelper;
import com.example.database.rows.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TasksTable {
    private static final String table = "tasks";
    public static final Columns columns;

    static {
        columns = new Columns();
        columns.add("ID", 1, "id");             // int      NOT NULL    PK      AI      UNIQUE
        columns.add("NAME", 2, "name");         // text     NOT NULL
        columns.add("ABOUT", 3, "description"); // text
        columns.add("INPUT", 4, "input");       // text
        columns.add("OUTPUT", 5, "output");     // text
    }

    public static Task selectTaskByID(int taskId){
        Task task = null;
        try {
            ResultSet resultSet = DataBaseHelper.executeQuery("SELECT * FROM " + table + " WHERE " + columns.getName("ID") + " = " + taskId);
            if (resultSet.next()) {
                task = Task.parseSQL(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    public static void updateTaskByID(Task task){
        DataBaseHelper.execute("UPDATE " + table + " SET " + task.updateString() + " WHERE " + columns.getName("ID") + " = " + task.getId());
    }
}
