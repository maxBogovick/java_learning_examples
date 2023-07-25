package com.example.conccollection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CopyOnWriteArrayListExample1 {
  public static void main(String[] args) throws InterruptedException {
    List<String> taskList = new ArrayList<>();

    for (int i = 0; i < 50; i++) {
      taskList.add("Task " + i);
    }
    // Add some tasks to the list

    // Create a thread pool with multiple threads to execute the tasks
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    // Submit tasks to the thread pool

    new Thread(() -> {

        for (String task : taskList) {
          System.out.println("Executing: " + task);
          // Simulate some work
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

    }).start();


    TimeUnit.MILLISECONDS.sleep(20L);
    System.out.println("additionl tasks");
    // Add a new task to the list while the threads are executing
    taskList.add("Task 1005");
    taskList.add("Task 1006");
    taskList.add("Task 1007");

    TimeUnit.MILLISECONDS.sleep(20L);
    System.out.println("additional tasks");
    // Add a new task to the list while the threads are executing
    taskList.add("Task 2005");
    taskList.add("Task 2006");
    taskList.add("Task 2007");

    // Shutdown the thread pool
    executorService.shutdown();
  }
}
