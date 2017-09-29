package com.showtime.controller.upload;

import com.showtime.model.view.upload.FileView;
import com.showtime.service.exception.ServiceExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api(value = "上传模块")
@RestController
@RequestMapping(value = "/api/v1")
public class UploadController {

    @Value("${pic.save.path}")
    private String savePathConfig;


    @Value("${pic.save.url}")
    private String saveUrlConfig;


    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(UploadController.class);


    @ApiOperation(value = "上传轮播图", notes = "上传轮播图")
    @RequestMapping(value = "/upload/banner/pic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
    @CrossOrigin
    public ResponseEntity<?> uploadBannerHeadImage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {

        return upload(request, response, map, "banner");
    }

    /**
     * 具体保存操作
     *
     * @param request
     * @param response
     * @param map
     * @param type
     * @return
     */
    private ResponseEntity<?> upload(HttpServletRequest request, HttpServletResponse response, ModelMap map, String type) {
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        MultipartFile file = mhs.getFile("file");
        //System.out.println("get upload request");
        ServletContext application = request.getSession().getServletContext();
        System.out.println("pic url: " + application.getRealPath("/"));
        LOG.info("pic url: " + application.getRealPath("/"));
        String savePath = savePathConfig + "/";
        String filePath = "images/" + type + "/";
        String newFileName = "";
        try {
            if (file != null && !file.isEmpty()) {

                //检查是否有文件夹
                String folderName = savePath + "images/" + type;
                File folder = new File(folderName);
                //没有则创建
                if (!folder.exists() && !folder.isDirectory()) {
                    LOG.info("创建文件夹：" + folderName);
                    folder.mkdir();
                }

                SimpleDateFormat bartDateFormat = new SimpleDateFormat
                        ("yyyyMMddHHmmss");
                newFileName = String.valueOf(bartDateFormat.format(new Date()));
                filePath = filePath + newFileName + "_" + file.getOriginalFilename();
                LOG.info("上传文件： " + filePath);
                FileUtils.writeByteArrayToFile(new File(savePath + filePath), file.getBytes());
                System.out.println(filePath);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileView fileVo = new FileView();
        fileVo.setFilePath(saveUrlConfig + filePath);
        System.out.println(fileVo.getFilePath());
        try {
//              return fileVo;
            return new ResponseEntity<>(fileVo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to upload file ! upload type: " + type;
            LOG.error(error, t);
            return ServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }

    }


    public String getSavePathConfig() {
        return savePathConfig;
    }

    public void setSavePathConfig(String savePathConfig) {
        this.savePathConfig = savePathConfig;
    }
}
