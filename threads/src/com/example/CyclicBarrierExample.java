package com.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierExample {
  public static void main(String[] args) {
    final int numberOfWorkers = 12;
    final CyclicBarrier barrier = new CyclicBarrier(3, () ->
        System.out.println("All workers have completed their tasks. Moving to the next phase.")
    );

    for (int i = 0; i < numberOfWorkers; i++) {
      Thread thread = new Thread(new WorkerThread(barrier));
      thread.start();
    }
  }

  private enum ImageTransformationType {RESIZE, CREATE_THUMBNAIL, LOAD_ON_SERVER}

  static class WorkerThread implements Runnable {
    private final CyclicBarrier barrier;

    public WorkerThread(CyclicBarrier barrier) {
      this.barrier = barrier;
    }

    @Override
    public void run() {
      try {
        //for (ImageTransformationType transformationType : ImageTransformationType.values()) {

          // Simulating task execution time
         // Thread.sleep((long) (Math.random() * 2000));
        System.out.println("Worker " + Thread.currentThread() + " is performing task ");
          barrier.await(); // Wait for other workers to complete their tasks
        //}
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }
}