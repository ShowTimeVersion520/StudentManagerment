DROP TABLE IF EXISTS t_change;
CREATE TABLE IF NOT EXISTS `t_change`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',

  `sc_change` CHAR(1) COMMENT '成绩变动 0-未变动 1-已变动',
  `student_change` CHAR(1) COMMENT '学生信息变动 0-未变动 1-已变动',
  `course_change` CHAR(1) COMMENT '课程信息变动 0-未变动 1-已变动'
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='变动表' AUTO_INCREMENT=1;