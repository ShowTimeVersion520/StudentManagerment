/*
Navicat MYSQL Data Transfer

Source Server         : 120.24.241.243_3306
Source Server Version : 90405
Source Host           : 120.24.241.243:3306
Source Database       : torinosrc
Source Schema         : public

Target Server Type    : MYSQL
Target Server Version : 90405
File Encoding         : 65001

Date: 2016-10-31 20:07:05
*/

DROP TABLE IF EXISTS t_sys_user_role;
DROP TABLE IF EXISTS t_sys_role_authority;
DROP TABLE IF EXISTS t_sys_user;
DROP TABLE IF EXISTS t_sys_role;
DROP TABLE IF EXISTS t_sys_authority;

CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `username` VARCHAR(64) DEFAULT "" COMMENT '用户名',
  `password` VARCHAR(512) DEFAULT "" COMMENT '密码',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  INDEX (`username`),
  UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_user` (`id`, `username`, `password`, `create_time`, `update_time`, `enabled`) VALUES
  (1, 'lvxin', 'b8053fb0d3f859d7d36b7034ad332c1335a9fae1e4750937488ef3f8d64306c22640849647e542ba', 1495016519592, 1495016519592, 1),
  (2, 'admin', 'a66298cf223f037bfa18c982b1162aa84781c3f05f839fa927bbd5e813aafd43fabc68ed1c4622a8', 1495202295539, 1495202295539, 1),
  (3, 'guest', '3d01267fab76c23ecb04d1cd646633aa43019b461e23dcfe6eb4e2c3e7e7be6163ac3d3c39a5205b', 1495202367492, 1495202367492, 1);

CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `english_name` VARCHAR(64) DEFAULT NULL COMMENT '角色英文名称',
  `chinese_name` VARCHAR(128) DEFAULT NULL COMMENT '角色中文名称',
  `description` VARCHAR(1024) DEFAULT NULL COMMENT '角色描述',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  UNIQUE(`chinese_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_role` (`id`, `english_name`, `chinese_name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (1, 'ADMIN', '管理员', '管理员', 1495016519592, 1495016519592, 1),
  (2, 'ADMIN', '运维人员', '运维人员', 1495016519592, 1495016519592, 1),
  (3, 'USER', '用户', '用户', 1495016519592, 1495016519592, 1);

CREATE TABLE IF NOT EXISTS `t_sys_authority` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `name` VARCHAR(64) DEFAULT NULL COMMENT '权限名称',
  `description` VARCHAR(1024) DEFAULT NULL COMMENT '权限描述',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  INDEX (`name`),
  UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_authority` (`id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (1, '后台账号管理', '后台账号管理', 1495016519592, 1495016519592, 1),
  (2, '角色/权限管理', '角色/权限管理', 1495016519592, 1495016519592, 1);

CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `sys_user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户Id',
  `sys_role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色Id',
#   PRIMARY KEY (`sys_user_id`, `sys_role_id`),
  INDEX (sys_user_id,sys_role_id),
  FOREIGN KEY (sys_user_id)
    REFERENCES t_sys_user(id),
  FOREIGN KEY (sys_role_id)
    REFERENCES t_sys_role(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

INSERT INTO `t_sys_user_role` (`sys_user_id`, `sys_role_id`) VALUES
  (1, 1),
  (2, 2),
  (3, 3);


CREATE TABLE IF NOT EXISTS `t_sys_role_authority` (
  `sys_role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色Id',
  `sys_authority_id` BIGINT UNSIGNED NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`sys_role_id`,`sys_authority_id`),
  FOREIGN KEY (sys_authority_id)
  REFERENCES t_sys_authority(id),
  FOREIGN KEY (sys_role_id)
  REFERENCES t_sys_role(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

INSERT INTO `t_sys_role_authority` (`sys_role_id`, `sys_authority_id`) VALUES
  (1, 1),
  (1, 2);
