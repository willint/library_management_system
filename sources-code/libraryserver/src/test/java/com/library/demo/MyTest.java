package com.library.demo;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.demo.system.dao.entity.SystemTest;
import com.library.demo.system.dao.mapper.SystemMapper;
import com.library.demo.system.dao.mapper.SystemUserMapper;
import com.library.demo.system.service.SystemUserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

	@Resource
	private SystemMapper systemMapper;

	@Resource
	private SystemUserMapper systemUserMapper;
	
	@Autowired
	private SystemUserService systemUserService;
	
	

	@Test
	public void TestH2() {
		List<SystemTest> list = systemMapper.selectBySql("select * from mytest");
//		 for(SystemTest t :list) {
//			 
//			 System.out.println(t.getId());
//			 System.out.println(t.getName());
//		 }
//		Wrapper<SystemUser> wrapper = new EntityWrapper<SystemUser>();
//		wrapper.eq("STATUS", "1");
//		List<SystemUser> userList = systemUserMapper.selectList(wrapper);
//		for (SystemUser u : userList) {
//
//			System.out.println(u.getId());
//			System.out.println(u.getUsername());
//		}
		
		
//		systemUserService.selectUserPage(page, "1");
		
	}

}
