package com.example.database.tables;

import com.example.database.Columns;
import com.example.database.DataBaseHelper;
import com.example.database.rows.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContestsTasksTable {
    private static final String table = "contests_tasks";
    public static final Columns columns;

    static {
        columns = new Columns();
        columns.add("CONTEST", 1, "id_contest");   // int      NOT NULL    PK
        columns.add("TASK", 2, "id_task");         // text     NOT NULL    PK
    }

    public static ArrayList<Task> getTasksForContest(int contestId) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            ResultSet resultSet = DataBaseHelper.executeQuery("SELECT " + columns.getName("TASK")+ " FROM " + table + " WHERE " + columns.getName("CONTEST") + " = " + contestId);
            while (resultSet.next()) {
                int taskId = resultSet.getInt(1);
                System.out.println("TASK_ID : " + taskId);
                DataBaseHelper.push();
                tasks.add(TasksTable.selectTaskByID(taskId));
                DataBaseHelper.pop();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
