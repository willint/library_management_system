package com.library.demo.system.service;

import com.library.demo.core.service.ICommonService;
import com.library.demo.core.vo.UserVo;
import com.library.demo.system.dao.entity.SystemUser;

public interface SystemUserService extends ICommonService<SystemUser> {

  public SystemUser findUserByPhone(String phone);

  public boolean saveOrUpdate(UserVo userVo);

  public void delUser(String userId);

  public void updatePwd(String userId, String password, String oldPassword);
  
  public boolean saveWithUserId(UserVo userVo);

}
