package com.example.forkjoin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchFIles {

  public static void main(String[] args) throws IOException {
    String result = "";
    final LocalDateTime now = LocalDateTime.now();
    System.out.println(now + " start processing");
    try (final Stream<Path> list = Files.list(Path.of("c:", "temp", "index"))) {
      list.forEach(file -> {
        try {
          Files.move(file, Path.of("c:", "temp", "index", UUID.randomUUID().toString()));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    }
    try (final Stream<Path> list = Files.list(Path.of("c:", "temp", "index"))) {
      result = list.map(file -> ForkJoinTask.searchTextInFile(file, "java")).collect(Collectors.joining(","));
    }
    final LocalDateTime end = LocalDateTime.now();
    System.out.println(end + " end processing with result\n" + result);
    System.out.println("duration = " + Duration.between(now, end).toMillis());

  }
}
