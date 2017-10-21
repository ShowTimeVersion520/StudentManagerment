package com.showtime.controller.sc;

import com.showtime.model.entity.course.Course;
import com.showtime.model.entity.student.Student;
import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import com.showtime.model.view.sc.ScResultView;
import com.showtime.model.view.sc.ScView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.service.sc.ScService;
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
import java.util.Date;

/**
 * <b><code>ScController</code></b>
 * <p/>
 * Sc的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-rs 1.0.0
 */
@Api(value = "成绩接口")
@RestController
@RequestMapping(value = "/api/v1")
public class ScController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ScController.class);

    /** The service. */
    @Autowired
    private ScService scService;

    @ApiOperation(value = "创建成绩", notes = "创建一个成绩")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/scs", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createSc(
            @ApiParam(value = "成绩", required = true) @RequestBody ScView scView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = scService.saveEntity(scView);
            // 获取刚刚保存的实体
            ScView scView1 = scService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/scs/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<ScView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, scView1);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_INSERT_FAILURE;;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除成绩", notes = "通过id删除成绩")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/scs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSc(
            @ApiParam(value = "成绩id", required = true) @PathVariable(value = "id") long id) {
        try {
            scService.deleteEntity(id);
            // 封装返回信息
            Message<ScView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除成绩", notes = "批量删除成绩")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/scs", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteScs(
            @ApiParam(value = "成绩ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            scService.deleteEntities(ids);
            // 封装返回信息
            Message<ScView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "更新成绩", notes = "更新成绩信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/scs/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateScs(
            @ApiParam(value = "成绩id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "成绩信息", required = true) @RequestBody ScView scView) {
        try {
            scView.setId(id);
            scService.updateEntity(scView);
            // 封装返回信息
            Message<ScView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, scView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取单个成绩", notes = "通过id获取成绩")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/scs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSc(
            @ApiParam(value = "成绩id", required = true) @PathVariable(value = "id") long id) {
        try {
            final ScView scView = scService.getEntity(id);
            // 封装返回信息
            Message<ScView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, scView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取成绩列表", notes = "通过查询条件获取成绩列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/scs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getScs(
                    @ApiParam(value = "学号", defaultValue = "", required = false) @RequestParam(value = "studentNumber", defaultValue = "-2147483648",  required = false) Integer studentNumber,
                    @ApiParam(value = "学生姓名", defaultValue = "", required = false) @RequestParam(value = "studentName", defaultValue = "",  required = false) String studentName,
                    @ApiParam(value = "学生班级", defaultValue = "", required = false) @RequestParam(value = "className", defaultValue = "",  required = false) String className,
                    @ApiParam(value = "年级", defaultValue = "", required = false) @RequestParam(value = "grade", defaultValue = "",  required = false) String grade,
                    @ApiParam(value = "课程号", defaultValue = "", required = false) @RequestParam(value = "courseNumber", defaultValue = "",  required = false) String courseNumber,
                    @ApiParam(value = "课程名称", defaultValue = "", required = false) @RequestParam(value = "courseName", defaultValue = "",  required = false) String courseName,
                    @ApiParam(value = "成绩", defaultValue = "", required = false) @RequestParam(value = "fraction", defaultValue = "-2147483648",  required = false) Integer fraction,
                    @ApiParam(value = "全级排名", defaultValue = "", required = false) @RequestParam(value = "gradeRanking", defaultValue = "-2147483648",  required = false) Integer gradeRanking,
                    @ApiParam(value = "班级排名", defaultValue = "", required = false) @RequestParam(value = "classRanking", defaultValue = "-2147483648",  required = false) Integer classRanking,
                    @ApiParam(value = "排序方式", required = true) @RequestParam(value = "sort",   required = true)  String sort,
                    @ApiParam(value = "升序或降序", required = true) @RequestParam(value = "sortDirection",   required = true)  String sortDirection,
                    @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            ScResultView scResultView = new ScResultView();
            scResultView.setGrade(grade);
            scResultView.setClassName(className);
            scResultView.setCourseNumber(courseNumber);
            scResultView.setCourseName(courseName);
            scResultView.setStudentNumber(studentNumber);
            scResultView.setStudentName(studentName);
            scResultView.setGradeRanking(gradeRanking);
            scResultView.setClassRanking(classRanking);

            Page<ScResultView> scResultViews = scService
                    .getEntitiesByParms(sort,sortDirection,fraction, scResultView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<ScResultView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, scResultViews);
                return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "通过关键字搜索成绩", notes = "通过关键字搜索成绩")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful request"),
        @ApiResponse(code = 500, message = "internal server error")})
    @RequestMapping(value = "/scs/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getScByKeyword(
            @ApiParam(value = "关键字", required = true) @RequestParam(value = "keyword", defaultValue = "0", required = false) String keyword,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            Page<ScView> scViews = scService
                    .getScByKeyword(keyword, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<ScView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, scViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "更新成绩排行", notes = "更新成绩信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/scs/ranking/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateScRankings() {
        try {
            scService.setRanking();
            // 封装返回信息
            Message<ScView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }
}
