package com.example;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 *
 * Синхронизация может привести к эффекту голодания потоков (Starvation).
 * */
public class StarvationExample
{
  private int value;

  public synchronized int getValue() {
    System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " get value = " + value);
    return value;
  }

  public synchronized void setValue(int value) {
    System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " set value = " + value);
    try {
      TimeUnit.SECONDS.sleep(5L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    this.value = value;
  }

  public static void main(String[] args) {
    StarvationExample testMulti = new StarvationExample();
    new Thread(() -> {
      System.out.println(LocalDateTime.now() + " " +Thread.currentThread().getName() + " start call get");
      int value = testMulti.getValue();
      System.out.println(LocalDateTime.now() + " " +Thread.currentThread().getName() + " end call get value = " + value);
    }).start();

    new Thread(() -> {
      System.out.println(LocalDateTime.now() + " " +Thread.currentThread().getName() + " start call set");
      testMulti.setValue(1);
      System.out.println(LocalDateTime.now() + " " +Thread.currentThread().getName() + " end call set");
    }).start();

    new Thread(() -> {
      System.out.println(LocalDateTime.now() + " " +Thread.currentThread().getName() + " start call set");
      testMulti.setValue(1);
      System.out.println(LocalDateTime.now() + " " +Thread.currentThread().getName() + " end call set");
    }).start();
  }
}
