DROP TABLE IF EXISTS t_banner;
CREATE TABLE IF NOT EXISTS `t_banner` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `auditors` VARCHAR(255) COMMENT '审核人员',
  `audit_status` INT(1) COMMENT '审核状态 0:待审核 1：审核失败 2：审核成功' ,
  `audit_content` TEXT COMMENT '审核内容',
  `audit_time` BIGINT NOT NULL DEFAULT '0' COMMENT '审批时间',

  `title` VARCHAR(255) COMMENT '广告标题',
  `content` TEXT COMMENT '广告内容',
  `sub_title` VARCHAR(255) COMMENT '广告二级标题',
  `image_url` TEXT COMMENT '广告图片',
  `redirect_url` TEXT COMMENT '跳转链接',
  `sequence` INT(255) DEFAULT '99'COMMENT '排序',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='广告表' AUTO_INCREMENT=1;