package com.example.conccollection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HashMapProblemExample {
  public static void main(String[] args) {
    // Shared HashMap without proper synchronization
    Map<String, Integer> sharedMap = new HashMap<>();

    // Reader thread
    Thread readerThread = new Thread(() -> {
      try {
        while (true) {
          for (String key : sharedMap.keySet()) {
            int value = sharedMap.get(key);
            System.out.println("Reader: Key=" + key + ", Value=" + value);
          }
          TimeUnit.SECONDS.sleep(1);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    // Writer thread
    Thread writerThread = new Thread(() -> {
      try {
        int counter = 1;
        while (true) {
          String key = "Key-" + counter;
          int value = counter;
          sharedMap.put(key, value);
          System.out.println("Writer: Added Key=" + key + ", Value=" + value);
          counter++;
          TimeUnit.SECONDS.sleep(2);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    // Start the threads
    readerThread.start();
    writerThread.start();

    // Let the threads run for some time
    try {
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Stop the threads
    readerThread.interrupt();
    writerThread.interrupt();
  }
}

