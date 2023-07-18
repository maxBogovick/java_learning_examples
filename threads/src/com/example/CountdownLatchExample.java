package com.example;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch — универсальный инструмент синхронизации, который можно использовать для различных целей.
 * CountDownLatch, инициализированный счетчиком, равным единице, служит простой защелкой включения/выключения
 * или воротами: все потоки, вызывающие await, ждут в воротах, пока они не будут открыты потоком,
 * вызывающим countDown. CountDownLatch, инициализированный значением N, может использоваться для того,
 * чтобы заставить один поток ждать, пока N потоков не завершат какое-либо действие или какое-то действие
 * не будет выполнено N раз.
 * */

public class CountdownLatchExample {

  public static void main(String[] args) {
    CountDownLatch start = new CountDownLatch(1);

    new Thread(() -> doWork(start)).start();
    new Thread(() -> doWork(start)).start();
    new Thread(() -> doWork(start)).start();
    new Thread(() -> doWork(start)).start();
    System.out.println("START!!!!!");
    start.countDown();
  }

  private static void doWork(CountDownLatch start) {
    try {
      start.await();
      System.out.println(Thread.currentThread().getName() + " is start run");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
