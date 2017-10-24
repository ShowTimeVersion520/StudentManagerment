DROP TABLE IF EXISTS t_course;
CREATE TABLE IF NOT EXISTS `t_course`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',

  `course_number` VARCHAR(64) UNIQUE NOT NULL COMMENT '课程编号',
  `name` VARCHAR(64) COMMENT '课程名称',
  `learn_hours` VARCHAR(10) COMMENT '学时',
  `credit`	VARCHAR(10) COMMENT '学分',
  `avg_fraction` INT COMMENT '平均分'
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='课程' AUTO_INCREMENT=1;


