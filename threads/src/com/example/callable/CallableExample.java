package com.example.callable;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableExample {

  record TaskResult(Long number, long result){}

  static class Task implements Callable<TaskResult> {

    private final Long number;

    Task(Long number) {
      this.number = number;
    }

    @Override
    public TaskResult call() {
      long result = 0;
      for (int i = 0; i < number; i++) {
        result += i;
      }
      return new TaskResult(number, result);
    }
  }

  public static void main2(String[] args) {
    final Instant start = Instant.now();
    long[] numbers = {1000, 300, 100, 400, 500, 800, 1200, 2000, 2300};
    calcInOneThread(numbers);
    System.out.println(Duration.between(start, Instant.now()));
  }

  private static void calcInOneThread(long[] numbers) {
    List<TaskResult> result = new ArrayList<>();
    for (long number : numbers) {
      long count = 0;
      for (int i = 0; i < number; i++) {
        count += i;
      }
      result.add(new TaskResult(number, count));
      //System.out.println("number = " + number +" count = " + count);
    }
    result.forEach(System.out::println);
  }

  public static void main(String[] args) {
    long[] numbers = {10123451, 30757511, 578676, 4011122, 12000, 100, 2000, 3909028, 49898393, 100, 2000, 3909028, 49898393, 100, 2000, 3909028, 49898393, 100, 2000, 3909028, 49898393};
    Instant start = Instant.now();
    calcInMultiThread(numbers);
    System.out.println(Duration.between(start, Instant.now()));
    start = Instant.now();
    calcInOneThread(numbers);
    System.out.println(Duration.between(start, Instant.now()));
  }

  private static void calcInMultiThread(long[] numbers) {
    List<FutureTask<TaskResult>> tasks = new ArrayList<>();
    for (long number : numbers) {
      FutureTask<TaskResult> task = new FutureTask<>(new Task(number));
      new Thread(task).start();
      tasks.add(task);
    }

    tasks.forEach(task -> {
      try {
        System.out.println(task.get());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    });
  }

}
