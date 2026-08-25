package com.example.flight_booking.util;

import java.security.SecureRandom;

public final class PnrGeneratorUtil {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int PNR_LENGTH = 6;
  private static final SecureRandom SECURE_RANDOM = new SecureRandom();

  private PnrGeneratorUtil() {
  }

  public static String generateRandomPnr() {
    StringBuilder pnrBuilder = new StringBuilder(PNR_LENGTH);
    for (int i = 0; i < PNR_LENGTH; i++) {
      int index = SECURE_RANDOM.nextInt(CHARACTERS.length());
      pnrBuilder.append(CHARACTERS.charAt(index));
    }
    return pnrBuilder.toString();
  }
}
