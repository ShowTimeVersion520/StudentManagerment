DROP TABLE IF EXISTS t_sc;
CREATE TABLE IF NOT EXISTS `t_sc`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',

  `student_number` VARCHAR(64) COMMENT '学号',
  `course_number` VARCHAR(64) COMMENT '课程号',
  `fraction` DECIMAL(10,2) COMMENT '成绩',
  `grade_ranking` INT COMMENT '全级排名',
  `grade_ranking_percent` DECIMAL(3,1) COMMENT '全级排名百分比',
  `class_ranking` INT COMMENT '班级排名',
  `class_ranking_percent` DECIMAL(3,1) COMMENT '班级排名百分比',

  UNIQUE(`student_number`,`course_number`)
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='成绩' AUTO_INCREMENT=1;