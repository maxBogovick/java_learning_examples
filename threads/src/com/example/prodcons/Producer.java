package com.example.prodcons;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
  private final Queue<Integer> buffer;
  private final int maxSize;

  public Producer(Queue<Integer> buffer, int maxSize) {
    this.buffer = buffer;
    this.maxSize = maxSize;
  }

  public void run() {
    try {
      int value = 0;
      while (true) {
        synchronized (buffer) {
          while (buffer.size() == maxSize) {
            // Buffer is full, wait for the consumer to consume items
            buffer.wait();
          }
          System.out.println("Producing: " + value);
          buffer.add(value++);
          buffer.notifyAll();
        }
        // Simulating delay between producing items
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
