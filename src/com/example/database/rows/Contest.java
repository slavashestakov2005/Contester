package com.example.database.rows;

import com.example.database.tables.ContestsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Contest {
    private int id;
    private String name, about;

    public Contest(int id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public Contest(String name, String about) {
        this.name = name;
        this.about = about;
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

    public static Contest parseSQL(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ContestsTable.columns.getIndex("ID"));
        String name = resultSet.getString(ContestsTable.columns.getIndex("NAME"));
        String about = resultSet.getString(ContestsTable.columns.getIndex("ABOUT"));
        return new Contest(id, name, about);
    }

    @Override
    public String toString() {
        return "Contest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
