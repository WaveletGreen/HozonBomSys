package com.connor.hozon.bom.resources.controller.file;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.service.file.*;
import com.connor.hozon.bom.resources.service.file.FileUploadService;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:
 */
@Controller
@RequestMapping(value = "file")
public class FileUploadController extends BaseController{
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private FileUploadUpdataService fileUploadUpdataService;

    @Autowired
    private FileUploadUpdataMbomService fileUploadUpdataMbomService;

    @Autowired
    private FileUploadUpdataMbom2Service fileUploadUpdataMbom2Service;

    @Autowired
    private FileUploadUpdataMbom3Service fileUploadUpdataMbom3Service;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void filesUpload (@RequestParam("file") MultipartFile file,

                             @RequestParam("projectId") String projectId,HttpServletResponse response){
        WriteResultRespDTO respDTO = fileUploadService.uploadEbomToDB(file,projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "/uploadPbom", method = RequestMethod.POST)
    public void filesUploadPbom (@RequestParam("file") MultipartFile file,
                                 @RequestParam("projectId") String projectId,HttpServletResponse response){
        //写服务
        System.out.println("uploadPbom--------");
        WriteResultRespDTO respDTO = fileUploadUpdataService.UploadPbomToDB(file,projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "/uploadMbom", method = RequestMethod.POST)
    public void filesUploadMbom (@RequestParam("file") MultipartFile file,
                                 @RequestParam("projectId") String projectId,HttpServletResponse response){
        //写服务
        System.out.println("uploadMbom--------");
        WriteResultRespDTO respDTO = fileUploadUpdataMbomService.UploadMbomToDB(file,projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "/uploadMbom2", method = RequestMethod.POST)
    public void filesUploadMbom2 (@RequestParam("file") MultipartFile file,
                                 @RequestParam("projectId") String projectId,HttpServletResponse response){
        //写服务
        System.out.println("uploadMbom2生产--------");
        WriteResultRespDTO respDTO = fileUploadUpdataMbom2Service.UploadMbomToDB(file,projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "/uploadMbom3", method = RequestMethod.POST)
    public void filesUploadMbom3 (@RequestParam("file") MultipartFile file,
                                 @RequestParam("projectId") String projectId,HttpServletResponse response){
        //写服务
        System.out.println("uploadMbom3财务--------");
        WriteResultRespDTO respDTO = fileUploadUpdataMbom3Service.UploadMbomToDB(file,projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

}

