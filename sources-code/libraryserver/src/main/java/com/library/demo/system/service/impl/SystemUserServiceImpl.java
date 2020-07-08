package com.library.demo.system.service.impl;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.library.demo.core.exception.ServiceException;
import com.library.demo.core.service.impl.CommonServiceImpl;
import com.library.demo.core.utils.CodeUtils;
import com.library.demo.core.utils.MD5Utils;
import com.library.demo.core.vo.UserVo;
import com.library.demo.system.dao.entity.SystemUser;
import com.library.demo.system.dao.entity.SystemUserRole;
import com.library.demo.system.dao.mapper.SystemUserMapper;
import com.library.demo.system.service.SystemUserRoleService;
import com.library.demo.system.service.SystemUserService;

@Transactional
@Service
public class SystemUserServiceImpl extends CommonServiceImpl<SystemUserMapper, SystemUser>
    implements SystemUserService {

  @Autowired
  private SystemUserRoleService systemUserRoleService;

  @Override
  public SystemUser findUserByPhone(String phone) {
    return selectOne(new EntityWrapper<SystemUser>().eq("PHONE", phone));
  }

  @Override
  public boolean saveOrUpdate(UserVo userVo) {
    SystemUser systemUser = null;
    if (StringUtils.isNotBlank(userVo.getId())) {// 修改
      systemUser = selectById(userVo.getId());
    } else {
      systemUser = new SystemUser();
      systemUser.setSalt(CodeUtils.getVerifyCode(6));
      systemUser.setPassword(MD5Utils.encodePassword("123456", systemUser.getSalt()));
    }
    if (systemUser == null) {
      throw new ServiceException("找不到该用户信息");
    }
    systemUser.setEmail(userVo.getEmail());
    systemUser.setPhone(userVo.getPhone());
    systemUser.setStatus(userVo.getStatus());
    systemUser.setUsername(userVo.getUsername());
    systemUser.setUpdateDate(new Date());
    return insertOrUpdate(systemUser);
  }

  @Override
  public void delUser(String userId) {
    systemUserRoleService.delete(new EntityWrapper<SystemUserRole>().eq("USER_ID", userId));
    deleteById(userId);
  }

  @Override
  public void updatePwd(String userId, String password, String oldPassword) {
    SystemUser systemUser = selectById(userId);
    if (systemUser == null) {
      throw new ServiceException("查询不到用户");
    }
    if (StringUtils.isNotBlank(oldPassword) && !systemUser.getPassword()
        .equals(MD5Utils.encodePassword(oldPassword, systemUser.getSalt()))) {// 修改密码
      throw new ServiceException("原密码错误");
    }
    password = MD5Utils.encodePassword(password, systemUser.getSalt());
    int i = baseMapper.updatePwd(password, userId);
    if(i <= 0) {
      throw new ServiceException("密码修改失败");
    }
  }

	@Override
	public boolean saveWithUserId(UserVo userVo) {
		
		
		SystemUser systemUser = new SystemUser();
		// 设置初始密码
		systemUser.setSalt(CodeUtils.getVerifyCode(6));
	    systemUser.setPassword(MD5Utils.encodePassword("123456", systemUser.getSalt()));
	    
		systemUser.setId(userVo.getId());
		
		systemUser.setEmail(userVo.getEmail());
	    systemUser.setPhone(userVo.getPhone());
	    systemUser.setStatus(userVo.getStatus());
	    systemUser.setUsername(userVo.getUsername());
	    systemUser.setUpdateDate(new Date());
	    return insertOrUpdate(systemUser);
	}
}
