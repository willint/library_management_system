INSERT INTO `system_user` VALUES ('032aa76fe0294af78fd1d6b1f816f639', 'admin', '系统管理员', 'a5a6c71cd64d9d772626628c01407029', 'ZP5WHJ', '', '1', '2019-4-17', '1', '2019-4-17');
INSERT INTO `system_user` VALUES ('1686ca2636c241c3b92fb3c7081f3379', 'libsys01', '图书馆查询人员', 'e35c1b9611f1f45e9ea851e40fe80ead', '37AEZ6', NULL, '1', '2020-7-8', '0', '2020-7-8');
INSERT INTO `system_user` VALUES ('0147f539a67945ef9d29ef7bd33dac4d', 'libsysadmin', '图书馆管理员', '2eb5591e4cd3fc713ec4ad946e221a0f', 'OXJNAQ', NULL, '1', '2020-7-8', '0', '2020-7-8');
INSERT INTO `system_user_role` VALUES ('b173901eb8e146228c718bae8c4dafdc', '0147f539a67945ef9d29ef7bd33dac4d', '831977f6121e47769018ed3ca3029ef3');
INSERT INTO `system_user_role` VALUES ('f41f3d00d2e640978e4f32480d5b51cf', '1686ca2636c241c3b92fb3c7081f3379', '766a814d34924cf5b72dbf4b404090ad');


INSERT INTO `system_menu` VALUES ('84A69752FFA142C3B7A7797ECFA07F56', '2019-3-21', '首页', 'el-icon-lx-home', 'dashboard', '0', 'index', 1, 1, '首页', 0);
INSERT INTO `system_menu` VALUES ('C210C3FE7F9B43398B01E4EB3268643C', '2019-3-21', '系统管理', 'el-icon-lx-settings', 'C210C3FE7F9B43398B01E4EB3268643C', '0', 'system', 99, 1, '系统管理', 0);
INSERT INTO `system_menu` VALUES ('a0260e7c303043ed9478f9120e856752', NULL, NULL, 'el-icon-document-copy', NULL, '0', 'book:list', 12, 1, '图书管理', 0);
INSERT INTO `system_menu` VALUES ('36D8DB985D274E008C9EC7C5054EC21B', '2019-3-21', '菜单管理', '', 'menu', 'C210C3FE7F9B43398B01E4EB3268643C', 'menu:all', 3, 1, '菜单管理', 0);
INSERT INTO `system_menu` VALUES ('7A4D4FB2B598438D9B6588831099D994', '2019-3-21', '用户管理', '', 'user', 'C210C3FE7F9B43398B01E4EB3268643C', 'user:list', 1, 1, '用户管理', 0);
INSERT INTO `system_menu` VALUES ('87E4540EC6AB48D292B20EFE519E1B31', '2019-3-21', '角色管理', '', 'role', 'C210C3FE7F9B43398B01E4EB3268643C', 'role:list', 2, 1, '角色管理', 0);
INSERT INTO `system_menu` VALUES ('6a188f332a7b4720a5eedce6f850d63e', NULL, NULL, NULL, 'book', 'a0260e7c303043ed9478f9120e856752', 'book:list', 1, 1, '图书列表', 0);
INSERT INTO `system_menu` VALUES ('1144C0587F084FF2891051667537385D', NULL, NULL, '', '', '87E4540EC6AB48D292B20EFE519E1B31', 'role:userRole', 0, 1, '用户角色', 1);
INSERT INTO `system_menu` VALUES ('3420F426635E46D3B97CB6197BD5C8EF', NULL, NULL, '', '', '87E4540EC6AB48D292B20EFE519E1B31', 'role:menu', 0, 0, '角色菜单', 1);
INSERT INTO `system_menu` VALUES ('43AD80E745D74265A45EAD7035121920', NULL, NULL, '', '', '87E4540EC6AB48D292B20EFE519E1B31', 'role:add', 0, 1, '新增', 1);
INSERT INTO `system_menu` VALUES ('57787D13258D419B83770BD61C23F51A', NULL, NULL, '', '', '7A4D4FB2B598438D9B6588831099D994', 'user:del', 0, 1, '删除', 1);
INSERT INTO `system_menu` VALUES ('96335C4A75BF40799FE7DBAC3544CA23', NULL, NULL, '', '', '7A4D4FB2B598438D9B6588831099D994', 'user:add', 0, 1, '新增', 1);
INSERT INTO `system_menu` VALUES ('CFEE087B0A114D18B40FBC4F9714A4FE', NULL, NULL, '', '', '87E4540EC6AB48D292B20EFE519E1B31', 'role:edit', 0, 1, '编辑', 1);
INSERT INTO `system_menu` VALUES ('E1A8B44AE8BC4FE4BA13777EE0E3EC8F', NULL, NULL, '', '', '87E4540EC6AB48D292B20EFE519E1B31', 'role:del', 0, 1, '删除', 1);
INSERT INTO `system_menu` VALUES ('EA1322EBA3AC4A9897547548654619C4', NULL, NULL, '', '', '7A4D4FB2B598438D9B6588831099D994', 'user:edit', 0, 1, '编辑', 1);
INSERT INTO `system_menu` VALUES ('cd7aaa0875694f11858c7aecc8e005c8', NULL, NULL, NULL, NULL, '6a188f332a7b4720a5eedce6f850d63e', 'book:add', 1, 1, '新增', 1);
INSERT INTO `system_menu` VALUES ('dafcece55247401199851b006a8f944d', NULL, NULL, NULL, NULL, '6a188f332a7b4720a5eedce6f850d63e', 'book:edit', 2, 1, '编辑', 1);
INSERT INTO `system_menu` VALUES ('ff1a3b331ed1478d9948aade80749234', NULL, NULL, NULL, NULL, '6a188f332a7b4720a5eedce6f850d63e', 'book:list', 3, 1, '查看', 1);


