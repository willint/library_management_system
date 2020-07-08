package com.library.demo.system.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.demo.core.annotation.Permission;
import com.library.demo.core.http.PageResponse;
import com.library.demo.core.page.Page;
import com.library.demo.core.spring.SpringUtil;
import com.library.demo.core.vo.PageRequestVo;
import com.library.demo.core.vo.RespVo;
import com.library.demo.core.vo.UserVo;
import com.library.demo.system.dao.entity.SystemUser;
import com.library.demo.system.service.SystemUserService;

@RestController
@RequestMapping("system/user")
public class SystemUserController {

  @Autowired
  private SystemUserService systemUserService;

  @Permission(key = "user:list")
  @RequestMapping("/pageList")
  public PageResponse<SystemUser> pageList(@RequestBody PageRequestVo pageRequestVo) {
    Page<SystemUser> page = systemUserService.list(pageRequestVo,
        systemUserService.maptoWrapper(pageRequestVo.getFromData()));
    PageResponse<SystemUser> pageResponse = new PageResponse<>(page);
    return pageResponse;
  }

  @Permission(key = "user:add")
  @RequestMapping("/saveOrUpdate")
  public RespVo saveUser(@RequestBody UserVo userVo) {
    boolean saveFlag = systemUserService.saveOrUpdate(userVo);
    if (saveFlag) {
      return new RespVo();
    } else {
      return new RespVo("-1", "新增用户失败");
    }
  }

  @Permission(key = "user:del")
  @RequestMapping("/del")
  public RespVo del(@RequestBody UserVo userVo) {
    systemUserService.delUser(userVo.getId());
    return new RespVo();
  }

  @RequestMapping("/updateMyPwd")
  public RespVo updateMyPwd(@RequestBody UserVo userVo) {
    if (StringUtils.isBlank(userVo.getOldPassword())) {
      return new RespVo("-1", "请输入原密码");
    }
    systemUserService.updatePwd(SpringUtil.getLoginInfo().getUser().getId(), userVo.getPassword(),
        userVo.getOldPassword());
    return new RespVo();
  }

  @Permission(key = "user:resetpwd")
  @RequestMapping("/resetPwd")
  public RespVo resetPwd(@RequestBody UserVo userVo) {
    systemUserService.updatePwd(userVo.getId(), userVo.getPassword(), null);
    return new RespVo();
  }
}
