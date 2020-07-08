package com.library.demo.system.service;

import java.util.List;
import java.util.Map;

import com.library.demo.core.service.ICommonService;
import com.library.demo.core.vo.MenuTree;
import com.library.demo.system.dao.entity.SystemMenu;

public interface SystemMenuService extends ICommonService<SystemMenu> {

   public List<MenuTree> menu(String userId, boolean isSys);

   public List<String> findUserPermission(String userId);

   public List<Map<String, Object>> menuTreeAll();

   public List<String> findAllMenuIdByRoleId(String userId);

   public Map<String, Object> allMenu();

  void delMenu(List<String> ids);

}
