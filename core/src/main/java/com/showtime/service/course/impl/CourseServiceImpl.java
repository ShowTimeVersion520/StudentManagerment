package com.showtime.service.course.impl;

import com.showtime.dao.change.ChangeDao;
import com.showtime.dao.course.CourseDao;
import com.showtime.dao.sc.ScDao;
import com.showtime.model.entity.change.Change;
import com.showtime.model.entity.course.Course;
import com.showtime.model.view.course.CourseView;
import com.showtime.service.commons.constants.change.ChangeNameConstant;
import com.showtime.service.commons.constants.change.IsChangeConstant;
import com.showtime.service.commons.utils.CreateNumberUtils;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.course.CourseService;
import com.showtime.service.exception.ServiceException;
import com.showtime.service.generator.GeneratorService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
* <b><code>CourseImpl</code></b>
* <p/>
* Course的具体实现
* <p/>
* <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class CourseServiceImpl implements CourseService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GeneratorService generatorService;
    @Autowired
    private ChangeDao changeDao;
    @Autowired
    private ScDao scDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(CourseView.class, Course.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Course.class, CourseView.class,
            false);

    @Override
    public CourseView getEntity(long id) {
        // 获取Entity
        Course course = courseDao.getOne(id);
        // 复制Dao层属性到view属性
        CourseView courseView = new CourseView();
        daoToViewCopier.copy(course, courseView,null);
        if(course.getAvgFraction() != null)
            courseView.setAvgFraction(new BigDecimal((float)course.getAvgFraction()/100).setScale(2, BigDecimal.ROUND_HALF_UP));
        return courseView;
    }

    @Override
    public Page<CourseView> getEntities(int currentPage, int pageSize) {
        List<Course> courses = courseDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<CourseView> courseViews = new ArrayList<>();
        for (Course course : courses){
            CourseView courseView = new CourseView();
            daoToViewCopier.copy(course, courseView, null);
            courseViews.add(courseView);
        }
        return new PageImpl(courseViews, pageable, courseViews == null ? 0 : courseViews.size());
    }

    @Override
    public Page<CourseView> getEntitiesByParms(CourseView courseView, int currentPage, int pageSize) {
        Specification<Course> courseSpecification = new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                                    // 课程编号
                    if(!"".equals(courseView.getCourseNumber())){
                    predicates.add(criteriaBuilder.equal(root.get("courseNumber").as(String.class), courseView.getCourseNumber()));
                    }
                                                                                    // 课程名称
                    if(!"".equals(courseView.getName())){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), courseView.getName() + "%"));
                    }
                                                                                    // 学时
                    if(!"".equals(courseView.getLearnHours())){
                    predicates.add(criteriaBuilder.equal(root.get("learnHours").as(String.class), courseView.getLearnHours()));
                    }
                                                                                    // 学分
                    if(!"".equals(courseView.getCredit())){
                    predicates.add(criteriaBuilder.equal(root.get("credit").as(String.class), courseView.getCredit()));
                    }
                                                                                                                                                    // 先修课id号
