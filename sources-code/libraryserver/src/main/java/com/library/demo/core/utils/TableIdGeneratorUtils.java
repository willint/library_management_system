package com.library.demo.core.utils;
import java.util.UUID;

public class TableIdGeneratorUtils {

  public static String getId() {
    return UUID.randomUUID().toString().replace("-", "").toUpperCase();
  }
}
