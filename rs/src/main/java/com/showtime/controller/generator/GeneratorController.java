package com.showtime.controller.generator;

import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.model.message.Message;
import com.showtime.model.view.generator.GeneratorView;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
import com.showtime.service.generator.GeneratorService;
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

@Api(value = "后缀生成接口")
@RestController
@RequestMapping(value = "/api/v1")
public class GeneratorController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(GeneratorController.class);

    /** The service. */
    @Autowired
    private GeneratorService generatorService;

    @ApiOperation(value = "创建后缀生成", notes = "创建一个后缀生成")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/generators", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createGenerator(
            @ApiParam(value = "后缀生成", required = true) @RequestBody GeneratorView generatorView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = generatorService.saveEntity(generatorView);
            // 获取刚刚保存的实体
            GeneratorView generatorView1 = generatorService.getEntity(Long.parseLong(id));
            // 获取刚刚保存的实体
            HttpHeaders headers = new HttpHeaders();
            // 设置http的headers
            headers.setLocation(ucBuilder.path("/api/v1/generators/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<GeneratorView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, generatorView1);
            return new ResponseEntity<>(message,headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity!" + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除后缀生成", notes = "通过id删除后缀生成")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/generators/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteGenerator(
            @ApiParam(value = "后缀生成id", required = true) @PathVariable(value = "id") long id) {
        try {
            generatorService.deleteEntity(id);
            // 封装返回信息
            Message<GeneratorView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS,null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除后缀生成", notes = "批量删除后缀生成")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/generators", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteGenerators(
            @ApiParam(value = "后缀生成ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            generatorService.deleteEntities(ids);
            // 封装返回信息
            Message<GeneratorView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS,null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "更新后缀生成", notes = "更新后缀生成信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/generators/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateGenerators(
            @ApiParam(value = "后缀生成id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "后缀生成信息", required = true) @RequestBody GeneratorView generatorView) {
        try {
            generatorView.setId(id);
            generatorService.updateEntity(generatorView);
            // 封装返回信息
            Message<GeneratorView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS,generatorView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity!" + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取单个后缀生成", notes = "通过id获取后缀生成")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/generators/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGenerator(
            @ApiParam(value = "后缀生成id", required = true) @PathVariable(value = "id") long id) {
        try {
            final GeneratorView generatorView = generatorService.getEntity(id);
            Message<GeneratorView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS,generatorView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取后缀生成列表", notes = "通过查询条件获取后缀生成列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/generators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGenerators(
            //@ApiParam(value = "用户名", defaultValue = "", required = false) @RequestParam(value = "userName", defaultValue = "", required = false) String userName,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "-2147483648", required = false) int enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            GeneratorView generatorView = new GeneratorView();
            Page<GeneratorView> generatorViews = generatorService
                    .getEntitiesByParms(generatorView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<GeneratorView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, generatorViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

}