//                    if(courseView.getPreCourse() != Integer.MIN_VALUE){
//                    predicates.add(criteriaBuilder.equal(root.get("preCourse").as(Long.class), courseView.getPreCourse()));
//                    }
                                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Course> courses = courseDao.findAll(courseSpecification, pageable);

        // 转换成View对象
        Converter<Course, CourseView> c = new Converter<Course, CourseView>() {
            @Override
            public CourseView convert(Course course) {
                CourseView courseView = new CourseView();
                daoToViewCopier.copy(course, courseView, null);
                if(course.getAvgFraction() != null)
                    courseView.setAvgFraction(new BigDecimal((float)course.getAvgFraction()/100).setScale(2, BigDecimal.ROUND_HALF_UP));
                //courseView.setAvgFraction(course.getAvgFraction());
//                if(!ObjectUtils.isEmpty(course.getPreCourse())){
//                Course pre = new Course();
//                pre = courseDao.findOne(course.getPreCourse());
//                    courseView.setPreCourseName(pre.getName());
//                }
                return courseView;
            }
        };
        return courses.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return courseDao.count();
    }

    @Override
    public List<CourseView> findAll() {
        List<CourseView> courseViews = new ArrayList<>();
        List<Course> courses = courseDao.findAll();
        for (Course course : courses){
            CourseView courseView = new CourseView();
            daoToViewCopier.copy(course, courseView, null);
            courseViews.add(courseView);
        }
        return courseViews;
    }

    @Override
    public String saveEntity(CourseView courseView) {
        // 保存的业务逻辑
        Course course = new Course();
        viewToDaoCopier.copy(courseView, course, null);
        course.setAvgFraction(courseView.getAvgFraction());
        // user数据库映射传给dao进行存储
        course.setCourseNumber(CreateNumberUtils.createCourseNumber(generatorService.getCourseNumberSuffix()));
        //course.setCreateTime(new Date().getTime());
        //course.setUpdateTime(new Date().getTime());
        courseDao.save(course);
        return String.valueOf(course.getId());
    }

    @Override
    public void deleteEntity(long id) {
        courseDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] tmps= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        ArrayList<Long> entityIds = new ArrayList<>();
        for(int i=0;i<tmps.length; ++i){
            Long entityId = Long.valueOf(tmps[i]);
            entityIds.add(entityId);
        }
        List<Course> courses = courseDao.getByIds(entityIds);
        courseDao.deleteInBatch(courses);
        ArrayList<String> numbers = new ArrayList<>();
        for(Course course:courses){
            numbers.add(course.getCourseNumber());
        }
        scDao.deleteInBatchByCourseNumber(numbers);
    }

    @Override
    public void updateEntity(CourseView courseView) {
        Course course = new Course();
        BeanCopier copier = BeanCopier.create(CourseView.class, Course.class,
                false);
        copier.copy(courseView, course, null);
        course.setAvgFraction(courseView.getAvgFraction());
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Course course1 = courseDao.findOne(course.getId());
        if(ObjectUtils.isEmpty(course1)){
            throw new ApplicationException("id not find in database!");
        }
        //course1.setUserName(course.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!course.getPassword().equals(course1.getPassword())){
        //    course1.setPassword(new StandardPasswordEncoder().encode(course.getPassword()));
        //}
        // course1.setCreateTime(course1.getCreateTime());
        //course.setUpdateTime(new Date().getTime());
        //course.setCreateTime(course1.getCreateTime());
        //course1.setSysRole(course.getSysRole());
        ReflectUtils.flushModel(course,course1);
//        if(Integer.MIN_VALUE == course.getPreCourse()){
//            course.setPreCourse(null);
//        }
        courseDao.save(course);
    }

    @Override
    public Page<CourseView> getCourseByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Course> courseSpecification = new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> predicates1 = new ArrayList<>();

                String[] keywords = keyword.split(" ");

                if (!ObjectUtils.isEmpty(keywords)) {
                    for (String word : keywords) {
                        //predicates1.add(criteriaBuilder.like(root.get("userName").as(String.class), "%" + word + "%"));
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

        Page<Course> courses = courseDao.findAll(courseSpecification, pageable);

        // 转换成View对象
        Converter<Course, CourseView> c = new Converter<Course, CourseView>() {
            @Override
            public CourseView convert(Course course) {
                CourseView courseView = new CourseView();
                daoToViewCopier.copy(course, courseView, null);
                return courseView;
            }
        };
        return courses.map(c);
    }

//    @Override
//    public List<PreCourseView> getAllPreCourses() {
//        List<Course> courses =  courseDao.findAll();
//
//        // 转换成View对象
//        List<PreCourseView> preCourseViews = new ArrayList<>();
//
//        //放入一个空的
//        PreCourseView preCourseView = new PreCourseView();
//        preCourseView.setId(Long.valueOf(Integer.MIN_VALUE));
//        preCourseView.setName("");
//        preCourseViews.add(preCourseView);
//
//        for (Course course : courses){
//            PreCourseView preCourseView2 = new PreCourseView();
//            preCourseView2.setId(course.getId());
//            preCourseView2.setName(course.getName());
//            preCourseViews.add(preCourseView2);
//        }
//        return preCourseViews;
//    }

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void updateCoursesAvgFraction() {
        Change change = changeDao.getByChangeName(ChangeNameConstant.COURSE_CHANGE);
        if(ObjectUtils.isEmpty(change)){
            throw new ServiceException("change is empty");
        }
        if(IsChangeConstant.CHANGE.equals(change.getIsChange())){
        courseDao.updateCoursesAvgFraction();
            change.setIsChange(IsChangeConstant.NOT_CHANGE);
            changeDao.save(change);
        }
    }
}
