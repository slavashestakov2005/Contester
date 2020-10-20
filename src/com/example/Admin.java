package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Admin {
    private static String adminNameFile = "C:\\Users\\Public\\Documents\\Contester\\admin_name.txt";
    private static String adminName, adminSurname;

    static {
        try {
            FileInputStream in = new FileInputStream(adminNameFile);
            String lines[] = new String(in.readAllBytes()).split("[\r\n]+");
            adminName = lines[0];
            adminSurname = lines[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAdminName() {
        return adminName;
    }

    public static String getAdminSurname() {
        return adminSurname;
    }

    public static boolean checkUser(String name, String surname){
        return adminName.equals(name) && adminSurname.equals(surname);
    }
}