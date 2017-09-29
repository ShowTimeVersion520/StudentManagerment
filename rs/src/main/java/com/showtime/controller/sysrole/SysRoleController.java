
package com.showtime.controller.sysrole;

import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.model.view.sysrole.SysRoleView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.sysrole.SysRoleService;
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

@Api(value = "后台管理角色接口")
@RestController
@RequestMapping(value = "/api/v1")
public class SysRoleController {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysRoleController.class);

    /** The service. */
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "创建后台管理角色", notes = "创建一个后台管理角色")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysroles", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createSysRole(
            @ApiParam(value = "后台管理角色", required = true) @RequestBody SysRoleView sysRoleView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = sysRoleService.saveEntity(sysRoleView);
            // 获取刚刚保存的实体
            SysRoleView sysRoleView1 = sysRoleService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/sysroles/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<SysRoleView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, sysRoleView1);
            return new ResponseEntity<>(message,headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! Detail: " + sysRoleView.toString();
            LOG.error(error, t);
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }
    }

    @ApiOperation(value = "删除后台管理角色", notes = "通过id删除后台管理角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysroles/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSysRole(
            @ApiParam(value = "后台管理角色id", required = true) @PathVariable(value = "id") long id) {
        try {
            sysRoleService.deleteEntity(id);
            // 封装返回信息
            Message<SysRoleView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS,null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "批量删除后台管理角色", notes = "批量删除后台管理角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysroles", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteSysRoles(
            @ApiParam(value = "后台管理角色ids", required = true) @RequestBody String ids) {
        try {
            sysRoleService.deleteEntities(ids);
            // 封装返回信息
            Message<SysRoleView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS,null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    /**
     * 更新后台管理角色.
     *
     * @param id the role id
     * @param sysRoleView the role view
     * @return 200
     */
    @ApiOperation(value = "更新后台管理角色", notes = "更新后台管理角色信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/sysroles/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateRole(
            @ApiParam(value = "角色id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "角色信息", required = true) @RequestBody SysRoleView sysRoleView) {
        try {
//            for(SysAuthority sysAuthority : sysRoleView.getSysAuthoritiesWithRole()){
//                System.out.println("sysRoleView: " + sysAuthority.toString());
//            }
            sysRoleView.setId(id);
            sysRoleService.updateEntity(sysRoleView);
            // 封装返回信息
            Message<SysRoleView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, sysRoleView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity!" + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取后台管理角色", notes = "通过id获取后台管理角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysroles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysRole(
            @ApiParam(value = "后台管理角色id", required = true) @PathVariable(value = "id") long id) {
        try {
            final SysRoleView sysRoleView = sysRoleService.getEntity(id);
            // 封装返回信息
            Message<SysRoleView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysRoleView);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取后台管理角色列表", notes = "通过查询条件获取后台管理角色列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/sysroles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getSysRoles(
            @ApiParam(value = "角色中文名", defaultValue = "", required = false) @RequestParam(value = "chineseName", defaultValue = "", required = false) String chineseName,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "-2147483648", required = false) int enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
//            System.out.println("角色中文名: " + chineseName);
//            System.out.println("状态: " + enabled);
//            System.out.println("页数: " + pageNumber);
//            System.out.println("每页加载量: " + pageSize);

            SysRoleView sysRoleView = new SysRoleView();
            sysRoleView.setChineseName(chineseName);
            sysRoleView.setEnabled(enabled);

            Page<SysRoleView> sysRoleViews = sysRoleService
                    .getEntitiesByParms(sysRoleView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<SysRoleView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, sysRoleViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }


}
