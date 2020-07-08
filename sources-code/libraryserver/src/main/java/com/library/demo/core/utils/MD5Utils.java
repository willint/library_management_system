package com.library.demo.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import com.library.demo.core.exception.ServiceException;

public class MD5Utils {

  private static final char[] HEX_DIGITS =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
  private static final int HEX_RIGHT_SHIFT_COEFFICIENT = 4;
  private static final int HEX_HIGH_BITS_BITWISE_FLAG = 0x0f;

  /**
   * @Title: MD5 @Description: TODO(MD5加密) @param s @return String 返回类型 @throws
   */
  public final static String MD5(String s) {
    char hexDigits[] =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g'};
    try {
      byte[] strTemp = s.getBytes();
      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
      mdTemp.update(strTemp);
      byte[] md = mdTemp.digest();
      int j = md.length;
      char str[] = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      return null;
    }
  }

  public static String encodePassword(String rawPass) {
    if (rawPass == null || rawPass == "") {
      return null;
    }
    return encodePassword(rawPass, defaultSalt);
  }

  public static String toMD5(String origin) throws RuntimeException {
    if (origin == null || origin.length() < 1) {
      return origin;
    }
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("md5");
    }
    byte[] results = digest.digest(origin.getBytes());
    String md5String = toHex(results);
    return md5String;
  }

  private static String toHex(byte[] results) {
    if (results == null)
      return null;
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < results.length; i++) {
      int hi = (results[i] >> 4) & 0x0f;
      int lo = results[i] & 0x0f;
      hexString.append(Character.forDigit(hi, 16)).append(Character.forDigit(lo, 16));
    }
    return hexString.toString();
  }

  public static String encodePassword(String rawPass, String salt) {
    String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
    MessageDigest messageDigest = getMessageDigest();
    byte[] digest;
    try {
      digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("UTF-8 not supported!");
    }
    return new String(Hex.encodeHex(digest));
  }

  public static boolean isPasswordValid(String encPass, String rawPass) {
    return isPasswordValid(encPass, rawPass, defaultSalt);
  }

  public static String encodePassword16(String plainText, String salt) {
    String strPwd = encodePassword(plainText, salt);
    return strPwd.substring(8, 24);
  }

  public static String encodePassword16(String plainText) {
    String strPwd = encodePassword(plainText, defaultSalt);
    return strPwd.substring(8, 24);
  }

  public static boolean isPasswordValid(String encPass, String rawPass, String salt) {
    if (encPass == null) {
      return false;
    }
    String pass2 = encodePassword(rawPass, salt);
    return encPass.equals(pass2);
  }

  protected static final MessageDigest getMessageDigest() {
    String algorithm = "MD5";
    try {
      return MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("No such algorithm [" + algorithm + "]", e);
    }
  }

  protected static String mergePasswordAndSalt(String password, Object salt, boolean strict) {
    if (strict && (salt != null)
        && ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1))) {
      throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
    }
    if ((salt == null) || "".equals(salt)) {
      return password;
    } else {
      return password + "{" + salt.toString() + "}";
    }
  }

  /**
   * 混淆码。防止破解。
   */
  private static String defaultSalt = "hna-key";

  /**
   * 获得混淆码
   * 
   * @return
   */
  public String getDefaultSalt() {
    return defaultSalt;
  }

  /**
   * 设置混淆码
   * 
   * @param defaultSalt
   */
  public static void setDefaultSalt(String salt) {
    defaultSalt = salt;
  }

  private static String byteArrayToHexString(byte b[]) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++)
      resultSb.append(byteToHexString(b[i]));

    return resultSb.toString();
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  private static final String hexDigits[] =
      {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

  public static String MD5Encode(String origin, String charsetname) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      if (charsetname == null || "".equals(charsetname))
        resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
      else
        resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
    } catch (Exception exception) {
    }
    return resultString;
  }

  private static String getFormattedText(final byte[] bytes) {
    final StringBuilder buf = new StringBuilder(bytes.length * 2);

    for (int j = 0; j < bytes.length; j++) {
      buf.append(
          HEX_DIGITS[(bytes[j] >> HEX_RIGHT_SHIFT_COEFFICIENT) & HEX_HIGH_BITS_BITWISE_FLAG]);
      buf.append(HEX_DIGITS[bytes[j] & HEX_HIGH_BITS_BITWISE_FLAG]);
    }
    return buf.toString();
  }

  public static String encodePayPwd(final byte[] bytes) {

    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] encode = md5.digest(bytes);
      return getFormattedText(encode);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }
  }
  public static String encodePayPwd(final String passwordAndSalt) {
    try {
      return encodePayPwd(passwordAndSalt.getBytes("utf-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }
}
}
