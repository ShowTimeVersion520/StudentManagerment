DROP TABLE IF EXISTS t_generator;
CREATE TABLE IF NOT EXISTS t_generator(
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',

  `name` VARCHAR(64) COMMENT 'XX单号',
  `number_suffix` VARCHAR(64) DEFAULT '000000' COMMENT '单号后缀',
  UNIQUE (`name`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编号后缀';

INSERT t_generator(`name`,`number_suffix`)VALUE('course_number','000000');
