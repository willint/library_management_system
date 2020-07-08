package com.library.demo.interceptor;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.library.demo.core.annotation.NoLoginAuth;
import com.library.demo.core.annotation.Permission;
import com.library.demo.core.session.LoginInfo;
import com.library.demo.core.spring.SpringUtil;
import com.library.demo.core.staticvalue.HttpStatus;

/**
 * 后台权限拦截器
 *
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod authhandler = (HandlerMethod) handler;
    // 判断是否为无需登录地址
    NoLoginAuth nologin = authhandler.getMethodAnnotation(NoLoginAuth.class);
    if (nologin != null) {// 存在NoLoginAuth注解直接放行
      return true;
    }
    // 需要登录
    if (!isLogin()) {// 未登录
      response.setStatus(HttpStatus.NO_LOGIN);
      return false;
    }

    if (!validatePermission(authhandler)) {// 没有权限访问
      response.setStatus(HttpStatus.NO_PERMISSION);
      return false;
    }
    return true;
  }

  /**
   * 验证用户权限
   */
  private boolean validatePermission(HandlerMethod authhandler) {
    Permission auth = authhandler.getMethodAnnotation(Permission.class);
    if (auth == null) {// 无需验证权限
      return true;
    }
    LoginInfo loginInfo = SpringUtil.getLoginInfo();
    if (loginInfo == null) {// 用户信息为空，没有权限访问
      return false;
    }
    if("1".equals(loginInfo.getUser().getIsSys())) {//系统用户,直接放行
      return true;
    }
    List<String> permissions = loginInfo.getPermissions();
    if (permissions == null || !permissions.contains(auth.key())) {// 用户权限为空或没有该权限
      return false;
    }
    return true;
  }

  /**
   * 判断是否已登录
   * 
   * @param request
   * @return
   */
  private boolean isLogin() {
    LoginInfo loginInfo = SpringUtil.getLoginInfo();
    if (null == loginInfo || null == loginInfo.getUser()) {
      return false;
    } else {
      return true;
    }
  }
}
