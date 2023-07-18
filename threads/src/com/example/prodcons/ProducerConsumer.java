package com.example.prodcons;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
  public static void main(String[] args) {
    final int MAX_SIZE = 5;
    Queue<Integer> buffer = new LinkedList<>();

    Thread producerThread = new Thread(new Producer(buffer, MAX_SIZE));
    Thread consumerThread = new Thread(new Consumer(buffer));
    Thread consumerThread2 = new Thread(new Consumer(buffer));
    Thread consumerThread3 = new Thread(new Consumer(buffer));

    producerThread.start();
    consumerThread.start();
    //consumerThread2.start();
    //consumerThread3.start();
  }




}
