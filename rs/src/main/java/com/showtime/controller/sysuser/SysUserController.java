
package com.showtime.controller.sysuser;

import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import com.showtime.model.view.sysuser.SysUserView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.service.sysuser.SysUserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Api(value = "后台管理用户接口")
@RestController
@RequestMapping(value = "/api/v1")
public class SysUserController {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysUserController.class);

    /** The service. */
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "创建后台管理用户", notes = "创建一个后台管理用户")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysusers", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createSysUser(
            @ApiParam(value = "后台管理用户", required = true) @RequestBody SysUserView sysUserView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = sysUserService.saveEntity(sysUserView);
            // 获取刚刚保存的实体
            SysUserView sysUserView1 = sysUserService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/sysusers/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<SysUserView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, sysUserView1);
            return new ResponseEntity<>(message,headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity!" + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除后台管理用户", notes = "通过id删除后台管理用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysusers/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSysUser(
            @ApiParam(value = "后台管理用户id", required = true) @PathVariable(value = "id") long id) {
        try {
            sysUserService.deleteEntity(id);
            // 封装返回信息
            Message<SysUserView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除后台管理用户", notes = "批量删除后台管理用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysusers", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSysUsers(
            @ApiParam(value = "后台管理用户ids", required = true) @RequestBody String ids) {
        try {
            sysUserService.deleteEntities(ids);
            // 封装返回信息
            Message<SysUserView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    /**
     * 更新后台管理用户.
     *
     * @param id the role id
     * @param sysUserView the role view
     * @return 200
     */
    @ApiOperation(value = "更新后台管理用户", notes = "更新后台管理用户信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/sysusers/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUser(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "用户信息", required = true) @RequestBody SysUserView sysUserView) {
        try {
            sysUserView.setId(id);
            sysUserService.updateEntity(sysUserView);
            // 封装返回信息
            Message<SysUserView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysUserView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity!";
            LOG.error(error, t);
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }
    }

    @ApiOperation(value = "获取后台管理用户", notes = "通过id获取后台管理用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysusers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysUser(
            @ApiParam(value = "后台管理用户id", required = true) @PathVariable(value = "id") long id) {
        try {
            final SysUserView sysUserView = sysUserService.getEntity(id);
            // 封装返回信息
            Message<SysUserView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysUserView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取后台管理用户", notes = "通过id获取后台管理用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysusers/username/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysUserByUserName(
            @ApiParam(value = "后台管理用户userName", required = true) @PathVariable(value = "username") String userName) {
        try {
            final SysUserView sysUserView = sysUserService.getEntity(userName);
            Message<SysUserView> message = null;
            if(ObjectUtils.isEmpty(sysUserView)){
                // 封装返回信息
                message = MessageUtils.setMessage(MessageCode.LOGIN_USERNAME_NOT_EXIST, MessageStatus.SUCCESS, MessageDescription.LOGIN_USERNAME_NOT_EXIST, sysUserView);
            }else {
                // 封装返回信息
                message = MessageUtils.setMessage(MessageCode.LOGIN_USERNAME_EXIST, MessageStatus.SUCCESS, MessageDescription.LOGIN_USERNAME_EXIST, sysUserView);
            }
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!";
            LOG.error(error, t);
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }
    }

    @ApiOperation(value = "获取后台管理用户列表", notes = "通过查询条件获取后台管理用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysusers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysUsers(
            @ApiParam(value = "用户名", defaultValue = "", required = false) @RequestParam(value = "userName", defaultValue = "", required = false) String userName,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "-2147483648", required = false) int enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
//            System.out.println("用户名: " + userName);
//            System.out.println("状态: " + enabled);
//            System.out.println("页数: " + pageNumber);
//            System.out.println("每页加载量: " + pageSize);

            SysUserView sysUserView = new SysUserView();
            sysUserView.setUserName(userName);
            sysUserView.setEnabled(enabled);

            Page<SysUserView> sysUserViews = sysUserService
                    .getEntitiesByParms(sysUserView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<SysUserView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysUserViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }


}
