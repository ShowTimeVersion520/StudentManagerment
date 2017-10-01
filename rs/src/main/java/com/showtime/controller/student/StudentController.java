package com.showtime.controller.student;

import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import com.showtime.model.view.student.StudentView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.service.student.StudentService;
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

/**
 * <b><code>StudentController</code></b>
 * <p/>
 * Student的具体实现
 * <p/>
 * <b>Creation Time:</b> Sun Oct 01 17:12:45 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-rs 1.0.0
 */
@Api(value = "学生接口")
@RestController
@RequestMapping(value = "/api/v1")
public class StudentController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(StudentController.class);

    /** The service. */
    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "创建学生", notes = "创建一个学生")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/students", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createStudent(
            @ApiParam(value = "学生", required = true) @RequestBody StudentView studentView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = studentService.saveEntity(studentView);
            // 获取刚刚保存的实体
            StudentView studentView1 = studentService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/students/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<StudentView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, studentView1);
            return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除学生", notes = "通过id删除学生")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteStudent(
            @ApiParam(value = "学生id", required = true) @PathVariable(value = "id") long id) {
        try {
            studentService.deleteEntity(id);
            // 封装返回信息
            Message<StudentView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除学生", notes = "批量删除学生")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/students", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> deleteStudents(
            @ApiParam(value = "学生ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            studentService.deleteEntities(ids);
            // 封装返回信息
            Message<StudentView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "更新学生", notes = "更新学生信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateStudents(
            @ApiParam(value = "学生id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "学生信息", required = true) @RequestBody StudentView studentView) {
        try {
            studentView.setId(id);
            studentService.updateEntity(studentView);
            // 封装返回信息
            Message<StudentView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, studentView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取单个学生", notes = "通过id获取学生")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getStudent(
            @ApiParam(value = "学生id", required = true) @PathVariable(value = "id") long id) {
        try {
            final StudentView studentView = studentService.getEntity(id);
            // 封装返回信息
            Message<StudentView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, studentView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取学生列表", notes = "通过查询条件获取学生列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getStudents(
            @ApiParam(value = "姓名", defaultValue = "", required = false) @RequestParam(value = "name", defaultValue = "",  required = false) String name,
            @ApiParam(value = "性别 1-男 0-女", defaultValue = "", required = false) @RequestParam(value = "gender", defaultValue = "",  required = false) String gender,
            @ApiParam(value = "籍贯", defaultValue = "", required = false) @RequestParam(value = "nativePlace", defaultValue = "",  required = false) String nativePlace,
            @ApiParam(value = "班级名称", defaultValue = "", required = false) @RequestParam(value = "className", defaultValue = "",  required = false) String className,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            StudentView studentView = new StudentView();
                    studentView.setName(name);
                    studentView.setGender(gender);
                    studentView.setNativePlace(nativePlace);
                    studentView.setClassName(className);
        
            Page<StudentView> studentViews = studentService
                    .getEntitiesByParms(studentView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<StudentView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, studentViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "通过关键字搜索学生", notes = "通过关键字搜索学生")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "successful request"),
        @ApiResponse(code = 500, message = "internal server error")})
    @RequestMapping(value = "/students/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getStudentByKeyword(
            @ApiParam(value = "关键字", required = true) @RequestParam(value = "keyword", defaultValue = "0", required = false) String keyword,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            Page<StudentView> studentViews = studentService
                    .getStudentByKeyword(keyword, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<StudentView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, studentViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

}
