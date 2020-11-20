package com.example.database.rows;

import com.example.database.DataBaseHelper;
import com.example.database.tables.LangsTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Lang {
    private int id;
    private String name, end1, end2, compileCommand, executeCommand;

    private Lang(boolean type, int id, String name, String end1, String end2, String compileCommand, String executeCommand) {
        this.id = id;
        this.name = name;
        this.end1 = end1;
        this.end2 = end2;
        this.compileCommand = compileCommand;
        this.executeCommand = executeCommand;
    }

    public Lang(int id, String name, String end1, String end2, String compileCommand, String executeCommand) {
        this.id = id;
        this.name = DataBaseHelper.toSQL(name);
        this.end1 = DataBaseHelper.toSQL(end1);
        this.end2 = DataBaseHelper.toSQL(end2);
        this.compileCommand = DataBaseHelper.toSQL(compileCommand);
        this.executeCommand = DataBaseHelper.toSQL(executeCommand);
    }

    public Lang(String name, String end1, String end2, String compileCommand, String executeCommand) {
        this.name = DataBaseHelper.toSQL(name);
        this.end1 = DataBaseHelper.toSQL(end1);
        this.end2 = DataBaseHelper.toSQL(end2);
        this.compileCommand = DataBaseHelper.toSQL(compileCommand);
        this.executeCommand = DataBaseHelper.toSQL(executeCommand);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnd1() {
        return end1;
    }

    public String getEnd2() {
        return end2;
    }

    public String getCompileCommand() {
        return compileCommand;
    }

    public String getExecuteCommand() {
        return executeCommand;
    }

    public static Lang parseSQL(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(LangsTable.columns.getIndex("ID"));
        String name = resultSet.getString(LangsTable.columns.getIndex("NAME"));
        String end1 = resultSet.getString(LangsTable.columns.getIndex("END1"));
        String end2 = resultSet.getString(LangsTable.columns.getIndex("END2"));
        String compile = resultSet.getString(LangsTable.columns.getIndex("COMPILE"));
        String execute = resultSet.getString(LangsTable.columns.getIndex("EXECUTE"));
        return new Lang(false, id, name, end1, end2, compile, execute);
    }

    @Override
    public String toString() {
        return "Lang{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", end1='" + end1 + '\'' +
                ", end2='" + end2 + '\'' +
                ", compileCommand='" + compileCommand + '\'' +
                ", executeCommand='" + executeCommand + '\'' +
                '}';
    }

    public String insertString() {
        return "(" + LangsTable.columns.getName("NAME") + ", " +
                LangsTable.columns.getName("END1") + ", " +
                LangsTable.columns.getName("END2") + ", " +
                LangsTable.columns.getName("COMPILE") + ", " +
                LangsTable.columns.getName("EXECUTE") + ") VALUES ('" +
                name + "', '" +  end1 + "', '" + end2 + "', '" +  compileCommand + "', '" + executeCommand + "')";
    }

    public String updateString() {
        return LangsTable.columns.getName("NAME") + " = '" + name + "', " +
                LangsTable.columns.getName("END1") + " = '" + end1 + "', " +
                LangsTable.columns.getName("END2") + " = '" + end2 + "', " +
                LangsTable.columns.getName("COMPILE") + " = '" + compileCommand + "', " +
                LangsTable.columns.getName("EXECUTE") + " = '" + executeCommand + "'";
    }
}
