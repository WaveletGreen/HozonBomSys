package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:
 */
@Controller
@RequestMapping(value = "file")
public class FileUploadController extends BaseController{

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public void fileupload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "D:/abc";
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath+"目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        //消息提示
        String message = "";
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                System.out.println("没有文件上传");
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                }
            }
        }catch (Exception e) {
            message= "文件上传失败！";
            e.printStackTrace();

        }
    }


    @RequestMapping(value = "/upload/demo", method = RequestMethod.POST)
    public void filesUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("file") MultipartFile file) {



//        InputStream inputStream = file.getInputStream();
//        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
//        HSSFWorkbook wb = new HSSFWorkbook(poifsFileSystem);
//        // 2.读取页脚sheet
//        HSSFSheet sheetAt = wb.getSheetAt(0);
//        // 3.循环读取某一行
//        for (Row row : sheetAt) {
//            // 4.读取每一行的单元格
//            String stringCellValue = row.getCell(0).getStringCellValue(); // 第一列数据
//            String stringCellValue2 = row.getCell(1).getStringCellValue();// 第二列
//            // 写多少个具体看大家上传的文件有多少列.....
//            // 测试是否读取到数据,及数据的正确性
//            System.out.println(stringCellValue);
//        }


        //设置返回信息的编码格式及类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String callbackName = request.getParameter("callback");



        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject resultJson = new JSONObject();



        // 获取文件名
        String filename = file.getOriginalFilename();

        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 传入的文件保存的路径，如果没有先进行创建文件
//                ConfigUtil configUtil = new ConfigUtil();
//                String FilePath = configUtil.path() + File.separator + filename;
                String FilePath = "D:/abc";
                File dir = new File(FilePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 转存文件，否则所创建的是个文件夹
                file.transferTo(new File(FilePath));
                // 获取需要处理的文件
                resultJson.put("data", "成功");
                response.getWriter().write(callbackName + "([" + resultJson + "])");

            } catch (Exception e) {
                System.out.println("文件转存失败");
            }
        } else {
            try {
                resultJson.put("data", "空文件");
                response.getWriter().write(callbackName + "([" + resultJson + "])");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