INSERT INTO `system_role` VALUES ('19c85440232e42e4b28efd6161ca4857', 'Admin', '1', '超级管理员', '2019-4-12', NULL);
INSERT INTO `system_role` VALUES ('766a814d34924cf5b72dbf4b404090ad', '图书馆查看角色', '0', '图书馆查看角色', '2020-7-8', NULL);
INSERT INTO `system_role` VALUES ('831977f6121e47769018ed3ca3029ef3', '图书馆编辑角色', '0', '图书馆编辑角色', '2020-7-8', NULL);

INSERT INTO `system_role_menu` VALUES ('739e8b884155432eb467dd4081be73c9', 'a0260e7c303043ed9478f9120e856752', '766a814d34924cf5b72dbf4b404090ad');
INSERT INTO `system_role_menu` VALUES ('61c6937280244ca0998c03b2eabc8cd1', '6a188f332a7b4720a5eedce6f850d63e', '766a814d34924cf5b72dbf4b404090ad');
INSERT INTO `system_role_menu` VALUES ('231e50a7868a4a798c9c13ca33ef9d2a', 'ff1a3b331ed1478d9948aade80749234', '766a814d34924cf5b72dbf4b404090ad');
INSERT INTO `system_role_menu` VALUES ('d52da561bade42fcac71c8ccac6ce334', 'a0260e7c303043ed9478f9120e856752', '831977f6121e47769018ed3ca3029ef3');
INSERT INTO `system_role_menu` VALUES ('88de54ba4aae47209fecf2e9f4791b80', '6a188f332a7b4720a5eedce6f850d63e', '831977f6121e47769018ed3ca3029ef3');
INSERT INTO `system_role_menu` VALUES ('f612291a65d84b49ac1bd8dce4ee21dc', 'cd7aaa0875694f11858c7aecc8e005c8', '831977f6121e47769018ed3ca3029ef3');
INSERT INTO `system_role_menu` VALUES ('040ed84720b84bc5b3846c1bd46a99f1', 'dafcece55247401199851b006a8f944d', '831977f6121e47769018ed3ca3029ef3');
INSERT INTO `system_role_menu` VALUES ('d23b49d0ccdd4c5982c9141dd0e1c223', 'ff1a3b331ed1478d9948aade80749234', '831977f6121e47769018ed3ca3029ef3');


INSERT INTO `tb_books` VALUES ('1', '高考模拟一1', '测试高考模拟习题', '2001', '无名1211', '图书馆主楼三层A区12', '#1-A3-0001', '\\idcard\\C84D90E115864FF086BD38F34A1F98C7.jpg', '0', '2020-7-7 23:30:13', '2020-7-8 21:32:43', '系统管理员', NULL);
INSERT INTO `tb_books` VALUES ('1d5f5239ea7c47a6b90fa0b06048ecdb', '测试历史', '2的大师傅打发打发', '20011', 'TS', 'DAZ', '#1-A33-93231', '\\idcard\\72439CFAA7E04971B350718E40A5A9E8.jpg', '0', NULL, '2020-7-8 09:28:56', '系统管理员', NULL);
INSERT INTO `tb_books` VALUES ('74e5be395bb946fa8fb6dfb3f841bec3', '营销中的厚黑学', '的事发生发射点', '1001', '营销', '第二大楼', '#01-B001-3232', '\\book\\FF7120AA0DA84AB887649B6B20E46A9D.jpg', '0', '2020-7-8 21:18:58', '2020-7-8 21:19:10', '图书馆管理员', NULL);

INSERT INTO `tb_category_code` VALUES (1, '1001', '考试');
INSERT INTO `tb_category_code` VALUES (2, '2001', '历史');
INSERT INTO `tb_category_code` VALUES (3, '20011', '近代史');
