package com.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch
 * CountDownLatch представляет собой конструкцию синхронизации, предоставляемую пакетом java.util.concurrent. Это позволяет
 * одному или нескольким потокам ожидать завершения набора операций, выполняемых другими потоками. Он инициализируется
 * счетчиком, и каждый вызов метода countDown() уменьшает счетчик. Потоки, вызывающие await() метод, будут блокироваться до тех пор,
 * пока счетчик не достигнет нуля.
 * */

public class TicketBookSystem {

  //private volatile int availableTickets = 10000;
  private AtomicInteger availableTickets = new AtomicInteger(10000);
  private CountDownLatch countDownLatch = new CountDownLatch(availableTickets.get());

  public static void main(String[] args) throws InterruptedException {
    TicketBookSystem ticketBookSystem = new TicketBookSystem();
    UserThread user1 = new UserThread("user1", 3000, ticketBookSystem);
    UserThread user2 = new UserThread("user2", 1000, ticketBookSystem);
    UserThread user3 = new UserThread("user3", 3000, ticketBookSystem);
    UserThread user4 = new UserThread("user4", 3000, ticketBookSystem);
    user1.start();
    user2.start();
    user3.start();
    user4.start();
    /*user1.join();
    user2.join();
    user3.join();
    user4.join();*/
    ticketBookSystem.getCountDownLatch().await();
    System.out.println(ticketBookSystem.availableTickets);
    System.out.println(user1);
    System.out.println(user2);
    System.out.println(user3);
    System.out.println(user4);
  }

  public CountDownLatch getCountDownLatch() {
    return countDownLatch;
  }

  public int bookTicket() {
    //synchronized (this) {
      availableTickets.decrementAndGet();
    //}
    return 1;
  }

}
