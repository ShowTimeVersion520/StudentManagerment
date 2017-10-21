package com.showtime.controller.course;

import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import com.showtime.model.view.course.CourseView;
import com.showtime.model.view.course.PreCourseView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.service.course.CourseService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * <b><code>CourseController</code></b>
 * <p/>
 * Course的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-rs 1.0.0
 */
@Api(value = "课程接口")
@RestController
@RequestMapping(value = "/api/v1")
public class CourseController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(CourseController.class);

    /** The service. */
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "创建课程", notes = "创建一个课程")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/courses", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createCourse(
            @ApiParam(value = "课程", required = true) @RequestBody CourseView courseView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = courseService.saveEntity(courseView);
            // 获取刚刚保存的实体
            CourseView courseView1 = courseService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/courses/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<CourseView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, courseView1);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_INSERT_FAILURE;;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除课程", notes = "通过id删除课程")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteCourse(
            @ApiParam(value = "课程id", required = true) @PathVariable(value = "id") long id) {
        try {
            courseService.deleteEntity(id);
            // 封装返回信息
            Message<CourseView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除课程", notes = "批量删除课程")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/courses", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteCourses(
            @ApiParam(value = "课程ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            courseService.deleteEntities(ids);
            // 封装返回信息
            Message<CourseView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "更新课程", notes = "更新课程信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/courses/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateCourses(
            @ApiParam(value = "课程id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "课程信息", required = true) @RequestBody CourseView courseView) {
        try {
            courseView.setId(id);
            courseService.updateEntity(courseView);
            // 封装返回信息
            Message<CourseView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, courseView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取单个课程", notes = "通过id获取课程")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCourse(
            @ApiParam(value = "课程id", required = true) @PathVariable(value = "id") long id) {
        try {
            final CourseView courseView = courseService.getEntity(id);
            // 封装返回信息
            Message<CourseView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, courseView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取课程列表", notes = "通过查询条件获取课程列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/courses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCourses(
                    @ApiParam(value = "课程编号", defaultValue = "", required = false) @RequestParam(value = "courseNumber", defaultValue = "",  required = false) String courseNumber,
                    @ApiParam(value = "课程名称", defaultValue = "", required = false) @RequestParam(value = "name", defaultValue = "",  required = false) String name,
                    @ApiParam(value = "学时", defaultValue = "", required = false) @RequestParam(value = "learnHours", defaultValue = "",  required = false) String learnHours,
                    @ApiParam(value = "学分", defaultValue = "", required = false) @RequestParam(value = "credit", defaultValue = "",  required = false) String credit,
                    @ApiParam(value = "平均分", defaultValue = "", required = false) @RequestParam(value = "avgFraction",   required = false) BigDecimal avgFraction,
                    @ApiParam(value = "先修课id号", defaultValue = "", required = false) @RequestParam(value = "preCourse", defaultValue = "-2147483648",  required = false) Long preCourse,
                    @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            CourseView courseView = new CourseView();
                    courseView.setCourseNumber(courseNumber);
                    courseView.setName(name);
                    courseView.setLearnHours(learnHours);
                    courseView.setCredit(credit);
                    courseView.setAvgFraction(avgFraction);
        
            Page<CourseView> courseViews = courseService
                    .getEntitiesByParms(courseView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<CourseView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, courseViews);
                return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "通过关键字搜索课程", notes = "通过关键字搜索课程")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful request"),
        @ApiResponse(code = 500, message = "internal server error")})
    @RequestMapping(value = "/courses/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCourseByKeyword(
            @ApiParam(value = "关键字", required = true) @RequestParam(value = "keyword", defaultValue = "0", required = false) String keyword,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            Page<CourseView> courseViews = courseService
                    .getCourseByKeyword(keyword, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<CourseView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, courseViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

//    @ApiOperation(value = "获取课程列表", notes = "通过查询条件获取课程列表")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "successful request"),
//            @ApiResponse(code = 500, message = "internal server error") })
//    @RequestMapping(value = "/courses/preCourses/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<?> getAllPreCourses() {
//        try {
//            List<PreCourseView> preCourseViews = courseService.getAllPreCourses();
//            // 封装返回信息
//            Message<List<PreCourseView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, preCourseViews);
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } catch (Throwable t) {
//            String error = MessageDescription.OPERATION_QUERY_FAILURE;
//            LOG.error(error, t);
//            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
//            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
//        }
//    }

    @ApiOperation(value = "更新课程平均成绩", notes = "更新课程平均成绩")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/courses/avgFraction", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateCoursesAvgFraction() {
        try {
            courseService.updateCoursesAvgFraction();
            // 封装返回信息
            Message<CourseView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }
}
