package com.example.generator;

import com.example.Root;
import com.example.database.rows.Contest;
import com.example.database.rows.Task;
import com.example.database.rows.Test;
import com.example.database.tables.ContestsTable;
import com.example.database.tables.ContestsTasksTable;
import com.example.database.tables.TestsTable;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Generator {
    public static void start(int contestId) throws IOException {
        System.out.println("Хотите сгенерировать контест №" + contestId);
        Contest contest = ContestsTable.selectContestByID(contestId);
        ArrayList<Task> tasks = ContestsTasksTable.getTasksForContest(contestId);
        deleteOldFiles(contestId);
        createNewDirectories(contestId, tasks);
        MainPageGenerator.generate(contest, tasks);
        for(Task task : tasks){
            TaskPageGenerator.generate(contest, task, tasks, TestsTable.getExampleTestsForTask(task.getId()));
            ArrayList<Test> tests = TestsTable.getTestsForTask(task.getId());
            for(int i = 0; i < tests.size(); ++i){
                saveTest(contestId, task.getId(), tests.get(i).getId(), tests.get(i).getInput());
            }
        }
    }

    public static void deleteOldFiles(int contestId) throws IOException {
        System.out.println("Удаление старых папок");
        Path directory = Paths.get(Root.webDirectory + "\\" + contestId);
        if (Files.exists(directory)) Files.walkFileTree(directory, new DeleteVisitor());
        directory = Paths.get(Root.rootDirectory + "\\Contests\\" + contestId);
        if (Files.exists(directory)) Files.walkFileTree(directory, new DeleteVisitor());
    }

    public static void createNewDirectories(int contestId, ArrayList<Task> tasks) throws IOException {
        System.out.println("Создание новых");
        Files.createDirectories(Paths.get(Root.webDirectory + "\\" + contestId));
        String contestDirectory = Root.rootDirectory + "\\Contests\\" + contestId;
        Files.createDirectories(Paths.get(contestDirectory));
        for(Task task : tasks){
            Files.createDirectories(Paths.get(contestDirectory +  "\\" + task.getId()));
            Files.createDirectories(Paths.get(contestDirectory +  "\\" + task.getId() + "\\sendings"));
            Files.createDirectories(Paths.get(contestDirectory +  "\\" + task.getId() + "\\tests"));
        }
    }

    public static void saveTest(int contestId, int taskId, int testId, String text) throws IOException {
        String path = Root.rootDirectory + "\\Contests\\" + contestId + "\\" + taskId + "\\tests\\" + testId + ".txt";
        System.out.print("Save : " + path);
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), "UTF-8"));
        out.write(text);
        out.close();
        System.out.println(" -> OK");
    }

    public static String toHTML(String text, int tabs){
        String lines[] = text.split("[\r\n]+");
        StringBuilder tabBuilder = new StringBuilder();
        for(int i = 0; i < tabs; ++i) tabBuilder.append("\t");
        String tab = tabBuilder.toString();
        StringBuilder builder = new StringBuilder();
        for(String line : lines){
            builder.append(tab).append("<p>").append(line).append("</p>\n");
        }
        return builder.toString();
    }
}
