package com.library.demo.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.library.demo.core.service.impl.CommonServiceImpl;
import com.library.demo.core.vo.MenuTree;
import com.library.demo.system.dao.entity.SystemMenu;
import com.library.demo.system.dao.entity.SystemRoleMenu;
import com.library.demo.system.dao.mapper.SystemMenuMapper;
import com.library.demo.system.service.SystemMenuService;
import com.library.demo.system.service.SystemRoleMenuService;

@Transactional
@Service
public class SystemMenuServiceImpl extends CommonServiceImpl<SystemMenuMapper, SystemMenu>
    implements SystemMenuService {

  @Autowired
  private SystemRoleMenuService systemRoleMenuService;

  @Override
  public List<String> findUserPermission(String userId) {
    return baseMapper.findPermissionByUserId(userId);
  }

  @Override
  public List<MenuTree> menu(String userId, boolean isSys) {
    List<SystemMenu> systemMenuList = null;
    if (isSys) {
      systemMenuList = baseMapper.findSysMenu();
    } else {
      systemMenuList = baseMapper.findMenuByUserId(userId);
    }
    // 格式化菜单树
    List<MenuTree> lists = new ArrayList<MenuTree>();
    for (SystemMenu systemMenu : systemMenuList) {
      MenuTree childTree = new MenuTree();
      if (systemMenu.getState() == 0) {
        continue;
      }
      if ("0".equals(systemMenu.getParent())) {
        childTree.setIcon(systemMenu.getIcon());
        childTree.setIndex(systemMenu.getIndex());
        childTree.setTitle(systemMenu.getTitle());
        List<MenuTree> menuChild = menuChild(systemMenu.getId(), systemMenuList);
        if (menuChild != null && menuChild.size() > 0) {
          childTree.setSubs(menuChild);
        }
        lists.add(childTree);
      }
    }
    return lists;
  }

  private List<MenuTree> menuChild(String id, List<SystemMenu> systemMenuList) {
    List<MenuTree> lists = new ArrayList<MenuTree>();
    for (SystemMenu systemMenu : systemMenuList) {
      if (systemMenu.getState() == 0) {
        continue;
      }
      MenuTree childTree = new MenuTree();
      if (systemMenu.getParent().equals(id)) {// 父菜单为当前菜单
        childTree.setIcon(systemMenu.getIcon());
        childTree.setIndex(systemMenu.getIndex());
        childTree.setTitle(systemMenu.getTitle());
        List<MenuTree> menuChild = menuChild(systemMenu.getId(), systemMenuList);
        if (menuChild != null && menuChild.size() > 0) {
          childTree.setSubs(menuChild);
        }
        lists.add(childTree);
      }
    }
    return lists;
  }

  @Override
  public List<String> findAllMenuIdByRoleId(String userId) {
    return baseMapper.findAllMenuIdByRoleId(userId);
  }

  @Override
  public List<Map<String, Object>> menuTreeAll() {
    EntityWrapper<SystemMenu> wrapper = new EntityWrapper<SystemMenu>();
    wrapper.eq("C_STATE", "1");
    List<SystemMenu> systemMenuList = baseMapper.selectList(wrapper);
    // 格式化菜单树
    List<Map<String, Object>> lists = new ArrayList<>();
    for (SystemMenu systemMenu : systemMenuList) {
      Map<String, Object> childTree = new HashMap<>();
      if ("0".equals(systemMenu.getParent())) {
        childTree.put("id", systemMenu.getId());
        childTree.put("label", systemMenu.getTitle());
        List<Map<String, Object>> menuChild = menuChildTree(systemMenu.getId(), systemMenuList);
        if (menuChild != null && menuChild.size() > 0) {
          childTree.put("children", menuChild);
        }
        lists.add(childTree);
      }
    }
    return lists;
  }

  @Override
  public Map<String, Object> allMenu() {
    EntityWrapper<SystemMenu> wrapper = new EntityWrapper<SystemMenu>();
    List<SystemMenu> systemMenuList = baseMapper.selectList(wrapper);
    // 格式化菜单树
    List<Map<String, Object>> lists = new ArrayList<>();
    Map<String, Object> menuMap = new HashMap<>();
    for (SystemMenu systemMenu : systemMenuList) {
      menuMap.put(systemMenu.getId(), systemMenu);
      Map<String, Object> childTree = new HashMap<>();
      if ("0".equals(systemMenu.getParent())) {
        childTree.put("id", systemMenu.getId());
        childTree.put("label", systemMenu.getTitle());
        childTree.put("parent", systemMenu.getParent());
        List<Map<String, Object>> menuChild = menuChildTree(systemMenu.getId(), systemMenuList);
        if (menuChild != null && menuChild.size() > 0) {
          childTree.put("children", menuChild);
        }
        lists.add(childTree);
      }
    }
    Map<String, Object> result = new HashMap<>();
    result.put("menuMap", menuMap);
    result.put("lists", lists);
    return result;
  }

  private List<Map<String, Object>> menuChildTree(String id, List<SystemMenu> systemMenuList) {
    List<Map<String, Object>> lists = new ArrayList<>();
    for (SystemMenu systemMenu : systemMenuList) {
      if (systemMenu.getParent().equals(id)) {// 父菜单为当前菜单
        Map<String, Object> childTree = new HashMap<>();
        childTree.put("id", systemMenu.getId());
        childTree.put("label", systemMenu.getTitle());
        childTree.put("parent", systemMenu.getParent());
        List<Map<String, Object>> menuChild = menuChildTree(systemMenu.getId(), systemMenuList);
        if (menuChild != null && menuChild.size() > 0) {
          childTree.put("children", menuChild);
        }
        lists.add(childTree);
      }
    }
    return lists;
  }

  @Override
  public void delMenu(List<String> ids) {
    for (String id : ids) {
      systemRoleMenuService.delete(new EntityWrapper<SystemRoleMenu>().eq("ROLE_ID", id));
    }
    deleteBatchIds(ids);
  }
}
