package com.showtime.controller.banner;


import com.showtime.model.message.ErrorResponseMessage;
import com.showtime.model.message.Message;
import com.showtime.service.exception.ServiceExceptionUtils;
import com.showtime.model.view.banner.BannerView;
import com.showtime.service.banner.BannerService;
import com.showtime.service.commons.utils.message.MessageCode;
import com.showtime.service.commons.utils.message.MessageDescription;
import com.showtime.service.commons.utils.message.MessageStatus;
import com.showtime.service.commons.utils.message.MessageUtils;
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

@Api(value = "轮播图接口")
@RestController
@RequestMapping(value = "/api/v1")
public class BannerController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(BannerController.class);

    /** The service. */
    @Autowired
    private BannerService bannerService;

    @ApiOperation(value = "创建轮播图", notes = "创建一个轮播图")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/banners", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createBanner(
            @ApiParam(value = "轮播图", required = true) @RequestBody BannerView bannerView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            String id = bannerService.saveEntity(bannerView);
            // 获取刚刚保存的实体
            BannerView bannerView1 = bannerService.getEntity(Long.parseLong(id));
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/banners/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            Message<BannerView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, bannerView1);
            return new ResponseEntity<>(message,headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity!" + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除轮播图", notes = "通过id删除轮播图")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/banners/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteBanner(
            @ApiParam(value = "轮播图id", required = true) @PathVariable(value = "id") long id) {
        try {
            bannerService.deleteEntity(id);
            // 封装返回信息
            Message<BannerView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "删除轮播图", notes = "批量删除轮播图")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/banners", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteBanners(
            @ApiParam(value = "轮播图ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            bannerService.deleteEntities(ids);
            // 封装返回信息
            Message<BannerView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities!" + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "更新轮播图", notes = "更新轮播图信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/banners/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUser(
            @ApiParam(value = "轮播图id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "轮播图信息", required = true) @RequestBody BannerView bannerView) {
        try {
            bannerView.setId(id);
            bannerService.updateEntity(bannerView);
            // 封装返回信息
            Message<BannerView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, bannerView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity!" + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取单个轮播图", notes = "通过id获取轮播图")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/banners/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getBanner(
            @ApiParam(value = "轮播图id", required = true) @PathVariable(value = "id") long id) {
        try {
            final BannerView bannerView = bannerService.getEntity(id);
            // 封装返回信息
            Message<BannerView> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, bannerView);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }

    @ApiOperation(value = "获取轮播图列表", notes = "通过查询条件获取轮播图列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/banners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getBanners(
            //@ApiParam(value = "用户名", defaultValue = "", required = false) @RequestParam(value = "userName", defaultValue = "", required = false) String userName,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "search", defaultValue = "", required = false) String search,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "-2147483648", required = false) int enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            BannerView bannerView = new BannerView();
            // TODO: set your bean value
            //bannerView.setUserName(userName);
            bannerView.setEnabled(enabled);
            bannerView.setSearch(search);
            Page<BannerView> bannerViews = bannerService
                    .getEntitiesByParms(bannerView, pageNumber, pageSize);
            // 封装返回信息
            Message<Page<BannerView>> message = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, bannerViews);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            Message<ErrorResponseMessage> message = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new ErrorResponseMessage(t.toString()));
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(message, t);
        }
    }
}
