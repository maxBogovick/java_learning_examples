package com.example;

public class UserThread extends Thread {

  private final String name;
  private final int tickets;
  private int bookedTicketCount;

  private final TicketBookSystem ticketBookSystem;

  UserThread(String name, int tickets, TicketBookSystem ticketBookSystem) {
    this.name = name;
    this.tickets = tickets;
    this.ticketBookSystem = ticketBookSystem;
  }

  @Override
  public void run() {
    while (bookedTicketCount < tickets) {
      bookedTicketCount += ticketBookSystem.bookTicket();
      ticketBookSystem.getCountDownLatch().countDown();
    }
  }

  public int getBookedTicketCount() {
    return bookedTicketCount;
  }

  @Override
  public String toString() {
    return "UserThread{" +
        "name='" + name + '\'' +
        ", tickets=" + tickets +
        ", bookedTicketCount=" + bookedTicketCount +
        ", ticketBookSystem=" + ticketBookSystem +
        '}';
  }
}