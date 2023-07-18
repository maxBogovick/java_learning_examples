package com.example.singleton;

import java.util.concurrent.CountDownLatch;

public class TestContainer {

  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    for (int i = 0; i < 100; i++) {
      int finalI = i;
      new Thread(() -> {
        try {
          countDownLatch.await();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        final Container instance = Container.getInstance();
        System.out.println(Thread.currentThread().getName() + " address = " + instance.toString());
        if (instance.getValue() == 0) {
          System.err.println("ERROR with id = " + finalI);
          throw new IllegalArgumentException(String.valueOf(finalI));
        } else {
          System.out.println(Thread.currentThread().getName() + " with id = " + finalI + " created normal");
        }
      }).start();
      new Thread(() -> {
        try {
          countDownLatch.await();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        final Container instance = Container.getInstance();
        System.out.println(Thread.currentThread().getName() + " address = " + instance.toString());
        if (instance.getValue() == 0) {
          System.err.println("ERROR with id = " + finalI);
          throw new IllegalArgumentException(String.valueOf(finalI));
        } else {
          System.out.println(Thread.currentThread().getName() + " with id = " + finalI + " created normal");
        }
      }).start();
    }
    countDownLatch.countDown();
  }
}
