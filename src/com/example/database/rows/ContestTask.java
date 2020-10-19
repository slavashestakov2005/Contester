package com.example.database.rows;

import com.example.database.tables.TestsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContestTask {
    private int contest, task;

    public ContestTask(int contest, int task) {
        this.contest = contest;
        this.task = task;
    }

    public int getContest() {
        return contest;
    }

    public int getTask() {
        return task;
    }

    public static ContestTask parseSQL(ResultSet resultSet) throws SQLException {
        int contest = resultSet.getInt(TestsTable.columns.getIndex("CONTEST"));
        int task = resultSet.getInt(TestsTable.columns.getIndex("TASK"));
        return new ContestTask(contest, task);
    }

    @Override
    public String toString() {
        return "ContestTask{" +
                "contest=" + contest +
                ", task=" + task +
                '}';
    }
}
