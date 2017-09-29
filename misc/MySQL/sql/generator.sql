DROP TABLE IF EXISTS t_generator;
CREATE TABLE IF NOT EXISTS t_generator(
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `auditors` VARCHAR(255) COMMENT '审核人员',
  `audit_status` INT(1) COMMENT '审核状态 0:待审核 1：审核失败 2：审核成功' ,
  `audit_content` TEXT COMMENT '审核内容',
  `audit_time` BIGINT NOT NULL DEFAULT '0' COMMENT '审批时间',

  `name` VARCHAR(64) COMMENT 'XX单号',
  `number_suffix` VARCHAR(64) DEFAULT '000000' COMMENT '单号后缀',
  UNIQUE (`name`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编号后缀';

INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'purchase_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'order_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'clearance_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'company_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'product_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'contract_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'logistics_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'invoice_number','000000');
INSERT t_generator(`enabled`,`name`,`number_suffix`)VALUE(1,'logistics_company_number','000000');