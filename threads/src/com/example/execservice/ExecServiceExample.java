package com.example.execservice;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecServiceExample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    final ThreadFactory threadFactory = new ThreadFactory() {
      @Override
      public Thread newThread(Runnable r) {
        System.out.println("Custom thread created");
        return new Thread(r);
      }
    };
    ExecutorService service = Executors.newFixedThreadPool(3, threadFactory);

    service.submit(() -> {
      System.out.println("run method");
      throw new IllegalArgumentException("Error in thread " + Thread.currentThread().getName());
    });

    final Future<Integer> submit1 = service.submit(() -> new Random().nextInt(100));
    final Future<Integer> submit2 = service.submit(() -> {
      throw new IllegalArgumentException("Error in thread " + Thread.currentThread().getName());
    });
    final Future<Integer> submit3 = service.submit(() -> new Random().nextInt(100));
    /*final List<Future<Object>> futures = service.invokeAll(List.of(() -> new Random().nextInt(100),
        () -> {
          throw new IllegalArgumentException("Error in thread " + Thread.currentThread().getName());
        },
        () -> new Random().nextInt(100)
    ));
    futures.forEach(item -> {
      try {
        System.out.println(item.get());
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
    });*/
    /*System.out.println(submit1.get());
    System.out.println(submit2.get());
    System.out.println(submit3.get());*/
    TimeUnit.SECONDS.sleep(2L);
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
    scheduledExecutorService.schedule(() -> {
      System.out.println("run one task");
    }, 1L, TimeUnit.SECONDS);
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      try {
        TimeUnit.SECONDS.sleep(10L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println(LocalDateTime.now() + " scheduleAtFixedRate start task periodicaly");
    }, 0L, 1L, TimeUnit.MINUTES);
    scheduledExecutorService.scheduleWithFixedDelay(() -> {
      try {
        TimeUnit.SECONDS.sleep(10L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.println(LocalDateTime.now() + " scheduleWithFixedDelay start task periodicaly");
    }, 1L, 1L, TimeUnit.MINUTES);
  }

}
