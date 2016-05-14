CREATE DATABASE  IF NOT EXISTS `mip`  
USE `mip`;
 
--
-- Table structure for table `app_applications`
--

DROP TABLE IF EXISTS `app_applications`;
 
CREATE TABLE `app_applications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(36) CHARACTER SET latin1 NOT NULL,
  `app_name` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `APP_ID_UNIQUE` (`app_id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 

--
-- Table structure for table `mip_sys_app_detail`
--

DROP TABLE IF EXISTS `mip_sys_app_detail`;
 
CREATE TABLE `mip_sys_app_detail` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `app_icon_url` varchar(128) NOT NULL COMMENT '应用图标下载地址',
  `app_url` varchar(128) NOT NULL COMMENT '应用下载地址',
  `app_score` int(11) DEFAULT NULL COMMENT '应用分数',
  `app_score_count` int(11) NOT NULL DEFAULT '0' COMMENT '应用打分次数',
  `app_download_count` int(11) NOT NULL DEFAULT '0' COMMENT '应用下载次数',
  `app_publisher` varchar(36) DEFAULT NULL COMMENT '应用发布者',
  `app_desc` varchar(128) DEFAULT NULL COMMENT '应用描述',
  `app_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '应用发布时间',
  `app_update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '应用更新时间',
  `app_os_version` varchar(32) DEFAULT NULL COMMENT '应用要求的操作系统最低版本',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用详情表';
 
 

--
-- Table structure for table `mip_sys_application`
--

DROP TABLE IF EXISTS `mip_sys_application`;
 
CREATE TABLE `mip_sys_application` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `app_id` varchar(36) NOT NULL COMMENT '应用标识',
  `app_name` varchar(45) DEFAULT NULL COMMENT '应用名称',
  `app_type` int(11) DEFAULT NULL COMMENT '应用类型',
  `app_version_name` varchar(45) DEFAULT NULL COMMENT '应用版本名称',
  `app_version_code` varchar(45) DEFAULT NULL COMMENT '应用版本号',
  `app_os` int(11) DEFAULT NULL COMMENT '应用操作系统类型',
  `app_pkg_name` varchar(64) DEFAULT NULL COMMENT '应用包名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';
 
 
