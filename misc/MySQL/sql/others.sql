DROP TABLE IF EXISTS t_class_name;
CREATE TABLE IF NOT EXISTS `t_class_name`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',

  `class_name` VARCHAR(64) UNIQUE NOT NULL COMMENT '班级名称'
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS t_scholarship;
CREATE TABLE IF NOT EXISTS `t_scholarship`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',

  `scholarship_level` INT UNIQUE NOT NULL COMMENT '奖学金等级',
  `money` DECIMAL(10,2) NOT NULL COMMENT '奖学金数目'
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='奖学金' AUTO_INCREMENT=1;