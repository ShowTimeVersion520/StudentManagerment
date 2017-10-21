package com.showtime.service.sc.impl;

import com.showtime.dao.change.ChangeDao;
import com.showtime.dao.course.CourseDao;
import com.showtime.dao.sc.ScDao;
import com.showtime.dao.student.ClassNameDao;
import com.showtime.dao.student.StudentDao;
import com.showtime.model.entity.change.Change;
import com.showtime.model.entity.course.Course;
import com.showtime.model.entity.student.ClassName;
import com.showtime.model.entity.sc.Sc;
import com.showtime.model.entity.student.Student;
import com.showtime.model.view.sc.ScResultView;
import com.showtime.model.view.sc.ScView;
import com.showtime.model.view.student.CountStudentView;
import com.showtime.service.commons.constants.change.ChangeNameConstant;
import com.showtime.service.commons.constants.FractionConstant;
import com.showtime.service.commons.constants.change.IsChangeConstant;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.exception.ServiceException;
import com.showtime.service.sc.ScService;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.EntityManagerImpl;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public Page<ScResultView> getEntitiesByParms(String sortType, String sortDirection, Integer fraction, ScResultView scResultView, int currentPage, int pageSize) {
        Pageable pageable = new PageRequest(currentPage,pageSize);
        ScView scView = this.scResultViewToScView(scResultView);
        Page<ScView> scViews = scDao.getByMultiCondition(sortType, sortDirection, pageable,scView, fraction);

//        转换成View对象
        Converter<ScView, ScResultView> c = new Converter<ScView, ScResultView>() {
            @Override
            public ScResultView convert(ScView scView) {
                return scViewToScResultView(scView);
            }
        };
        return scViews.map(c);
//        Specification<Sc> scSpecification = new Specification<Sc>() {
//            @Override
//            public Predicate toPredicate(Root<Sc> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                                                                    // 添加时间
//                    if(scView.getCreateTime() != Integer.MIN_VALUE){
//                    predicates.add(criteriaBuilder.equal(root.get("createTime").as(Long.class), scView.getCreateTime()));
//                    }
//                                                                                    // 更新时间
//                    if(scView.getUpdateTime() != Integer.MIN_VALUE){
//                    predicates.add(criteriaBuilder.equal(root.get("updateTime").as(Long.class), scView.getUpdateTime()));
//                    }
//                                                                    // 学号
//                    if(!"".equals(scView.getStudentNumber())){
//                    predicates.add(criteriaBuilder.equal(root.get("studentNumber").as(String.class), scView.getStudentNumber()));
//                    }
//                     // 学生姓名
//                    if(!"".equals(scView.getStudentName())){
//                        List<String> studentNumbers = studentDao.getStudentNumberByName(scView.getClassName());
//                        Predicate predicate = null;
//                        for(String studentNumber:studentNumbers){
//                            if(predicate == null){
//                                predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentNumber));
//                            }
//                            predicate = criteriaBuilder.or(predicate,criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentNumber));
//                        }
//                        predicates.add(predicate);
//                    }
//                    // 学生班级
//                    if(!"".equals(scView.getClassName())){
//                        List<String> studentNumbers = studentDao.getStudentNumberByClassName(scView.getClassName());
//                        Predicate predicate = null;
//                        for(String studentNumber:studentNumbers){
//                            if(predicate == null){
//                                predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentNumber));
//                            }
//                            predicate = criteriaBuilder.or(predicate,criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentNumber));
//                        }
//                        predicates.add(predicate);                    }
//                    // 年级
//                    if(!"".equals(scView.getGrade())){
//                        List<String> studentNumbers = studentDao.getStudentNumberByGrade(scView.getGrade());
//                        Predicate predicate = null;
//                        for(String studentNumber:studentNumbers){
//                            if(predicate == null){
//                                predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentNumber));
//                            }
//                            predicate = criteriaBuilder.or(predicate,criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentNumber));
//                        }
//                        predicates.add(predicate);
//                    }
//                    // 课程号
//                    if(!"".equals(scView.getCourseNumber())){
//                    predicates.add(criteriaBuilder.equal(root.get("courseNumber").as(String.class), scView.getCourseNumber()));
//                    }
//                    // 课程名称
//                    if(!"".equals(scView.getCourseName())){
//                        List<String> courseNumbers = courseDao.getCourseNumberByName(scView.getCourseName());
//                        Predicate predicate = null;
//                        for(String courseNumber:courseNumbers){
//                            if(predicate == null){
//                                predicate = criteriaBuilder.or(criteriaBuilder.equal(root.get("courseNumber").as(String.class), courseNumber));
//                            }
//                            predicate = criteriaBuilder.or(predicate,criteriaBuilder.equal(root.get("courseNumber").as(String.class), courseNumber));
//                        }
//                        predicates.add(predicate);
//                    }
//                       // 成绩
//                    if(fraction != Integer.MIN_VALUE){
//                        if(fraction == FractionConstant.POOR_FRACTION){
//                            predicates.add(criteriaBuilder.lessThan(root.get("fraction").as(Integer.class), 60));
//                        }
//                    }
//                                                                                                                                                    // 全级排名
//                    if(scView.getGradeRanking() != Integer.MIN_VALUE){
//                    predicates.add(criteriaBuilder.equal(root.get("gradeRanking").as(Integer.class), scView.getGradeRanking()));
//                    }
//                                                                                                                                    // 班级排名
//                    if(scView.getClassRanking() != Integer.MIN_VALUE){
//                    predicates.add(criteriaBuilder.equal(root.get("classRanking").as(Integer.class), scView.getClassRanking()));
//                    }
//                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
//                return criteriaQuery.getRestriction();
//            }
//        };
//        Sort sort = null;
//
//        // 设置排序
//        if("ASC".equals(sortDirection)){
//            sort = new Sort(Sort.Direction.ASC, sortType);
//        }else if("DESC".equals(sortDirection)){
//            sort = new Sort(Sort.Direction.DESC, sortType);
//        }
//
//        // 设置分页
//        Pageable pageable = new PageRequest(currentPage, pageSize, sort);
//
//        Page<Sc> scs = scDao.findAll(scSpecification, pageable);
//
//        // 转换成View对象
//        Converter<Sc, ScView> c = new Converter<Sc, ScView>() {
//            @Override
//            public ScView convert(Sc sc) {
//                ScView scView = new ScView();
//                daoToViewCopier.copy(sc, scView, null);
//                Student student = studentDao.getByStudentNumber(sc.getStudentNumber());
//                Course course = courseDao.getByCourseNumber(sc.getCourseNumber());
//                scView.setClassName(student.getClassName());
//                scView.setGrade(student.getGrade());
//                scView.setStudentName(student.getName());
//                scView.setCourseName(course.getName());
//                return scView;
//            }
//        };
//        return scs.map(c);

    }
    private ScResultView scViewToScResultView(ScView scView) {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer fraction = scView.getFraction();
        Long createTime = scView.getCreateTime();
        Long updateTime = scView.getUpdateTime();
        Integer gradeRankingPercent = scView.getGradeRankingPercent();
        Integer classRankingRercent = scView.getClassRankingPercent();

        ScResultView scResultView = new ScResultView();

        scResultView.setId(scView.getId());
        if(createTime != null)
            scResultView.setCreateTime(new Date(createTime*1000));
        if(updateTime != null)
            scResultView.setUpdateTime(new Date(updateTime*1000));
        scResultView.setStudentNumber(scView.getStudentNumber());
        scResultView.setCourseNumber(scView.getCourseNumber());
        if(fraction != null)
            scResultView.setFraction(new BigDecimal((double)fraction/100).setScale(2));
        scResultView.setGradeRanking(scView.getGradeRanking());
        if(gradeRankingPercent != null){
            String s = String.valueOf(gradeRankingPercent);
            s += "%";
            System.out.println("-------->" + s);
            scResultView.setGradeRankingPercent(s);
        }
        scResultView.setClassRanking(scView.getClassRanking());
        if(classRankingRercent != null){
            String s = String.valueOf(classRankingRercent);
            s += "%";
            System.out.println("-------->" + s);
            scResultView.setClassRankingPercent(s);
        }
        scResultView.setStudentName(scView.getStudentName());
        scResultView.setClassName(scView.getClassName());
        scResultView.setGrade(scView.getGrade());
        scResultView.setCourseName(scView.getCourseName());

        return scResultView;
    }

    private ScView scResultViewToScView(ScResultView scResultView) {
        BigDecimal fraction = scResultView.getFraction();
        Date createTime = scResultView.getCreateTime();
        Date updateTime = scResultView.getUpdateTime();
        String gradeRankingPercent = scResultView.getGradeRankingPercent();
        String classRankingRercent = scResultView.getClassRankingPercent();

        ScView scView = new ScView();

        scView.setId(scResultView.getId());
        if(createTime != null)
            scView.setCreateTime(createTime.getTime());
        if(updateTime != null)
            scView.setUpdateTime(updateTime.getTime());
        scView.setStudentNumber(scResultView.getStudentNumber());
        scView.setCourseNumber(scResultView.getCourseNumber());
        if(fraction != null)
            scView.setFraction(fraction.multiply(new BigDecimal(100)).intValue());
        scView.setGradeRanking(scResultView.getGradeRanking());
        if(gradeRankingPercent != null){
            gradeRankingPercent = gradeRankingPercent.replace(".", "");
            gradeRankingPercent = gradeRankingPercent.replace("%", "");
            //System.out.println("-------->" + gradeRankingPercent);
            scView.setGradeRankingPercent(Integer.valueOf(gradeRankingPercent));
        }
        scView.setClassRanking(scResultView.getClassRanking());
        if(classRankingRercent != null){
            classRankingRercent = classRankingRercent.replace(".", "");
            classRankingRercent = classRankingRercent.replace("%", "");
            //System.out.println("-------->" + classRankingRercent);
            scView.setClassRankingPercent(Integer.valueOf(classRankingRercent));
        }
        scView.setStudentName(scResultView.getStudentName());
        scView.setClassName(scResultView.getClassName());
        scView.setGrade(scResultView.getGrade());
        scView.setCourseName(scResultView.getCourseName());

        return scView;
    }

    @Transactional(rollbackOn = { Exception.class })
    public void setRanking() {
        Change change = changeDao.getByChangeName(ChangeNameConstant.SC_CHANGE);
        if(ObjectUtils.isEmpty(change)){
            throw new ServiceException("change is empty");
        }

        //System.out.println("------------>start ranking" + CommonUtils.dateToString(new Date(), CommonUtils.MFSTR));
        if(IsChangeConstant.CHANGE.equals(change.getIsChange())){
            List<String> grades = classNameDao.getAllGrades();
            List<ClassName> classNames = classNameDao.findAll();
            List<Course> courses = courseDao.findAll();
            String sort = "fraction";
            //获取每个班级的人数
            List<CountStudentView> classNumbers = studentDao.getCountByClassName();
            //获取每个年级的人数
            List<CountStudentView> gradeNumbers = studentDao.getCountByGrade();
            if(courses.size() != 0){
                for(Course course:courses) {
                    //更新班级排名
                    if (classNames.size() != 0) {
                        for (ClassName className : classNames) {
                            //System.out.println("------->courseNumber = " + course.getCourseNumber() + " className = " + className + "grade = " + className.getGrade());
                            List<Sc> scs = scDao.getByCourse_CourseNumberAndStudent_ClassNameAndStudent_Grade(course.getCourseNumber(), className.getClassName(), className.getGrade(), sort);
                            //System.out.println("------->end");

                            if(scs.size() != 0){
                                long count = 0L;
                                //获取上了这堂课的班级人数
                                for(CountStudentView countStudentView:classNumbers){
                                    if(countStudentView.getClassName().equals(className.getClassName())
                                            && countStudentView.getGrade().equals(className.getGrade())){
                                        count = countStudentView.getCount();
                                    }
                                }

                                //System.out.println("---------->classNameCount = " + count);
                                //存储成绩
                                Map<Integer, Integer> map = new HashMap<>();
                                for(int i=0; i<scs.size();++i){
                                    //System.out.println("--------->正在更新成绩" + i+" "+scs.size());
                                    Sc sc = scs.get(i);
                                    if(map.get(sc.getFraction()) == null){
                                        map.put(sc.getFraction(),i+1);
                                    }
                                    float rank = map.get(sc.getFraction());
                                    sc.setClassRanking((int)rank);
                                    double percent = rank/(int)count;
                                    //System.out.println("--------->5" + "percent = " + percent);
                                    sc.setClassRankingPercent((int)(percent*100));
                                    sc.setUpdateTime(new Date().getTime());
                                }

                                System.out.println("------>进行批量更新");
                                scDao.batchUpdate(scs);
                                System.out.println("------>批量更新结束");
                            }
                        }
                    }

                    //更新年级排名
                    if (grades.size() != 0) {
                        for (String grade : grades) {
                            //System.out.println("------->courseNumber = " + course.getCourseNumber() + " grade = " + grade);
                            List<Sc> scs = scDao.getByCourse_CourseNumberAndStudent_Grade(course.getCourseNumber(), grade, sort);
                            //System.out.println("------->end");

                            if(scs.size() != 0){
                                long count = 0L;
                                //获取上了这堂课的班级人数
                                for(CountStudentView countStudentView:gradeNumbers){
                                    if(countStudentView.getGrade().equals(grade)){
                                        count = countStudentView.getCount();
                                    }
                                }
                                //System.out.println("---------->gradeCount = " + count);
                                //存储成绩
                                Map<Integer, Integer> map = new HashMap<>();

                                for(int i=0; i<scs.size();++i){
                                    //System.out.println("--------->正在更新成绩" + i+" "+scs.size());
                                    Sc sc = scs.get(i);
                                    if(map.get(sc.getFraction()) == null){
                                        map.put(sc.getFraction(),i+1);
                                    }
                                    float rank = map.get(sc.getFraction());
                                    sc.setGradeRanking((int)rank);
                                    //System.out.println("--------->正在设置年级排名百分比");
                                    sc.setGradeRankingPercent((int)(rank/(int)count));
                                    //System.out.println("--------->设置年级排名百分比结束");
                                    sc.setUpdateTime(new Date().getTime());
                                }
                                System.out.println("------>进行批量更新");
                                scDao.batchUpdate(scs);
                                System.out.println("------>批量更新结束");
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
