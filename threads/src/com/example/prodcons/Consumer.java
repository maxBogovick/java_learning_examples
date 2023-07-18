package com.example.prodcons;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
  private final Queue<Integer> buffer;

  public Consumer(Queue<Integer> buffer) {
    this.buffer = buffer;
  }

  //@Override
  public void run() {
    try {
      while (true) {
        synchronized (buffer) {
          while (buffer.isEmpty()) {
            // Buffer is empty, wait for the producer to produce items
            System.out.println("consumer waiting");
            buffer.wait();
          }
          int value = buffer.poll();
          System.out.println("Consuming: " + value);
          buffer.notifyAll();
        }
        // Simulating delay between consuming items
        Thread.sleep(2000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /*@Override
  public void run() {
    while (true) {
      while (!buffer.isEmpty()) {
        // Buffer is empty, wait for the producer to produce items
        int value = buffer.poll();
        System.out.println("Consuming: " + value);
      }
      try {
        TimeUnit.SECONDS.sleep(2L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

  }*/
}