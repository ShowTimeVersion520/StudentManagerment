DROP TABLE IF EXISTS t_student;
CREATE TABLE IF NOT EXISTS `t_student`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',

  `student_number` INT UNIQUE NOT NULL COMMENT '学号',
  `name` VARCHAR(128) COMMENT '姓名',
  `gender` CHAR(1) COMMENT '性别',
  `native_place` VARCHAR(128) COMMENT '籍贯',
  `grade` VARCHAR(10) COMMENT '年级',
  `class_name` VARCHAR(64) COMMENT '班级名称',
  `scholarship_level` INT COMMENT '奖学金等级',
  `sum_fraction` DECIMAL(10,2) COMMENT '总成绩'
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='学生' AUTO_INCREMENT=1;