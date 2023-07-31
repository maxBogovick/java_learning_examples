package com.example.prodcons;

import java.util.concurrent.LinkedTransferQueue;

public class ProducerConsumerExample {
  public static void main(String[] args) {
    LinkedTransferQueue<String> messageQueue = new LinkedTransferQueue<>();

    // Producer thread
    Thread producerThread = new Thread(() -> {

      for (int i = 1; i <= 50; i++) {
        String message = "Message " + i;
        System.out.println("Producer: Adding message = " + message);
        try {

          //messageQueue.tryTransfer(message, 100, TimeUnit.MILLISECONDS); // Try Adds message to the queue and blocks if the queue is full
          messageQueue.transfer(message); // Adds message to the queue and blocks if the queue have a data
          System.out.println("Producer: Successful transfer message = " + message);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    // Consumer thread
    Thread consumerThread = new Thread(() -> {
      try {
        while (true) {
          Thread.sleep(1000);
          String message = messageQueue.take(); // Blocks if the queue is empty
          System.out.println("Consumer: Received " + message);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });// Consumer thread
    Thread consumerThread2 = new Thread(() -> {
      try {
        while (true) {
          Thread.sleep(1000);
          String message = messageQueue.take(); // Blocks if the queue is empty
          System.out.println("Consumer: Received " + message);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    // Start the threads
    producerThread.start();
    consumerThread.start();
    consumerThread2.start();

    // Wait for both threads to finish
    try {
      producerThread.join();
      consumerThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}