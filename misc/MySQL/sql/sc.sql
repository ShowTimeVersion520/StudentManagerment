DROP TABLE IF EXISTS t_sc;
CREATE TABLE IF NOT EXISTS `t_sc`(
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',

  `student_number` INT COMMENT '学号',
  `course_number` VARCHAR(64) COMMENT '课程号',
  `fraction` INT COMMENT '成绩',
  `grade_ranking` INT COMMENT '全级排名',
  `grade_ranking_percent` INT COMMENT '全级排名百分比',
  `class_ranking` INT COMMENT '班级排名',
  `class_ranking_percent` INT COMMENT '班级排名百分比',

  UNIQUE(`student_number`,`course_number`)
)ENGINE INNODB DEFAULT CHARSET=utf8 COMMENT='成绩' AUTO_INCREMENT=1;

DROP VIEW IF EXISTS v_course_avg_fraction;
CREATE VIEW v_course_avg_fraction
  AS
    SELECT
      course_number,
      avg(fraction) AS avg_fraction
    FROM
      t_sc AS sc
    GROUP BY course_number;

DROP VIEW IF EXISTS v_student_sum_fraction;
CREATE VIEW v_student_sum_fraction
  AS
    SELECT
      student_number,
      sum(fraction) AS sum_fraction
    FROM
      t_sc AS sc
    GROUP BY student_number;