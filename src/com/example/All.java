package com.example;

import com.example.database.rows.Contest;
import com.example.database.tables.ContestsTable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet("/all")
public class All extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<Contest> contests = ContestsTable.getAll();
        for(Contest contest : contests) System.out.println(contest);
    }
}