-- Table structure for table `mip_sys_token`
-- 令牌表
--
DROP TABLE IF EXISTS `mip_sys_token`;
CREATE TABLE `mip_sys_token` (
  `token_id` varchar(36) NOT NULL,
  `create_time` timestamp,
  `update_time` timestamp,
  `user_name` varchar(50) NOT NULL,
  `device_id` varchar(50) NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


---- Table structure for table `mip_sys_authority`
-- 权限表

--

DROP TABLE IF EXISTS `mip_sys_authority`;
 
CREATE TABLE `mip_sys_authority` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `show_index` int(11) DEFAULT NULL COMMENT '排序',
  `loc_index` tinyint(4) DEFAULT NULL COMMENT '占位符索引',
  `descp` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';
 
 

--
-- Table structure for table `mip_sys_device`
--

DROP TABLE IF EXISTS `mip_sys_device`;
  
CREATE TABLE `mip_sys_device` (
  `id` varchar(32) NOT NULL COMMENT '唯一标识',
  `name` varchar(50) NOT NULL COMMENT '设备名称',
  `os` tinyint(4) DEFAULT NULL COMMENT '操作系统类型',
  `type` tinyint(4) DEFAULT NULL COMMENT '设备类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '设备状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';
 

--
-- Table structure for table `mip_sys_device_detail`
--

DROP TABLE IF EXISTS `mip_sys_device_detail`;
 
CREATE TABLE `mip_sys_device_detail` (
  `id` varchar(36) NOT NULL COMMENT '设备标识',
  `device_detail` blob NOT NULL COMMENT '设备详情',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '设备注册时间',
  `destory_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '设备注销时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备详情表';
 
 

--
-- Table structure for table `mip_sys_device_order`
--

DROP TABLE IF EXISTS `mip_sys_device_order`;
 
CREATE TABLE `mip_sys_device_order` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `device_id` varchar(36) NOT NULL COMMENT '设备标识',
  `device_order` varchar(36) NOT NULL COMMENT '设备指令',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '指令创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备指令表';
 

--
-- Table structure for table `mip_sys_group`
--

DROP TABLE IF EXISTS `mip_sys_group`;
 
CREATE TABLE `mip_sys_group` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `group_name` varchar(45) DEFAULT NULL COMMENT '用户组名称',
  `group_desc` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户组描述',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父组标识',
  `status` varchar(45) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `fk_group_self_parent_id_idx` (`parent_id`),
  CONSTRAINT `fk_group_self_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `mip_sys_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户分组表';
 
 
--
-- Table structure for table `mip_sys_resource`
--

DROP TABLE IF EXISTS `mip_sys_resource`;
 
CREATE TABLE `mip_sys_resource` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `name` varchar(50) NOT NULL COMMENT '资源名称',
  `uri` varchar(50) DEFAULT NULL COMMENT '资源定位标识',
  `type` tinyint(4) DEFAULT NULL COMMENT '资源类型：1url 2文件',
  `permission` int(11) DEFAULT NULL COMMENT '资源权限累加和',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';
 



--
-- Table structure for table `mip_sys_resource_authority`
--

DROP TABLE IF EXISTS `mip_sys_resource_authority`;
 
 CREATE TABLE `mip_sys_resource_authority` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源唯一标识',
  `authority_id` varchar(36) DEFAULT NULL COMMENT '权限唯一标识',
  `url` varchar(256) DEFAULT NULL COMMENT '对应的访问路径',
  PRIMARY KEY (`id`),
  KEY `fk_ra_resource_id_idx` (`resource_id`),
  KEY `fk_ra_authority_id_idx` (`authority_id`),
  KEY `uniq_resource_id_authority_id` (`authority_id`,`resource_id`),
  CONSTRAINT `fk_ra_authority_id` FOREIGN KEY (`authority_id`) REFERENCES `mip_sys_authority` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ra_resource_id` FOREIGN KEY (`resource_id`) REFERENCES `mip_sys_resource` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源权限关联表';
 


--
-- Table structure for table `mip_sys_role`
--

DROP TABLE IF EXISTS `mip_sys_role`;
 
CREATE TABLE `mip_sys_role` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
 

--
-- Table structure for table `mip_sys_role_resource`
--

DROP TABLE IF EXISTS `mip_sys_role_resource`;
 
CREATE TABLE `mip_sys_role_resource` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色标识',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源标识',
  `permission` int(11) DEFAULT NULL COMMENT '角色对应的权限累加值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqu_key_roleid_resource_id` (`resource_id`,`role_id`),
  KEY `fk_rs_resource_id_idx` (`resource_id`),
  KEY `fk_rs_role_id_idx` (`role_id`),
  CONSTRAINT `fk_rs_resource_id` FOREIGN KEY (`resource_id`) REFERENCES `mip_sys_resource` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rs_role_id` FOREIGN KEY (`role_id`) REFERENCES `mip_sys_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源管理表';
 

--
-- Table structure for table `mip_sys_user`
--

DROP TABLE IF EXISTS `mip_sys_user`;
 
CREATE TABLE `mip_sys_user` (
  `id` varchar(36) NOT NULL COMMENT '唯一标示',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
 


--
-- Table structure for table `mip_sys_user_device`
--

DROP TABLE IF EXISTS `mip_sys_user_device`;
 
CREATE TABLE `mip_sys_user_device` (
  `id` varchar(32) NOT NULL COMMENT '唯一标识',
  `user_id` varchar(32) NOT NULL COMMENT '用户标识',
  `device_id` varchar(32) NOT NULL COMMENT '设备标识',
  `status` tinyint(4) DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备表';

 

--
-- Table structure for table `mip_sys_user_group`
--

DROP TABLE IF EXISTS `mip_sys_user_group`;
 
CREATE TABLE `mip_sys_user_group` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户标识',
  `group_id` varchar(36) DEFAULT NULL COMMENT '组标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqe_user_id_group_id` (`group_id`,`user_id`),
  KEY `fk_ug_user_id_idx` (`user_id`),
  KEY `fk_ug_group_id_idx` (`group_id`),
  CONSTRAINT `fk_ug_group_id` FOREIGN KEY (`group_id`) REFERENCES `mip_sys_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ug_user_id` FOREIGN KEY (`user_id`) REFERENCES `mip_sys_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组关联表';
 

--
-- Table structure for table `mip_sys_user_role`
--

DROP TABLE IF EXISTS `mip_sys_user_role`;
 
CREATE TABLE `mip_sys_user_role` (
  `id` varchar(36) NOT NULL COMMENT '唯一标识',
  `user_id` varchar(36) NOT NULL COMMENT '用户标识',
  `role_id` varchar(36) NOT NULL COMMENT '角色标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id_role_id` (`role_id`,`user_id`),
  KEY `fk_user_role_user_id_idx` (`user_id`),
  KEY `fk_user_role_role_id_idx` (`role_id`),
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `mip_sys_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `mip_sys_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';
 
-- 设备表
CREATE TABLE `mip_sys_device` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `os` tinyint(4) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
--
-- 设备详情表
--
	
DROP TABLE IF EXISTS `mip_sys_device_detail`;
 
CREATE TABLE `mip_sys_device_detail` (
  `id` varchar(32) NOT NULL,
  `device_id` varchar(32) NOT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `destroy_time` timestamp NOT NULL DEFAULT null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
  
 

-- 应用用户组

DROP TABLE IF EXISTS `mip_sys_app_usergroup`;
create table mip_sys_app_usergroup
(
  id                 VARCHAR(36) primary key not null,
  app_id             VARCHAR(36) not null,
  group_id           VARCHAR(36) not null
);
	
 

 
 
 
