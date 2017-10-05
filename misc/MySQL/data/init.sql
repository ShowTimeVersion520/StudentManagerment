-- 添加性别
INSERT INTO `t_gender`(`gender`) VALUE('男');
INSERT INTO `t_gender`(`gender`) VALUE('女');

-- 添加年级
INSERT INTO `t_grade`(`grade`) VALUE('14级');
INSERT INTO `t_grade`(`grade`) VALUE('15级');
INSERT INTO `t_grade`(`grade`) VALUE('16级');

-- 添加班级
INSERT INTO `t_class_name`(`class_name`) VALUE('1班');
INSERT INTO `t_class_name`(`class_name`) VALUE('2班');
INSERT INTO `t_class_name`(`class_name`) VALUE('3班');
INSERT INTO `t_class_name`(`class_name`) VALUE('4班');

-- 添加奖学金
INSERT INTO `t_scholarship`(`scholarship_level`, `money`) VALUE(1,1000);
INSERT INTO `t_scholarship`(`scholarship_level`, `money`) VALUE(2,500);
INSERT INTO `t_scholarship`(`scholarship_level`, `money`) VALUE(3,200);

-- 添加课程
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(1,'英语',80,4,NULL);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(5,'语文',78,3,NULL);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(2,'数据结构',64,2,5);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(3,'数据库',40,2,2);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(4,'DB_设计',46,3,3);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(7,'操作系统',56,3,5);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE(6,'计算机网络',64,3,7);

-- 添加学生
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(1,'张三','男','北京市', '14级','1班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(2,'李四','女','天津市', '14级','1班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(3,'王五','女','北京市', '14级','2班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(4,'刘一','男','天津市', '14级','2班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(5,'陈二','男','黑龙江省','14级','3班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(6,'赵六','男','黑龙江省','14级','3班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(7,'孙七','男','黑龙江省','14级','3班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(8,'周八','男','台湾省', '14级','4班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(10,'郑十','男','台湾省','14级','4班',NULL);

-- 添加成绩
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,1,91);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,2,60);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,3,55);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,5,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,6,59.5);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,1,50);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,3,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,5,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,6,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,1,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,3,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,5,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,6,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,1,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,3,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,5,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,6,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,1,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,3,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,4,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,5,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,6,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,1,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,3,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,5,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,6,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,1,50);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,3,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,5,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,6,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,1,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,3,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,5,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,6,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,1,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,3,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,4,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,5,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,6,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,1,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,2,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,3,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,4,70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,5,90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,6,90);