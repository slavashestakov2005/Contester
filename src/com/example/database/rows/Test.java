package com.example.database.rows;

import com.example.database.tables.TestsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    private int task;
    private String input, output;
    private boolean isExample, isPrivate;

    public Test(int task, String input, String output, boolean isExample, boolean isPrivate) {
        this.task = task;
        this.input = input;
        this.output = output;
        this.isExample = isExample;
        this.isPrivate = isPrivate;
    }

    public int getTask() {
        return task;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public boolean isExample() {
        return isExample;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public static Test parseSQL(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(TestsTable.columns.getIndex("TASK"));
        String input = resultSet.getString(TestsTable.columns.getIndex("INPUT"));
        String output = resultSet.getString(TestsTable.columns.getIndex("OUTPUT"));
        boolean isExample = resultSet.getInt(TestsTable.columns.getIndex("EXAMPLE")) == 1;
        boolean isPrivate = resultSet.getInt(TestsTable.columns.getIndex("PUBLIC")) == 1;
        return new Test(id, input, output, isExample, isPrivate);
    }

    @Override
    public String toString() {
        return "Test{" +
                "task=" + task +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", isExample=" + isExample +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
