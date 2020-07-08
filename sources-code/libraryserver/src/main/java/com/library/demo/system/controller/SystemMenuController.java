package com.library.demo.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.library.demo.core.annotation.Permission;
import com.library.demo.core.exception.ServiceException;
import com.library.demo.core.session.LoginInfo;
import com.library.demo.core.spring.SpringUtil;
import com.library.demo.core.vo.MenuTree;
import com.library.demo.core.vo.MenuVo;
import com.library.demo.core.vo.RespVo;
import com.library.demo.system.dao.entity.SystemMenu;
import com.library.demo.system.dao.entity.SystemRoleMenu;
import com.library.demo.system.service.SystemMenuService;
import com.library.demo.system.service.SystemRoleMenuService;

@RestController
@RequestMapping("system/menu")
public class SystemMenuController {

  @Autowired
  private SystemMenuService systemMenuService;

  @Autowired
  private SystemRoleMenuService systemRoleMenuService;

  @RequestMapping("/menuList")
  public RespVo menuList() {
    LoginInfo loginInfo = SpringUtil.getLoginInfo();
    List<MenuTree> list = systemMenuService.menu(loginInfo.getUser().getId(),
        "1".equals(loginInfo.getUser().getIsSys()));
    return new RespVo(list);
  }

  @Permission(key = "role:menu")
  @RequestMapping("/roleMenuAndAllMenu")
  public RespVo roleMenuAndAllMenu(@RequestBody MenuVo menuVo) {
    List<String> myMenu = systemMenuService.findAllMenuIdByRoleId(menuVo.getRoleId());
    List<Map<String, Object>> menuTree = systemMenuService.menuTreeAll();
    Map<String, Object> body = new HashMap<>();
    body.put("myMenu", myMenu);
    body.put("menuTree", menuTree);
    return new RespVo(body);
  }

  @Permission(key = "role:menu")
  @RequestMapping("/addRoleMenu")
  public RespVo addRoleMenu(@RequestBody MenuVo menuVo) {
    String roleId = menuVo.getRoleId();
    List<SystemRoleMenu> entityList = new ArrayList<>();
    for (String menuId : menuVo.getIds()) {
      SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
      systemRoleMenu.setRoleId(roleId);
      systemRoleMenu.setMenuId(menuId);
      entityList.add(systemRoleMenu);
    }
    boolean delFlag =
        systemRoleMenuService.delete(new EntityWrapper<SystemRoleMenu>().eq("ROLE_ID", roleId));
    if (!delFlag) {
      return new RespVo("-1", "操作异常");
    }
    if (entityList.size() > 0) {
      systemRoleMenuService.insertBatch(entityList);
    }
    return new RespVo();
  }

  @RequestMapping("/allMenu")
  @Permission(key = "menu:all")
  public RespVo allMenu() {
    Map<String, Object> menuTree = systemMenuService.allMenu();
    return new RespVo(menuTree);
  }

  @RequestMapping("/addMenu")
  @Permission(key = "menu:all")
  public RespVo addMenu(@RequestBody MenuVo menuVo) {
    SystemMenu systemMenu = null;
    if (StringUtils.isNotBlank(menuVo.getId())) {
      systemMenu = systemMenuService.selectById(menuVo.getId());
    } else {
      systemMenu = new SystemMenu();
    }
    if (systemMenu == null) {
      throw new ServiceException("找不到该用户信息");
    }
    systemMenu.setTitle(menuVo.getTitle());
    systemMenu.setDescription(menuVo.getDescription());
    systemMenu.setIndex(menuVo.getIndex());
    systemMenu.setParent(menuVo.getParent());
    systemMenu.setPermission(menuVo.getPermission());
    systemMenu.setSeq(Integer.parseInt(menuVo.getSeq()));
    systemMenu.setState(Integer.parseInt(menuVo.getState()));
    systemMenu.setType(Integer.parseInt(menuVo.getType()));
    systemMenu.setIcon(menuVo.getIcon());
    boolean saveFlag = systemMenuService.insertOrUpdate(systemMenu);
    if (saveFlag) {
      return new RespVo();
    } else {
      return new RespVo("-1", "操作失败");
    }
  }

  @RequestMapping("/delMenu")
  @Permission(key = "menu:all")
  public RespVo delMenu(@RequestBody MenuVo menuVo) {
    systemMenuService.delMenu(menuVo.getIds());
    return new RespVo();
  }
}
