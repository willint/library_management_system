package com.library.demo.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.demo.core.annotation.NoLoginAuth;
import com.library.demo.core.spring.SpringUtil;
import com.library.demo.core.vo.LoginVo;
import com.library.demo.core.vo.RespVo;
import com.library.demo.system.service.SystemLoginService;

@RestController
@RequestMapping("system")
public class SystemLoginController {

  @Autowired
  private SystemLoginService systemLoginService;

  /**
   * 登录接口
   * @param loginVo
   * @return
   */
  @NoLoginAuth
  @RequestMapping("/login")
  public RespVo login(@RequestBody LoginVo loginVo) {
    systemLoginService.login(loginVo.getPhone(), loginVo.getPwd());
    return new RespVo(SpringUtil.getLoginInfo());
  }
}
