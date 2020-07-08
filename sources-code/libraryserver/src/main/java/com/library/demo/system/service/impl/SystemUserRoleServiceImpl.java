package com.library.demo.system.service.impl;
import org.springframework.stereotype.Service;

import com.library.demo.core.service.impl.CommonServiceImpl;
import com.library.demo.system.dao.entity.SystemUserRole;
import com.library.demo.system.dao.mapper.SystemUserRoleMapper;
import com.library.demo.system.service.SystemUserRoleService;

@Service
public class SystemUserRoleServiceImpl extends
    CommonServiceImpl<SystemUserRoleMapper, SystemUserRole> implements SystemUserRoleService {


}
