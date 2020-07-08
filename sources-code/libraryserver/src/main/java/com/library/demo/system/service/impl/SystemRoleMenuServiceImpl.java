package com.library.demo.system.service.impl;
import org.springframework.stereotype.Service;

import com.library.demo.core.service.impl.CommonServiceImpl;
import com.library.demo.system.dao.entity.SystemRoleMenu;
import com.library.demo.system.dao.mapper.SystemRoleMenuMapper;
import com.library.demo.system.service.SystemRoleMenuService;

@Service
public class SystemRoleMenuServiceImpl extends
    CommonServiceImpl<SystemRoleMenuMapper, SystemRoleMenu> implements SystemRoleMenuService {

}
