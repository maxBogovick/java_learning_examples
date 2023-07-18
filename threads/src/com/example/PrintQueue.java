package com.example;

import java.util.concurrent.Semaphore;

// Task - create program for printing data from only a limited number of threads
/**
 * Семафор
 * Semaphore класс позволяет контролировать доступ к общему ресурсу, ограничивая количество одновременных потоков.
 * Он поддерживает установленное количество разрешений, и каждый acquire() вызов потока
 * использует разрешение, в то время как каждый release() вызов
 * возвращает разрешение.
 * В этом примере Semaphore с указанным количеством разрешений ограничивается количество потоков, которые могут
 * printDocument() одновременно вызывать метод. Если достигнуто максимальное количество разрешений, дополнительные потоки будут
 * заблокированы до тех пор, пока разрешение не станет доступным через acquire() метод. Как только поток завершает печать, он
 * освобождает разрешение с помощью release() метода, разрешая печать другому потоку.
 * */

public class PrintQueue {
  private final Semaphore semaphore;

  public PrintQueue(int maxConcurrentPrinters) {
    semaphore = new Semaphore(maxConcurrentPrinters);
  }

  public void printDocument(String document) throws InterruptedException {
    semaphore.acquire();
    // Simulate printing
    System.out.println("Printing: " + document);
    Thread.sleep(1000); // Simulating print time
    semaphore.release();
  }

  public static void main(String[] args) {
    PrintQueue printQueue = new PrintQueue(2);
    for (int i = 0; i < 20; i++) {
      final int documentId = i;
      new Thread(() -> {
        try {
          printQueue.printDocument(String.valueOf(documentId));
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }).start();
    }
  }
}
