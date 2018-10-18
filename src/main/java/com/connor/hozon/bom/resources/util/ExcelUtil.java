package com.connor.hozon.bom.resources.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:
 */
public class ExcelUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";


    private static final String SEVER_LOCATION = "C:\\file\\upload";//服务器上传文件地址
    private static final long FILE_MAX_SIZE = 10485760L;//文件大小限制 最大为10MB 这里采用字节






    public static boolean checkFileSize(long size){
        if(size == 0L){
            return false;
        }
        if(size>FILE_MAX_SIZE){
            return false;
        }
        return true;
    }


    /**
     * 写文件到指定的位置
     * @param file
     * @param fileName
     * @throws Exception
     */
    public static String uploadFileToLocation(byte[] file,  String fileName) throws Exception {
        File targetFile = new File(SEVER_LOCATION);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(SEVER_LOCATION+"\\" + fileName);
            out.write(file);
        }finally {
            if(out!=null){
                out.flush();
                out.close();
            }
        }
        return SEVER_LOCATION+"\\" + fileName;
    }


    /**
     * 删除文件
     *
     * @param pathname
     * 			文件名（包括路径）
     */
    public static void deleteFile(String pathname) throws FileNotFoundException{

        File file = new File(pathname);
        if(file.isFile() && file.exists()){
            file.delete();
        }
        else{
            throw new FileNotFoundException("File["+ pathname +"] not exists!");
        }

    }


    /**
     * 删除服务器指定位置的全部文件
     * @param
     */
    public static void deleteFile() throws FileNotFoundException{
        File file = new File(SEVER_LOCATION);
        deleteAllFile(file);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     */
    private static boolean deleteAllFile(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
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
     * 读取excel文件内容
     *
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    public static void readExcel(String filePath) throws FileNotFoundException,
            FileFormatException {
        // 检查
        preReadCheck(filePath);
        // 获取workbook对象
        Workbook workbook = null;

        try {
            workbook = getWorkbook(filePath);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet();

            // 读文件 一个sheet一个sheet地读取
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = workbook.getSheetAt(numSheet);

                if (sheet == null) {
                    continue;
                }

                int firstRowIndex = sheet.getFirstRowNum();
                int lastRowIndex = sheet.getLastRowNum();
                // 读取首行 即,表头
                Row firstRow = sheet.getRow(firstRowIndex);
                HSSFRow row = s.createRow(firstRowIndex);
                for (int i = firstRow.getFirstCellNum(); i < firstRow
                        .getLastCellNum(); i++) {
                    Cell cell = firstRow.getCell(i);

                    HSSFRichTextString cellValue = (HSSFRichTextString) cell
                            .getRichStringCellValue();

                    HSSFCell cell1 = row.createCell(i);

                    cell1.setCellValue(cellValue);
                }

                // 读取数据行
                for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
                    Row currentRow = sheet.getRow(rowIndex);// 当前行

                    HSSFRow currentRow1 = s.createRow(rowIndex);
                    int firstColumnIndex = currentRow.getFirstCellNum(); // 首列
                    int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
                    for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
                        Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格
                        if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                            HSSFRichTextString cellValue = (HSSFRichTextString) currentCell
                                    .getRichStringCellValue();

                            cellValue.getFontAtIndex(1);

                            HSSFCell cell1 = currentRow1
                                    .createCell(columnIndex);
                            cell1.setCellValue(cellValue);
                            HSSFRichTextString cellValue111 = (HSSFRichTextString) cell1
                                    .getRichStringCellValue();
                        } else {
                            String cellValue = String.valueOf(currentCell
                                    .getNumericCellValue());
                            HSSFCell cell1 = currentRow1
                                    .createCell(columnIndex);
                            cell1.setCellValue(cellValue);
                        }

                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取单元格
     * @param row
     * @param cellIndex
     * @return
     */
    public static Cell getCell(Row row, int cellIndex) {
        Cell cell =row.getCell(cellIndex);
        if(null==cell){
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
}
