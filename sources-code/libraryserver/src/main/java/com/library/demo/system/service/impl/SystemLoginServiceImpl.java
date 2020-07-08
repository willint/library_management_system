package com.library.demo.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.demo.core.exception.ServiceException;
import com.library.demo.core.session.LoginInfo;
import com.library.demo.core.spring.SpringUtil;
import com.library.demo.core.utils.MD5Utils;
import com.library.demo.core.vo.MenuTree;
import com.library.demo.system.dao.entity.SystemUser;
import com.library.demo.system.service.SystemLoginService;
import com.library.demo.system.service.SystemMenuService;
import com.library.demo.system.service.SystemUserService;

@Service
public class SystemLoginServiceImpl implements SystemLoginService {

  @Autowired
  private SystemUserService systemUserService;
  
  @Autowired
  private SystemMenuService systemMenuService;
  
  @Override
  public void login(String phone, String pwd) {
    SystemUser systemUser = systemUserService.findUserByPhone(phone);
    if(systemUser == null){
      throw new ServiceException("用户名密码错误");
    }
    String password = systemUser.getPassword();
    if(StringUtils.isBlank(password) || StringUtils.isBlank(pwd)) {
      throw new ServiceException("用户名密码错误");
    }
    pwd = MD5Utils.encodePassword(pwd, systemUser.getSalt());
    if(!password.equals(pwd)) {
      throw new ServiceException("用户名密码错误");
    }
    //密码验证通过
    List<MenuTree> menuTrees = systemMenuService.menu(systemUser.getId(), false);
    List<String> permissions = systemMenuService.findUserPermission(systemUser.getId());
    //设置session
    SpringUtil.setLoginInfo(new LoginInfo(systemUser, menuTrees, permissions));
  }
}
