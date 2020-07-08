create table mytest(id int primary key auto_increment, name varchar(20) not null);
CREATE TABLE `system_menu` (
  `id` varchar(96) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `C_DESCRIPTION` longtext,
  `c_icon` varchar(150) DEFAULT NULL,
  `c_index` varchar(150) DEFAULT NULL,
  `c_parent` varchar(96) DEFAULT NULL,
  `c_permission` longtext,
  `c_seq` bigint(20) DEFAULT NULL,
  `c_state` bigint(20) DEFAULT NULL,
  `c_title` longtext,
  `c_type` bigint(20) DEFAULT NULL
) ;


CREATE TABLE `system_role` (
  `ID` varchar(120) DEFAULT NULL,
  `NAME` longtext,
  `IS_SYS` varchar(6) DEFAULT '0',
  `REMARKS` longtext,
  `CREATE_DATE` date DEFAULT NULL,
  `UPDATE_DATE` date DEFAULT NULL
) ;



CREATE TABLE `system_role_menu` (
  `ID` varchar(120) DEFAULT NULL,
  `MENU_ID` varchar(120) DEFAULT NULL,
  `ROLE_ID` varchar(120) DEFAULT NULL
);



CREATE TABLE `system_user` (
  `ID` varchar(120) DEFAULT NULL,
  `PHONE` varchar(60) DEFAULT NULL,
  `USER_NAME` varchar(150) DEFAULT NULL,
  `PASSWORD` varchar(150) DEFAULT NULL,
  `SALT` varchar(30) DEFAULT NULL,
  `EMAIL` varchar(150) DEFAULT NULL,
  `STATUS` varchar(12) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `IS_SYS` varchar(12) DEFAULT NULL,
  `UPDATE_DATE` date DEFAULT NULL
) ;


CREATE TABLE `system_user_role` (
  `ID` varchar(120) DEFAULT NULL,
  `USER_ID` varchar(120) DEFAULT NULL,
  `ROLE_ID` varchar(120) DEFAULT NULL
) ;

CREATE TABLE `tb_category_code` (
  `id` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '编码',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `tb_books` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '书名',
  `introduction` varchar(255) DEFAULT NULL COMMENT '摘要',
  `category` varchar(255) DEFAULT NULL COMMENT '类别',
  `author` varchar(255) NOT NULL COMMENT '作者',
  `location` varchar(255) DEFAULT NULL COMMENT '存放位置',
  `index_code` varchar(255) DEFAULT NULL COMMENT '图书索引编码',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `del_flag` varchar(255) DEFAULT '0' COMMENT '逻辑删除 0-未删除 ；1-已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
