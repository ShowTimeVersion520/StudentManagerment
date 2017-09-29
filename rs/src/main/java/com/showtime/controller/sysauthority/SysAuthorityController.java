
package com.showtime.controller.sysauthority;

import com.showtime.model.message.Message;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.view.sysauthority.SysAuthorityView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.sysauthority.SysAuthorityService;
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

@Api(value = "后台管理权限接口")
@RestController
@RequestMapping(value = "/api/v1")
public class SysAuthorityController {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysAuthorityController.class);

    /** The service. */
    @Autowired
    private SysAuthorityService sysAuthorityService;

    @ApiOperation(value = "创建后台管理权限", notes = "创建一个后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysauthorities", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createSysAuthority(
            @ApiParam(value = "后台管理权限", required = true) @RequestBody SysAuthorityView sysAuthorityView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = sysAuthorityService.saveEntity(sysAuthorityView);
            // 获取刚刚保存的实体
            SysAuthorityView sysAuthorityView1 = sysAuthorityService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/sysauthorities/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<SysAuthorityView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, sysAuthorityView1);
            return new ResponseEntity<>(message,headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity!" + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除后台管理权限", notes = "通过id删除后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysauthorities/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSysAuthority(
            @ApiParam(value = "后台管理权限id", required = true) @PathVariable(value = "id") long id) {
        try {
            sysAuthorityService.deleteEntity(id);
            // 封装返回信息
            Message<SysAuthorityView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS,null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除后台管理权限", notes = "批量删除后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysauthorities", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSysAuthoritys(
            @ApiParam(value = "后台管理权限ids", required = true) @RequestBody String ids) {
        try {
            sysAuthorityService.deleteEntities(ids);
            // 封装返回信息
            Message<SysAuthorityView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS,null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    /**
     * 更新后台管理权限.
     *
     * @param id the id
     * @param sysAuthorityView the view
     * @return 200
     */
    @ApiOperation(value = "更新后台管理权限", notes = "更新后台管理权限信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/sysauthorities/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateSysAuthority(
            @ApiParam(value = "权限id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "权限信息", required = true) @RequestBody SysAuthorityView sysAuthorityView) {
        try {
            sysAuthorityView.setId(id);
            sysAuthorityService.updateEntity(sysAuthorityView);
            // 封装返回信息
            Message<SysAuthorityView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysAuthorityView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity!" + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取后台管理权限", notes = "通过id获取后台管理权限")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysauthorities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysAuthority(
            @ApiParam(value = "后台管理权限id", required = true) @PathVariable(value = "id") long id) {
        try {
            final SysAuthorityView sysAuthorityView = sysAuthorityService.getEntity(id);
            // 封装返回信息
            Message<SysAuthorityView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysAuthorityView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取后台管理权限列表", notes = "通过查询条件获取后台管理权限列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysauthorities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysAuthoritys(
            @ApiParam(value = "权限", defaultValue = "", required = false) @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "-2147483648", required = false) int enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
//            System.out.println("权限中文名: " + name);
//            System.out.println("状态: " + enabled);
//            System.out.println("页数: " + pageNumber);
//            System.out.println("每页加载量: " + pageSize);

            SysAuthorityView sysAuthorityView = new SysAuthorityView();
            sysAuthorityView.setName(name);
            sysAuthorityView.setEnabled(enabled);
            Page<SysAuthorityView> sysAuthorityViews = sysAuthorityService
                    .getEntitiesByParms(sysAuthorityView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<SysAuthorityView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysAuthorityViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }


}
