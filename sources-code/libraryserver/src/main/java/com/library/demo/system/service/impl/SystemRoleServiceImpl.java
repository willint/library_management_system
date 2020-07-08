package com.library.demo.system.service.impl;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.library.demo.core.exception.ServiceException;
import com.library.demo.core.service.impl.CommonServiceImpl;
import com.library.demo.core.vo.RoleVo;
import com.library.demo.system.dao.entity.SystemRole;
import com.library.demo.system.dao.entity.SystemRoleMenu;
import com.library.demo.system.dao.entity.SystemUserRole;
import com.library.demo.system.dao.mapper.SystemRoleMapper;
import com.library.demo.system.service.SystemRoleMenuService;
import com.library.demo.system.service.SystemRoleService;
import com.library.demo.system.service.SystemUserRoleService;

@Transactional
@Service
public class SystemRoleServiceImpl extends CommonServiceImpl<SystemRoleMapper, SystemRole>
    implements SystemRoleService {
  
  @Autowired
  private SystemRoleMenuService systemRoleMenuService;
  
  @Autowired
  private SystemUserRoleService systemUserRoleService;

  @Override
  public boolean saveOrUpdate(RoleVo roleVo) {
    SystemRole systemRole = null;
    if (StringUtils.isNotBlank(roleVo.getId())) {// 修改
      systemRole = selectById(roleVo.getId());
    } else {
      systemRole = new SystemRole();
    }
    if (systemRole == null) {
      throw new ServiceException("找不到该角色信息");
    }
    systemRole.setName(roleVo.getName());
    systemRole.setRemarks(roleVo.getRemarks());
    return insertOrUpdate(systemRole);
  }
  
  /**
   * 用户角色
   */
  @Override
  public List<SystemRole> userRole(String userId) {
    return baseMapper.findRoleByUserId(userId);
  }
  
  /**
   * 非用户角色
   * @param userId
   * @return
   */
  @Override
  public List<SystemRole> userNoRole(String userId) {
    return baseMapper.findRoleNoUserId(userId);
  }
  
  @Override
  public void delRole(String id) {
    systemRoleMenuService.delete(new EntityWrapper<SystemRoleMenu>().eq("ROLE_ID", id));
    systemUserRoleService.delete(new EntityWrapper<SystemUserRole>().eq("ROLE_ID", id));
    deleteById(id);
  }
}
