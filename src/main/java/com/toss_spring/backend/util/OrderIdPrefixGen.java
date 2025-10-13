package com.toss_spring.backend.util;

import java.security.SecureRandom;

public class OrderIdPrefixGen {
  private static final String CHARS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
  private static final SecureRandom secureRandom = new SecureRandom();

  public static String getOrderIdPrefix(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(CHARS.charAt(secureRandom.nextInt(CHARS.length())));
    }
    return sb.toString();
  }
}
