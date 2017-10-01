package com.showtime.service.student.impl;

import com.showtime.dao.student.StudentDao;
import com.showtime.model.entity.student.Student;
import com.showtime.model.view.student.StudentView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.student.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
* <b><code>StudentImpl</code></b>
* <p/>
* Student的具体实现
* <p/>
* <b>Creation Time:</b> Sun Oct 01 17:12:45 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class StudentServiceImpl implements StudentService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentDao studentDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(StudentView.class, Student.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Student.class, StudentView.class,
            false);

    @Override
    public StudentView getEntity(long id) {
        // 获取Entity
        Student student = studentDao.getOne(id);
        // 复制Dao层属性到view属性
        StudentView studentView = new StudentView();
        daoToViewCopier.copy(student, studentView,null);
        return studentView;
    }

    @Override
    public Page<StudentView> getEntities(int currentPage, int pageSize) {
        List<Student> students = studentDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<StudentView> studentViews = new ArrayList<>();
        for (Student student : students){
            StudentView studentView = new StudentView();
            daoToViewCopier.copy(student, studentView, null);
            studentViews.add(studentView);
        }
        return new PageImpl(studentViews, pageable, studentViews == null ? 0 : studentViews.size());
    }

    @Override
    public Page<StudentView> getEntitiesByParms(StudentView studentView, int currentPage, int pageSize) {
        Specification<Student> studentSpecification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                // 姓名
                if(!"".equals(studentView.getName())){
                    predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), studentView.getName()));
                }
                                // 性别 1-男 0-女
                if(!"".equals(studentView.getGender())){
                    predicates.add(criteriaBuilder.equal(root.get("gender").as(String.class), studentView.getGender()));
                }
                                // 籍贯
                if(!"".equals(studentView.getNativePlace())){
                    predicates.add(criteriaBuilder.equal(root.get("nativePlace").as(String.class), studentView.getNativePlace()));
                }
                                // 班级名称
                if(!"".equals(studentView.getClassName())){
                    predicates.add(criteriaBuilder.equal(root.get("className").as(String.class), studentView.getClassName()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Student> students = studentDao.findAll(studentSpecification, pageable);

        // 转换成View对象
        Converter<Student, StudentView> c = new Converter<Student, StudentView>() {
            @Override
            public StudentView convert(Student student) {
                StudentView studentView = new StudentView();
                daoToViewCopier.copy(student, studentView, null);
                return studentView;
            }
        };
        return students.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return studentDao.count();
    }

    @Override
    public List<StudentView> findAll() {
        List<StudentView> studentViews = new ArrayList<>();
        List<Student> students = studentDao.findAll();
        for (Student student : students){
            StudentView studentView = new StudentView();
            daoToViewCopier.copy(student, studentView, null);
            studentViews.add(studentView);
        }
        return studentViews;
    }

    @Override
    public String saveEntity(StudentView studentView) {
        // 保存的业务逻辑
        Student student = new Student();
        viewToDaoCopier.copy(studentView, student, null);
        // user数据库映射传给dao进行存储
        studentDao.save(student);
        return String.valueOf(student.getId());
    }

    @Override
    public void deleteEntity(long id) {
        studentDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Student> students = new ArrayList<>();
        for(String entityId : entityIds){
            Student student = new Student();
            student.setId(Long.valueOf(entityId));
            students.add(student);
        }
        studentDao.deleteInBatch(students);
    }

    @Override
    public void updateEntity(StudentView studentView) {
        Student student = new Student();
        BeanCopier copier = BeanCopier.create(StudentView.class, Student.class,
                false);
        copier.copy(studentView, student, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Student student1 = studentDao.findOne(student.getId());
        if(ObjectUtils.isEmpty(student1)){
            throw new ApplicationException("id not find in database!");
        }
        //student1.setUserName(student.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!student.getPassword().equals(student1.getPassword())){
        //    student1.setPassword(new StandardPasswordEncoder().encode(student.getPassword()));
        //}
        // student1.setCreateTime(student1.getCreateTime());
        //student.setUpdateTime(new Date().getTime());
        //student.setCreateTime(student1.getCreateTime());
        //student1.setSysRole(student.getSysRole());
        ReflectUtils.flushModel(student,student1);
        studentDao.save(student);
    }

    @Override
    public Page<StudentView> getStudentByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Student> studentSpecification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> predicates1 = new ArrayList<>();

                String[] keywords = keyword.split(" ");

                if (!ObjectUtils.isEmpty(keywords)) {
                    for (String word : keywords) {
                        predicates1.add(criteriaBuilder.like(root.get("").as(String.class), "%" + word + "%"));
                    }
                    predicates.add(criteriaBuilder.or(predicates1.toArray(new Predicate[predicates1.size()])));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "");

        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Student> students = studentDao.findAll(studentSpecification, pageable);

        // 转换成View对象
        Converter<Student, StudentView> c = new Converter<Student, StudentView>() {
            @Override
            public StudentView convert(Student student) {
                StudentView studentView = new StudentView();
                daoToViewCopier.copy(student, studentView, null);
                return studentView;
            }
        };
        return students.map(c);
    }
}
