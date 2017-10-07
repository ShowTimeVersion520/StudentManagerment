package com.showtime.service.sc.impl;

import com.showtime.dao.change.ChangeDao;
import com.showtime.dao.course.CourseDao;
import com.showtime.dao.sc.ScDao;
import com.showtime.dao.student.ClassNameDao;
import com.showtime.dao.student.StudentDao;
import com.showtime.model.entity.change.Change;
import com.showtime.model.entity.course.Course;
import com.showtime.model.entity.sc.Sc;
import com.showtime.model.view.sc.ScView;
import com.showtime.service.commons.constants.change.ChangeNameConstant;
import com.showtime.service.commons.constants.FractionConstant;
import com.showtime.service.commons.constants.change.IsChangeConstant;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.exception.ServiceException;
import com.showtime.service.sc.ScService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* <b><code>ScImpl</code></b>
* <p/>
* Sc的具体实现
* <p/>
* <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class ScServiceImpl implements ScService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ScServiceImpl.class);

    @Autowired
    private ScDao scDao;
    @Autowired
    private ChangeDao changeDao;
    @Autowired
    private ClassNameDao classNameDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudentDao studentDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(ScView.class, Sc.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Sc.class, ScView.class,
            false);

    @Override
    public ScView getEntity(long id) {
        // 获取Entity
        Sc sc = scDao.getOne(id);
        // 复制Dao层属性到view属性
        ScView scView = new ScView();
        daoToViewCopier.copy(sc, scView,null);
        return scView;
    }

    @Override
    public Page<ScView> getEntities(int currentPage, int pageSize) {
        List<Sc> scs = scDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<ScView> scViews = new ArrayList<>();
        for (Sc sc : scs){
            ScView scView = new ScView();
            daoToViewCopier.copy(sc, scView, null);
            scViews.add(scView);
        }
        return new PageImpl(scViews, pageable, scViews == null ? 0 : scViews.size());
    }

    @Override
    public Page<ScView> getEntitiesByParms(ScView scView, int currentPage, int pageSize) {
        return null;
    }

    @Override
    public Page<ScView> getEntitiesByParms(String sortType, String sortDirection, Integer fraction,ScView scView, int currentPage, int pageSize) {
        Specification<Sc> scSpecification = new Specification<Sc>() {
            @Override
            public Predicate toPredicate(Root<Sc> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                                                    // 添加时间
                    if(scView.getCreateTime() != Integer.MIN_VALUE){
                    predicates.add(criteriaBuilder.equal(root.get("createTime").as(Long.class), scView.getCreateTime()));
                    }
                                                                                    // 更新时间
                    if(scView.getUpdateTime() != Integer.MIN_VALUE){
                    predicates.add(criteriaBuilder.equal(root.get("updateTime").as(Long.class), scView.getUpdateTime()));
                    }
                                                                    // 学号
                    if(!"".equals(scView.getStudent().getStudentNumber())){
                    predicates.add(criteriaBuilder.equal(root.get("student").get("studentNumber").as(String.class), scView.getStudent().getStudentNumber()));
                    }
                     // 学生姓名
                    if(!"".equals(scView.getStudent().getName())){
                    predicates.add(criteriaBuilder.equal(root.get("student").get("name").as(String.class), scView.getStudent().getName()));
                    }
                    // 学生班级
                    if(!"".equals(scView.getStudent().getClassName())){
                        predicates.add(criteriaBuilder.equal(root.get("student").get("className").as(String.class), scView.getStudent().getClassName()));
                    }
                    // 年级
                    if(!"".equals(scView.getStudent().getGrade())){
                        predicates.add(criteriaBuilder.equal(root.get("student").get("grade").as(String.class), scView.getStudent().getGrade()));
                    }
                    // 课程号
                    if(!"".equals(scView.getCourse().getCourseNumber())){
                    predicates.add(criteriaBuilder.equal(root.get("course").get("courseNumber").as(String.class), scView.getCourse().getCourseNumber()));
                    }
                    // 课程名称
                    if(!"".equals(scView.getCourse().getName())){
                    predicates.add(criteriaBuilder.equal(root.get("course").get("name").as(String.class), scView.getCourse().getName()));
                    }
                       // 成绩
                    if(fraction != Integer.MIN_VALUE){
                        if(fraction == FractionConstant.POOR_FRACTION){
                            predicates.add(criteriaBuilder.lessThan(root.get("fraction").as(BigDecimal.class), new BigDecimal(60)));
                        }
                    }
                                                                                                                                                    // 全级排名
                    if(scView.getGradeRanking() != Integer.MIN_VALUE){
                    predicates.add(criteriaBuilder.equal(root.get("gradeRanking").as(Integer.class), scView.getGradeRanking()));
                    }
                                                                                                                                    // 班级排名
                    if(scView.getClassRanking() != Integer.MIN_VALUE){
                    predicates.add(criteriaBuilder.equal(root.get("classRanking").as(Integer.class), scView.getClassRanking()));
                    }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };
        Sort sort = null;
        // 设置排序
        if("ASC".equals(sortDirection)){
            sort = new Sort(Sort.Direction.ASC, sortType);
        }else if("DESC".equals(sortDirection)){
            sort = new Sort(Sort.Direction.DESC, sortType);
        }

        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Sc> scs = scDao.findAll(scSpecification, pageable);

        // 转换成View对象
        Converter<Sc, ScView> c = new Converter<Sc, ScView>() {
            @Override
            public ScView convert(Sc sc) {
                ScView scView = new ScView();
                daoToViewCopier.copy(sc, scView, null);
                return scView;
            }
        };
        return scs.map(c);

    }
    @Transactional(rollbackOn = { Exception.class })
    public void setRanking() {
        Change change = changeDao.getByChangeName(ChangeNameConstant.SC_CHANGE);
        if(ObjectUtils.isEmpty(change)){
            throw new ServiceException("change is empty");
        }
        //System.out.println("------------>start ranking" + CommonUtils.dateToString(new Date(), CommonUtils.MFSTR));
        if(IsChangeConstant.CHANGE.equals(change.getIsChange())){
            List<String> classNames = classNameDao.getAllClassNames();
            List<String> grades = classNameDao.getAllGrades();
            List<Course> courses = courseDao.findAll();
            Sort sort = new Sort(Sort.Direction.DESC, "fraction");

            if(courses.size() != 0){
                for(Course course:courses) {

                    //更新班级排名
                    if (classNames.size() != 0) {
                        for (String className : classNames) {
                            List<Sc> scs = scDao.getByCourse_CourseNumberAndStudent_ClassName(course.getCourseNumber(), className, sort);
                            //获取上了这堂课的班级人数
                            Integer count = studentDao.getCountByClassName(className);
                            if(scs.size() != 0){
                                for(int i=0; i<scs.size();++i){
                                    Sc sc = scs.get(i);
                                    float rank = i+1;
                                    sc.setClassRanking(i+1);
                                    sc.setClassRankingPercent(new BigDecimal(rank/count));
                                    sc.setUpdateTime(new Date().getTime());
                                    scDao.save(sc);
                                }
                            }
                        }
                    }

                    //更新年级排名
                    if (grades.size() != 0) {
                        for (String grade : grades) {
                            List<Sc> scs = scDao.getByCourse_CourseNumberAndStudent_Grade(course.getCourseNumber(), grade, sort);
                            //获取上了这堂课的年级人数
                            Integer count = studentDao.getCountByGrade(grade);
                            if(scs.size() != 0){
                                for(int i=0; i<scs.size();++i){
                                    Sc sc = scs.get(i);
                                    sc.setGradeRanking(i+1);
                                    float rank = i+1;
                                    sc.setGradeRankingPercent(new BigDecimal(rank/count));
                                    sc.setUpdateTime(new Date().getTime());
                                    scDao.save(sc);
                                }
                            }
                        }
                    }
                }
            }
            //初始化为没变动
            change.setIsChange(IsChangeConstant.NOT_CHANGE);
            changeDao.save(change);
        }
    }

    @Override
    public long getEntitiesCount() {
        return scDao.count();
    }

    @Override
    public List<ScView> findAll() {
        List<ScView> scViews = new ArrayList<>();
        List<Sc> scs = scDao.findAll();
        for (Sc sc : scs){
            ScView scView = new ScView();
            daoToViewCopier.copy(sc, scView, null);
            scViews.add(scView);
        }
        return scViews;
    }

    @Override
    public String saveEntity(ScView scView) {
        // 保存的业务逻辑
        Sc sc = new Sc();
        viewToDaoCopier.copy(scView, sc, null);
        // user数据库映射传给dao进行存储 TODO: 添加Sc相关属性
        //sc.setCreateTime(new Date().getTime());
        //sc.setUpdateTime(new Date().getTime());
        scDao.save(sc);
        return String.valueOf(sc.getId());
    }

    @Override
    public void deleteEntity(long id) {
        scDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Sc> scs = new ArrayList<>();
        for(String entityId : entityIds){
            Sc sc = new Sc();
            sc.setId(Long.valueOf(entityId));
            scs.add(sc);
        }
        scDao.deleteInBatch(scs);
    }

    @Override
    public void updateEntity(ScView scView) {
        Sc sc = new Sc();
        BeanCopier copier = BeanCopier.create(ScView.class, Sc.class,
                false);
        copier.copy(scView, sc, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Sc sc1 = scDao.findOne(sc.getId());
        if(ObjectUtils.isEmpty(sc1)){
            throw new ApplicationException("id not find in database!");
        }
        //sc1.setUserName(sc.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!sc.getPassword().equals(sc1.getPassword())){
        //    sc1.setPassword(new StandardPasswordEncoder().encode(sc.getPassword()));
        //}
        // sc1.setCreateTime(sc1.getCreateTime());
        sc.setUpdateTime(new Date().getTime());
        sc.setCreateTime(sc1.getCreateTime());
        //sc1.setSysRole(sc.getSysRole());
        ReflectUtils.flushModel(sc,sc1);
        scDao.save(sc);

        changeDao.updateChange(IsChangeConstant.CHANGE);
    }

    @Override
    public Page<ScView> getScByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Sc> scSpecification = new Specification<Sc>() {
            @Override
            public Predicate toPredicate(Root<Sc> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<Sc> scs = scDao.findAll(scSpecification, pageable);

        // 转换成View对象
        Converter<Sc, ScView> c = new Converter<Sc, ScView>() {
            @Override
            public ScView convert(Sc sc) {
                ScView scView = new ScView();
                daoToViewCopier.copy(sc, scView, null);
                return scView;
            }
        };
        return scs.map(c);
    }
}
