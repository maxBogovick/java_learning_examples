package com.example.singleton;

public class Container {

  private int value = 1;

  public int getValue() {
    return value;
  }

  private Container() {
  }

  private static Container INSTANCE;

  public static Container getInstance() {
    if (INSTANCE == null) {
      synchronized (Container.class) {
        if (INSTANCE == null) {
          INSTANCE = new Container();
        }
      }
    }
    return INSTANCE;
  }

}
