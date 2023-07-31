package com.example.forkjoin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTask extends RecursiveTask<String> {

  private final List<Path> files;
  private final String searchText;

  public ForkJoinTask(List<Path> files, String searchText) {
    this.files = files;
    this.searchText = searchText;
  }


  @Override
  protected String compute() {
    if (files == null || files.isEmpty()) {
      return null;
    } else if (files.size() == 1) {
      return searchTextInFile(files.get(0), searchText);
    }
    final List<Path> left = files.subList(0, files.size() / 2);
    final List<Path> right = files.subList(files.size() / 2, files.size());

    ForkJoinTask leftTask = new ForkJoinTask(left, searchText);
    ForkJoinTask rightTask = new ForkJoinTask(right, searchText);
    leftTask.fork();
    final String rightValue = rightTask.compute();
    final String leftValue = leftTask.join();
    return rightValue == null ? leftValue : (leftValue == null ? rightValue : rightValue + ", " + leftValue);
  }

  public static String searchTextInFile(Path file, String searchText) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains(searchText)) {
          return file.toString();
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
    final LocalDateTime now = LocalDateTime.now();
    System.out.println(now + " start processing");
    ForkJoinTask forkJoinTask = new ForkJoinTask(Files.list(Path.of("c:", "temp", "index")).toList(), "java");
    final LocalDateTime end = LocalDateTime.now();
    System.out.println(end + " end processing " + ForkJoinPool.commonPool().submit(forkJoinTask).get());
    System.out.println("duration = " + Duration.between(now, end).toMillis());
  }
}
