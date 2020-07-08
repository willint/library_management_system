package com.library.demo.system.service;

import java.util.List;

import com.library.demo.core.service.ICommonService;
import com.library.demo.core.vo.RoleVo;
import com.library.demo.system.dao.entity.SystemRole;

public interface SystemRoleService extends ICommonService<SystemRole> {

  public boolean saveOrUpdate(RoleVo roleVo);

  public List<SystemRole> userRole(String userId);

  public List<SystemRole> userNoRole(String userId);

  public void delRole(String id);

}
