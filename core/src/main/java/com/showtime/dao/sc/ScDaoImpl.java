package com.showtime.dao.sc;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;

import com.showtime.dao.base.BaseDao;
import com.showtime.dao.base.BatchDao;
import com.showtime.model.entity.sc.Sc;
import com.showtime.model.entity.student.Student;
import com.showtime.model.entity.course.Course;
import com.showtime.model.view.sc.ScView;
import com.showtime.service.commons.constants.FractionConstant;
import org.springframework.data.domain.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public class ScDaoImpl implements BatchDao, BaseDao<ScView> {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public void batchInsert(List list) {
        for(int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
            if(i % 30== 0) {
                em.flush();
                em.clear();
            }
        }
    }

    @Transactional
    public void batchUpdate(List list) {
        for(int i = 0; i < list.size(); i++) {
            em.merge(list.get(i));
            if(i % 30== 0) {
                em.flush();
                em.clear();
            }
        }
    }

    @Override
    public Page<ScView> getByMultiCondition(String sortType, String sortDirection,Pageable pageable, ScView entity, Object condition) {
        Integer fraction = (Integer)condition;
        String sql = "select sc.*, s.name as student_name, c.name as course_name" +
                " from t_sc sc " +
                "inner join t_student s on sc.student_number = s.student_number " +
                "inner join t_course c on sc.course_number = c.course_number ";
        boolean falg = false;

//        // 添加时间
//        if(entity.getCreateTime() != Integer.MIN_VALUE){
//            if(!falg){
//                sql += "where sc.create_time = " + entity.getCreateTime() + " ";
//                falg = true;
//            }
//            else
//                sql += "and sc.create_time = " + entity.getCreateTime() + " ";
//
//        }
//        // 更新时间
//        if(entity.getUpdateTime() != Integer.MIN_VALUE){
//            if(!falg){
//                sql += "where sc.update_time = " + entity.getUpdateTime() + " ";
//                falg = true;
//            }
//            else
//                sql += "and sc.update_time = " + entity.getUpdateTime() + " ";
//        }
        // 学号
        if(entity.getStudentNumber() != Integer.MIN_VALUE){
            if(!falg){
                sql += "where sc.student_number = " + entity.getStudentNumber() + " ";
                falg = true;
            }
            else
                sql += "and sc.student_number = " + entity.getStudentNumber() + " ";

        }
        // 学生姓名
        if(!"".equals(entity.getStudentName())){
            if(!falg){
                sql += "where s.name = '" + entity.getStudentName() + "' ";
                falg = true;
            }
            else
                sql += "and s.name = '" + entity.getStudentName() + "' ";

        }
        // 学生班级
        if(!"".equals(entity.getClassName())){
            if(!falg){
                sql += "where s.class_name = '" + entity.getClassName() + "' ";
                falg = true;
            }
            else
                sql += "and s.class_name = '" + entity.getClassName() + "' ";

        }
        // 年级
        if(!"".equals(entity.getGrade())){
            if(!falg){
                sql += "where s.grade = '" + entity.getGrade() + "' ";
                falg = true;
            }
            else
                sql += "and s.grade = '" + entity.getGrade() + "' ";

        }
        // 课程号
        if(!"".equals(entity.getCourseNumber())){
            if(!falg){
                sql += "where sc.course_number = '" + entity.getCourseNumber() + "' ";
                falg = true;
            }
            else
                sql += "and sc.course_number = '" + entity.getCourseNumber() + "' ";

        }
        // 课程名称
        if(!"".equals(entity.getCourseName())){
            if(!falg){
                sql += "where c.name = '" + entity.getClassName() + "' ";
                falg = true;
            }
            else
                sql += "and c.name = '" + entity.getClassName() + "' ";

        }
        // 成绩
        if(fraction != Integer.MIN_VALUE){
            if(fraction == FractionConstant.POOR_FRACTION){
                if(!falg){
                    sql += "where sc.fraction < 6000" + " ";
                    falg = true;
                }
                else
                    sql += "and sc.fraction < 6000" + " ";

            }
        }
        // 全级排名
        if(entity.getGradeRanking() != Integer.MIN_VALUE){
            if(!falg){
                sql += "where sc.grade_ranking = " + entity.getGradeRanking() + " ";
                falg = true;
            }
            else
                sql += "and sc.grade_ranking = " + entity.getGradeRanking() + " ";

        }
        // 班级排名
        if(entity.getClassRanking() != Integer.MIN_VALUE){
            if(!falg){
                sql += "where sc.class_ranking = " + entity.getGradeRanking() + " ";
                falg = true;
            }
            else
                sql += "and sc.class_ranking = " + entity.getGradeRanking() + " ";

        }

        sql += "order by " + sortType + " " + sortDirection + " ";
        //分页
        sql += "limit " + pageable.getPageNumber() + "," + pageable.getPageSize();

        //System.out.println("------------>" + sql);
        Query query = em.createNativeQuery(sql);
        List<Object[]> objects = query.getResultList();
        List<ScView> scViews = new ArrayList<>();
        for(Object[] object:objects){
            ScView scView = new ScView();

            scView.setId(((BigInteger)object[0]).longValue());
            if(object[1] != null)
                scView.setCreateTime(((BigInteger)object[1]).longValue());
            if(object[2] != null)
                scView.setUpdateTime(((BigInteger)object[2]).longValue());
            if(object[3] != null)
                scView.setStudentNumber((Integer)object[3]);
            if(object[4] != null)
                scView.setCourseNumber((String)object[4]);
            if(object[5] != null)
                scView.setFraction((Integer)object[5]);
            if(object[6] != null)
                scView.setGradeRanking((Integer)object[6]);
            if(object[7] != null)
                scView.setGradeRankingPercent((Integer)object[7]);
            if(object[8] != null)
                scView.setClassRanking((Integer)object[8]);
            if(object[9] != null)
                scView.setClassRankingPercent((Integer)object[9]);
            if(object[10] != null)
                scView.setStudentName((String)object[10]);
            if(object[11] != null)
                scView.setCourseName((String)object[11]);

            scViews.add(scView);
        }

        return new PageImpl(scViews, pageable, scViews == null ? 0 : scViews.size());
    }
}