package com.library.demo.system.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.demo.core.annotation.Permission;
import com.library.demo.core.http.PageResponse;
import com.library.demo.core.page.Page;
import com.library.demo.core.vo.PageRequestVo;
import com.library.demo.core.vo.RespVo;
import com.library.demo.core.vo.RoleVo;
import com.library.demo.system.dao.entity.SystemRole;
import com.library.demo.system.dao.entity.SystemUserRole;
import com.library.demo.system.service.SystemRoleService;
import com.library.demo.system.service.SystemUserRoleService;

@RestController
@RequestMapping("system/role")
public class SystemRoleController {

  @Autowired
  private SystemRoleService systemRoleService;
  
  @Autowired
  private SystemUserRoleService systemUserRoleService;

  @Permission(key = "role:list")
  @RequestMapping("/pageList")
  public PageResponse<SystemRole> pageList(@RequestBody PageRequestVo pageRequestVo) {
    Page<SystemRole> page = systemRoleService.list(pageRequestVo,
        systemRoleService.maptoWrapper(pageRequestVo.getFromData()));
    PageResponse<SystemRole> pageResponse = new PageResponse<>(page);
    return pageResponse;
  }

  @Permission(key = "role:add")
  @RequestMapping("/saveOrUpdate")
  public RespVo saveRole(@RequestBody RoleVo roleVo) {
    boolean saveFlag = systemRoleService.saveOrUpdate(roleVo);
    if (saveFlag) {
      return new RespVo();
    } else {
      return new RespVo("-1", "新增用户失败");
    }
  }

  @Permission(key = "role:del")
  @RequestMapping("/del")
  public RespVo del(@RequestBody RoleVo roleVo) {
    systemRoleService.delRole(roleVo.getId());
    return new RespVo();
  }

  @Permission(key = "role:userRole")
  @RequestMapping("/userRole")
  public RespVo userRole(@RequestBody RoleVo roleVo) {
    List<SystemRole> userRole = systemRoleService.userRole(roleVo.getUserId());
    List<SystemRole> userNoRole = systemRoleService.userNoRole(roleVo.getUserId());
    Map<String, List<SystemRole>> body = new HashMap<>();
    body.put("userRole", userRole);
    body.put("userNoRole", userNoRole);
    return new RespVo(body);
  }
  
  @Permission(key = "role:userRole")
  @RequestMapping("/addUserRole")
  public RespVo addUserRole(@RequestBody RoleVo roleVo) {
    SystemUserRole entity = new SystemUserRole();
    entity.setRoleId(roleVo.getId());
    entity.setUserId(roleVo.getUserId());
    boolean saveFlag = systemUserRoleService.insert(entity);
    if (saveFlag) {
      return new RespVo();
    } else {
      return new RespVo("-1", "添加用户角色失败");
    }
  }
  @Permission(key = "role:userRole")
  @RequestMapping("/delUserRole")
  public RespVo delUserRole(@RequestBody RoleVo roleVo) {
    SystemUserRole entity = new SystemUserRole();
    entity.setRoleId(roleVo.getId());
    entity.setUserId(roleVo.getUserId());
    Map<String,Object> columnMap = new HashMap<>();
    columnMap.put("ROLE_ID", roleVo.getId());
    columnMap.put("USER_ID", roleVo.getUserId());
    boolean saveFlag = systemUserRoleService.deleteByMap(columnMap);
    if (saveFlag) {
      return new RespVo();
    } else {
      return new RespVo("-1", "添加用户角色失败");
    }
  }
}
