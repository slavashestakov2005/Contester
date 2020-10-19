package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

public class DataBaseHelper {
    private static Connection connection;
    private static Stack<Statement> statements;

    static {
        try {
            connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Public\\Documents\\Contester\\database.db");
            statements = new Stack<>();
            statements.push(connection.createStatement());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void execute(String sql){
        try {
            statements.peek().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String sql){
        try {
            return statements.peek().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void push(){
        try {
            statements.push(connection.createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void pop(){
        statements.pop();
    }
}
