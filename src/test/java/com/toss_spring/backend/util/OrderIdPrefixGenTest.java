package com.toss_spring.backend.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderIdPrefixGenTest {
  @Test
  void generateRandomPrefixOfLength6() {
    String prefix = OrderIdPrefixGen.getOrderIdPrefix(6);
    System.out.println(prefix);
    assert(prefix.length() == 6);
  }
}