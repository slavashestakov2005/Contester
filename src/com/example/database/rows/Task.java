package com.example.database.rows;

import com.example.database.DataBaseHelper;
import com.example.database.tables.TasksTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {
    private int id;
    private String name, about, input, output, solution;

    private Task(boolean type, int id, String name, String about, String input, String output, String solution) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.input = input;
        this.output = output;
        this.solution = solution;
    }

    public Task(int id, String name, String about, String input, String output, String solution) {
        this.id = id;
        this.name = DataBaseHelper.toSQL(name);
        this.about = DataBaseHelper.toSQL(about);
        this.input = DataBaseHelper.toSQL(input);
        this.output = DataBaseHelper.toSQL(output);
        this.solution = DataBaseHelper.toSQL(solution);
    }

    public Task(String name, String about, String input, String output, String solution) {
        this.name = DataBaseHelper.toSQL(name);
        this.about = DataBaseHelper.toSQL(about);
        this.input = DataBaseHelper.toSQL(input);
        this.output = DataBaseHelper.toSQL(output);
        this.solution = DataBaseHelper.toSQL(solution);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getSolution() {
        return solution;
    }

    public static Task parseSQL(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(TasksTable.columns.getIndex("ID"));
        String name = resultSet.getString(TasksTable.columns.getIndex("NAME"));
        String about = resultSet.getString(TasksTable.columns.getIndex("ABOUT"));
        String input = resultSet.getString(TasksTable.columns.getIndex("INPUT"));
        String output = resultSet.getString(TasksTable.columns.getIndex("OUTPUT"));
        String solution = resultSet.getString(TasksTable.columns.getIndex("SOLUTION"));
        return new Task(false, id, name, about, input, output, solution);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", solution='" + solution + '\'' +
                '}';
    }

    public String updateString(){
        return TasksTable.columns.getName("NAME") + " = '" + name + "', " +
                TasksTable.columns.getName("ABOUT") + " = '" + about + "', " +
                TasksTable.columns.getName("INPUT") + " = '" + input + "', " +
                TasksTable.columns.getName("OUTPUT") + " = '" + output + "', " +
                TasksTable.columns.getName("SOLUTION") + " = '" + solution + "'";
    }

    public String insertString() {
        return "(" + TasksTable.columns.getName("NAME") + ", " +
                TasksTable.columns.getName("ABOUT") + ", " +
                TasksTable.columns.getName("INPUT") + ", " +
                TasksTable.columns.getName("OUTPUT") + ", " +
                TasksTable.columns.getName("SOLUTION") + ") VALUES ('" +
                name + "', '" +  about + "', '" + input + "', '" + output + "', '" + solution + "')";
    }
}
