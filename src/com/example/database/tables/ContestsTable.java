package com.example.database.tables;

import com.example.database.Columns;
import com.example.database.DataBaseHelper;
import com.example.database.rows.Contest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContestsTable {
    private static final String table = "contests";
    public static final Columns columns;

    static {
        columns = new Columns();
        columns.add("ID", 1, "id");             // int      NOT NULL    PK      AI      UNIQUE
        columns.add("NAME", 2, "name");         // text     NOT NULL
        columns.add("ABOUT", 3, "description"); // text
    }

    public static ArrayList<Contest> getAll() {
        ArrayList<Contest> contests = new ArrayList<>();
        try {
            ResultSet resultSet = DataBaseHelper.executeQuery("SELECT * FROM " + table);
            while (resultSet.next()) {
                contests.add(Contest.parseSQL(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contests;
    }
}
