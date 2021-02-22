package com.example.runner;

import com.example.database.rows.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Results {
    private static HashMap<String, HashMap<Integer, Integer>> array = new HashMap<>();

    public static void add(String name, int task, Status status){
        if (!array.containsKey(name)) array.put(name, new HashMap<>());
        HashMap<Integer, Integer> map = array.get(name);
        if (!map.containsKey(task)) map.put(task, 0);
        int was = map.get(task);
        if (was <= 0){
            if (status == Status.OK) map.put(task, 1 - was);
            else map.put(task, was - 1);
        }
        array.put(name, map);
    }

    public static HashMap<String, HashMap<Integer, Integer>> getForContest(ArrayList<Task> tasks){
        HashMap<String, HashMap<Integer, Integer>> answer = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        for(Task task : tasks) set.add(task.getId());
        for(String name : array.keySet()){
            HashMap<Integer, Integer> tmp = new HashMap<>(), his = array.get(name);
            for(int taskId : his.keySet()){
                if (set.contains(taskId)) tmp.put(taskId, his.get(taskId));
            }
            if (tmp.size() > 0) answer.put(name, tmp);
        }
        return answer;
    }
}
