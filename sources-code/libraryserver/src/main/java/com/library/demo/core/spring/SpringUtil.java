package com.library.demo.core.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.library.demo.core.session.LoginInfo;
import com.library.demo.core.staticvalue.SessionSettings;

public class SpringUtil {

  public static HttpServletRequest getRequest() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    return request;
  }

  public static String getDomain() {
    return getRequest().getServerName();
  }

  public static HttpSession getSession() {
    return getRequest().getSession();
  }

  public static Object getSession(String name) {
    return getSession().getAttribute(name);
  }

  public static void removeSession(String name) {
    getSession().removeAttribute(name);
  }

  public static void setSession(String name, Object value) {
    getSession().setAttribute(name, value);
  }

  public static void setLoginInfo(LoginInfo loginInfo) {
    setSession(SessionSettings.ADMIN_USER, loginInfo);
  }

  public static LoginInfo getLoginInfo() {
    LoginInfo loginInfo = (LoginInfo) getSession(SessionSettings.ADMIN_USER);
    return loginInfo;
  }

  public static String getIpAddr() {
    HttpServletRequest request = getRequest();
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
