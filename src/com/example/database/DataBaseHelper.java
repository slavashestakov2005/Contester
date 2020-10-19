package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseHelper {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resSet;

    static {
        try {
            connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Public\\Documents\\Contester\\database.db");
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void execute(String sql){
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String sql){
        try {
            resSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public static void CreateDB(){
        execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
        execute("CREATE TABLE if not exists 'contests' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'description' text);");
        System.out.println("Таблица создана или уже существует.");
    }

    public static void WriteDB() {
        execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
        execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
        execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");

        execute("INSERT INTO 'contests' ('name', 'description') VALUES ('Контест №1', 'Описание контеста №1'); ");
        execute("INSERT INTO 'contests' ('name') VALUES ('Контест №2 без описания'); ");
        execute("INSERT INTO 'contests' ('name', 'description') VALUES ('3', 'description 3'); ");

        System.out.println("Таблица заполнена");
    }

    public static void ReadDB(){
        try {
            executeQuery("SELECT * FROM users");
            while (resSet.next()) {
                int id = resSet.getInt("id");
                String name = resSet.getString("name");
                String phone = resSet.getString("phone");
                System.out.println("ID = " + id);
                System.out.println("name = " + name);
                System.out.println("phone = " + phone);
                System.out.println();
            }
            System.out.println("Таблица выведена");
            executeQuery("SELECT * FROM contests");
            while (resSet.next()) {
                int id = resSet.getInt(1);
                String name = resSet.getString(2);
                String description = resSet.getString(3);
                System.out.println("ID = " + id);
                System.out.println("name = " + name);
                System.out.println("description = " + description);
                System.out.println();
            }
            System.out.println("Таблица выведена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CloseDB() {
        try {
            connection.close();
            statement.close();
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
