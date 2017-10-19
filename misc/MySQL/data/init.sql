-- 添加性别
INSERT INTO `t_gender`(`gender`) VALUE('男');
INSERT INTO `t_gender`(`gender`) VALUE('女');

-- 添加班级
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('14级','1班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('14级','2班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('14级','3班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('14级','4班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('15级','1班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('15级','2班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('15级','3班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('15级','4班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('16级','1班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('16级','2班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('16级','3班');
INSERT INTO `t_class_name`(`grade`,`class_name`) VALUE('16级','4班');

-- 添加奖学金
INSERT INTO `t_scholarship`(`scholarship_level`, `money`,`number`) VALUE(1,1000,1);
INSERT INTO `t_scholarship`(`scholarship_level`, `money`,`number`) VALUE(2,500, 3);
INSERT INTO `t_scholarship`(`scholarship_level`, `money`,`number`) VALUE(3,200, 10);

-- 添加课程
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000002','英语',80,4,NULL);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000003','语文',78,3,NULL);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000004','数据结构',64,3,2);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000005','数据库',40,2,3);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000006','DB_设计',46,3,4);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000007','操作系统',56,3,2);
INSERT INTO `t_course`(`course_number`, `name`, `learn_hours`, `credit`, `pre_course`) VALUE('C20161017223640000008','计算机网络',64,3,6);

-- 添加学生
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(1,'张三','男','北京市', '14级','1班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(2,'李四','女','天津市', '14级','1班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(3,'王五','女','北京市', '14级','2班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(4,'刘一','男','天津市', '14级','2班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(5,'陈二','男','黑龙江省','14级','3班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(6,'赵六','男','黑龙江省','14级','3班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(7,'孙七','男','黑龙江省','14级','3班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(8,'周八','男','台湾省', '14级','4班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(9,'吴九','女','广东省', '14级','4班',NULL);
INSERT INTO `t_student`(`student_number`, `name`, `gender`, `native_place`,`grade`, `class_name`, `scholarship_level`) VALUE(10,'郑十','男','台湾省','14级','4班',NULL);

-- 添加成绩
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,'C20161017223640000002',91);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,'C20161017223640000004',60);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,'C20161017223640000005',55);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,'C20161017223640000003',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(1,'C20161017223640000008',59.5);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,'C20161017223640000002',50);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,'C20161017223640000005',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,'C20161017223640000003',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(2,'C20161017223640000008',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,'C20161017223640000002',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,'C20161017223640000005',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,'C20161017223640000003',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(3,'C20161017223640000008',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,'C20161017223640000002',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,'C20161017223640000005',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,'C20161017223640000003',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(4,'C20161017223640000008',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,'C20161017223640000002',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,'C20161017223640000005',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,'C20161017223640000006',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,'C20161017223640000003',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(5,'C20161017223640000008',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,'C20161017223640000002',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,'C20161017223640000005',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,'C20161017223640000003',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(6,'C20161017223640000008',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,'C20161017223640000002',50);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,'C20161017223640000005',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,'C20161017223640000003',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(7,'C20161017223640000008',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,'C20161017223640000002',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,'C20161017223640000005',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,'C20161017223640000003',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(8,'C20161017223640000008',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,'C20161017223640000002',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,'C20161017223640000005',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,'C20161017223640000006',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,'C20161017223640000003',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(9,'C20161017223640000008',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,'C20161017223640000002',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,'C20161017223640000004',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,'C20161017223640000005',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,'C20161017223640000006',70);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,'C20161017223640000003',90);
INSERT INTO `t_sc`(student_number,course_number,fraction) VALUE(10,'C20161017223640000008',90);

-- 添加变动
INSERT INTO `t_change`(change_name,is_change) VALUE('sc_change', 1);
INSERT INTO `t_change`(change_name,is_change) VALUE('student_change', 1);
INSERT INTO `t_change`(change_name,is_change) VALUE('course_change', 1);