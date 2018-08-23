package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import sql.pojo.bom.HzImportEbomRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/8/23
 * @Description:
 */
public class Test3 {
    public static void main(String[] a) throws Exception{
        String textUtl = "D:\\file\\upload\\..EP10分时租赁全配置BOM清单2018.01.25.xlsx";
        //读取excel文件
        Workbook workbook = ExcelUtil.getWorkbook(textUtl);
        List<List<HzImportEbomRecord>> list1 = new ArrayList<>();
        for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
            Sheet sheet = workbook.getSheetAt(numSheet);
            int i = 1;
            while (i<=sheet.getLastRowNum()){
                List<HzImportEbomRecord> list = new ArrayList<>();
                int high = 0;
                boolean add2Y = true;
                for(int rowNum = i; rowNum <= sheet.getLastRowNum(); rowNum++){
                    Row sheetRow = sheet.getRow(rowNum);
                    if(sheetRow != null){
                        HzImportEbomRecord record = new HzImportEbomRecord();
                        String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                        record.setLevel(level);
                        record.setHigh(high);
                        if(level.equals("2") || level.equals("2Y")){
                            if(add2Y){
                                list.add(record);
                                high++;
                                i++;
                                add2Y =false;
                            }else {
                                break;
                            }
                        }  else {
                            list.add(record);
                            high++;
                            i++;
                        }

                    }
                }

                list1.add(list);
            }
        }
        System.out.println(JSON.toJSONString(list1));
        int sum = 0;
        for(List<HzImportEbomRecord> list:list1){
            sum+=list.size();
        }
        System.out.println(sum);
    }
}
