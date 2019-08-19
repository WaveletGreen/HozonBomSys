/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.controller.sys;


import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
import cn.net.connor.hozon.services.common.util.DictCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * 类描述：实现附件上传的实现类
 * @auther linzf
 * @create 2017/8/24 0024
 */
@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    HzFeatureValueDao hzFeatureValueDao;

    //文件上传相关代码
    @RequestMapping(value = "uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam("file_data") MultipartFile file) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("msg", "上传文件为空");
            return result;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = DictCache.getDictValue("fileTempPath", "fileTempPath");
        if (filePath == null || filePath.equalsIgnoreCase("")) {
            filePath = "E://test//";
        }
        // 解决中文问题，liunx下中文路径，图片显示问题
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            result.put("msg", "上传文件成功");
            result.put("success", true);
            return result;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("msg", "上传文件失败");
        result.put("success", false);
        return result;
    }

    //文件下载相关代码
//    @RequestMapping(value = "/download", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
////            ,produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
//    )
//    public String downloadFile(org.apache.catalina.servlet4preview.http.HttpServletRequest request, HttpServletResponse response
//            , @RequestParam List<String> uids
//            , @RequestParam List<String> columns
//            , @RequestParam List<String> fields
//    ) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String fileName = "static/files/EBOM导入模板.xlsx";
//        String fileName2 = "static/files/EBOM导入模板"+ UUIDHelper.generateUpperUid()+".xlsx";
//        HzFeatureValue record = hzCfg0RecordDao.selectByPrimaryKey(uids.get(0));
//        //获取反射的get方法，必须与实体类里的方法一一对应上，否则会报方法找不到错误
//        Method method = HzFeatureValue.class.getMethod("get" + fields.get(1));
//        //执行方法，相当于用get方法
//        Object oxx = method.invoke(record);
//        if (fileName != null) {
//            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
//            String realPath = request.getServletContext().getRealPath(
//                    "//WEB-INF//classes//");
//            URL path = getClass().getClassLoader().getResource("");//.getPath();
//            File file = new File(path.getPath() + "/" + fileName);
//            File ft = new File(path.getPath() + "/" + fileName2);
//            if (!ft.exists()) {
//                ft.createNewFile();
//            }
//            if (file.exists()) {
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition",
//                        "attachment;fileName=" + fileName);// 设置文件名
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    System.out.println("success");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            ft.delete();
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }

}
