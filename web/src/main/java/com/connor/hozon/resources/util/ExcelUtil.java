package com.connor.hozon.resources.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:excel 操作工具类
 */
public class ExcelUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";


    private static final String SEVER_LOCATION = "C:\\file\\upload";//服务器上传文件地址
    private static final long FILE_MAX_SIZE = 10485760L;//文件大小限制 最大为10MB 这里采用字节


    public static boolean checkFileSize(long size) {
        if (size == 0L) {
            return false;
        }
        if (size > FILE_MAX_SIZE) {
            return false;
        }
        return true;
    }


    /**
     * 写文件到指定的位置
     *
     * @param file
     * @param fileName
     * @throws Exception
     */
    public static String uploadFileToLocation(byte[] file, String fileName) throws Exception {
        File targetFile = new File(SEVER_LOCATION);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(SEVER_LOCATION + "\\" + fileName);
            out.write(file);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        return SEVER_LOCATION + "\\" + fileName;
    }


    /**
     * 删除文件
     *
     * @param pathname 文件名（包括路径）
     */
    public static void deleteFile(String pathname) throws FileNotFoundException {

        File file = new File(pathname);
        if (file.isFile() && file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException("File[" + pathname + "] not exists!");
        }

    }


    /**
     * 删除服务器指定位置的全部文件
     *
     * @param
     */
    public static void deleteFile() throws FileNotFoundException {
        File file = new File(SEVER_LOCATION);
        deleteAllFile(file);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     */
    private static boolean deleteAllFile(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteAllFile(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /***
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
     */
    public static Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook = null;
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        if (filePath.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(is);
            //workbook = new HSSFWorkbook(is);
        }
        return workbook;
    }

    /**
     * 文件检查
     *
     * @param fileName
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    public static boolean preReadCheck(String fileName) {
        if (!(fileName.endsWith(EXTENSION_XLS) || fileName
                .endsWith(EXTENSION_XLSX))) {
            return false;
        }
        return true;
    }

    /**
     * 获取单元格
     *
     * @param row
     * @param cellIndex
     * @return
     */
    public static Cell getCell(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (null == cell) {
            return new Cell() {
                @Override
                public String getStringCellValue() {
                    return "";
                }

                @Override
                public int getColumnIndex() {
                    return 0;
                }

                @Override
                public int getRowIndex() {
                    return 0;
                }

                @Override
                public Sheet getSheet() {
                    return null;
                }

                @Override
                public Row getRow() {
                    return null;
                }

                @Override
                public void setCellType(int cellType) {

                }

                @Override
                public int getCellType() {
                    return 1;
                }

                @Override
                public int getCachedFormulaResultType() {
                    return 0;
                }

                @Override
                public void setCellValue(double value) {

                }

                @Override
                public void setCellValue(Date value) {

                }

                @Override
                public void setCellValue(Calendar value) {

                }

                @Override
                public void setCellValue(RichTextString value) {

                }

                @Override
                public void setCellValue(String value) {

                }

                @Override
                public void setCellFormula(String formula)
                        throws FormulaParseException {

                }

                @Override
                public String getCellFormula() {
                    return null;
                }

                @Override
                public double getNumericCellValue() {
                    return 0;
                }

                @Override
                public Date getDateCellValue() {
                    return null;
                }

                @Override
                public RichTextString getRichStringCellValue() {
                    return null;
                }

                @Override
                public void setCellValue(boolean value) {

                }

                @Override
                public void setCellErrorValue(byte value) {

                }

                @Override
                public boolean getBooleanCellValue() {
                    return false;
                }

                @Override
                public byte getErrorCellValue() {
                    return 0;
                }

                @Override
                public void setCellStyle(CellStyle style) {
                }

                @Override
                public CellStyle getCellStyle() {
                    return null;
                }

                @Override
                public void setAsActiveCell() {
                }

                @Override
                public CellAddress getAddress() {
                    return null;
                }

                @Override
                public void setCellComment(Comment comment) {
                }

                @Override
                public Comment getCellComment() {
                    return null;
                }

                @Override
                public void removeCellComment() {
                }

                @Override
                public Hyperlink getHyperlink() {
                    return null;
                }

                @Override
                public void setHyperlink(Hyperlink link) {
                }

                @Override
                public void removeHyperlink() {

                }

                @Override
                public CellRangeAddress getArrayFormulaRange() {
                    return null;
                }

                @Override
                public boolean isPartOfArrayFormulaGroup() {
                    return false;
                }


            };
        }else{
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            return cell;
        }

    }
    /**
     * 写Excel文件
     *
     * @param fileName : 下载文件名称
     * @param title    : 标题
     * @param dataList : 内容
     * @return
     * @throws Exception
     */
    public static boolean writeExcel(String fileName, String[] title, List<String[]> dataList, String str, HttpServletRequest request) throws Exception {
        boolean flag = false;
        try {
//            Object obj = ExcelUtil.class.getClassLoader();
            String realPath = request.getServletContext().getRealPath(
                    "//WEB-INF//classes//");
            String pathx = realPath + "static/files/tableExport.xlsx";
            File f = null;
            //以属性文件进行控制调试阶段用到的代码
//            PropertiesHelper helper = new PropertiesHelper();
//            Properties prox = helper.load();
//            boolean isDownload = Boolean.valueOf(prox.getProperty("DOWNLOAD_FILE_DEBUG"));
//            helper.load();
//            if (!isDownload) {
//                f = new File(pathx);
//            } else {
//                f = new File(ExcelUtil.class.getClassLoader().getResource("static/files/tableExport.xlsx").getFile());// 声明File对象
//            }

            f = new File(pathx);
            if (f == null || !f.exists()) {
                f = new File(ExcelUtil.class.getClassLoader().getResource("static/files/tableExport.xlsx").getFile());
                if(f == null || !f.exists())
                return false;
            }
            FileOutputStream fos = new FileOutputStream(f);
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFCellStyle titleCellStyle = wb.createCellStyle();
            titleCellStyle.setBorderTop(CellStyle.BORDER_THIN);
            titleCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            titleCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            titleCellStyle.setBorderRight(CellStyle.BORDER_THIN);
            titleCellStyle.setWrapText(true);//设置自动换行
            titleCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中
            titleCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中
            titleCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            //IndexedColors.GREY_25_PERCENT.getIndex()
            titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            XSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 12);// 设置字体大小
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//字体加粗
            titleCellStyle.setFont(font);

            int pageSize = 65535;
            int listSize = dataList.size();
            int sheetSize = 0;
            if (listSize % pageSize == 0) {
                sheetSize = listSize / pageSize;
            } else {
                sheetSize = (listSize / pageSize) + 1;
            }

            if (sheetSize == 0) {//没有数据
                XSSFSheet sheet = wb.createSheet();
                XSSFRow row = sheet.createRow(0);
                row.setHeight((short) 600);//目的是想把行高设置成25px
                row.setRowStyle(titleCellStyle);
                sheet.setDefaultColumnWidth(20);
                sheet.setDefaultRowHeightInPoints(20);
                //title
                for (int i = 0; i < title.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(titleCellStyle);
                    cell.setCellValue(title[i]);
                }
            } else {
                int start = 0;
                int end = 0;
                for (int s = 0; s < sheetSize; s++) {
                    start = s * pageSize;
                    if (s == (sheetSize - 1))
                        end = listSize;
                    else
                        end = (s + 1) * pageSize;
                    XSSFSheet sheet = wb.createSheet();
                    XSSFRow row = sheet.createRow(0);
                    row.setHeight((short) 600);//目的是想把第一行行高设置成25px
                    //row.setRowStyle(titleCellStyle);
                    sheet.setDefaultColumnWidth(20);//宽
                    sheet.setDefaultRowHeightInPoints(20);//高
                    //title
                    for (int i = 0; i < title.length; i++) {
                        XSSFCell cell = row.createCell(i);
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellStyle(titleCellStyle);
                        cell.setCellValue(title[i]);
                    }
                    //context
                    XSSFCellStyle cellStyle = wb.createCellStyle();
                    cellStyle.setBorderTop(CellStyle.BORDER_THIN);
                    cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
                    cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
                    cellStyle.setBorderRight(CellStyle.BORDER_THIN);
                    int row_index = 1;
                    for (int j = start; j < end; j++) {
                        XSSFRow data_row = sheet.createRow(row_index);
                        row_index++;
                        String[] cellList = dataList.get(j);
                        int len = cellList.length;
                        for (int k = 0; k < len; k++) {
                            //列
                            XSSFCell cell = data_row.createCell(k);
                            String value = cellList[k];
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(value);
                            //列中加下拉框
                            if ("pbom".equals(str)) {
                                String[] datas = new String[]{"MAKE", "BUY"};
                                String[] datas2 = new String[]{"Y", "N"};
                                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
                                XSSFDataValidationHelper dvHelper2 = new XSSFDataValidationHelper(sheet);
                                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(datas);
                                XSSFDataValidationConstraint dvConstraint2 = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(datas2);
                                CellRangeAddressList addressList = new CellRangeAddressList(1, end, 12, 12);
                                CellRangeAddressList addressList2 = new CellRangeAddressList(1, end, 13, 14);
                                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                                XSSFDataValidation validation2 = (XSSFDataValidation) dvHelper2.createValidation(dvConstraint2, addressList2);
                                // 07默认setSuppressDropDownArrow(true);
                                // validation.setSuppressDropDownArrow(true);
                                // validation.setShowErrorBox(true);
                                sheet.addValidationData(validation);
                                sheet.addValidationData(validation2);
                            } else if ("mbom".equals(str)) {
                                String[] datas = new String[]{"生产", "财务"};
                                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
                                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(datas);
                                CellRangeAddressList addressList = new CellRangeAddressList(1, end, 23, 23);
                                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                                sheet.addValidationData(validation);
                            }

                        }
                    }
                }
            }
            wb.write(fos);
//            wb.write(out);
            // 弹出下载对话框
//            out.close();
            fos.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return flag;
    }


    /***************************zhudb新增*********************/
    public static List<String[]> readExcel(MultipartFile file) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();

                //获取第一行及标题行的列数
                Row rowTitle = sheet.getRow(0);
                if(rowTitle==null){
                    throw new IOException("Excel文件第一行表头不存在");
                }
                //获得标题的列数
                int lastCellNum = rowTitle.getPhysicalNumberOfCells();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null||row.getPhysicalNumberOfCells()==0){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();

                    String[] cells = new String[lastCellNum];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }
    public static void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {

        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
