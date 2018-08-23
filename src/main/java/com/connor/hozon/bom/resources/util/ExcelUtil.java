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


    private static final String SEVER_LOCATION = "D:\\file\\upload";//服务器上传文件地址
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
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
//    public static void preReadCheck(String filePath)
//            throws FileNotFoundException, FileFormatException {
//        // 常规检查
//        File file = new File(filePath);
//        if (!file.exists()) {
//            throw new FileNotFoundException("传入的文件不存在：" + filePath);
//        }
//
//        if (!(filePath.endsWith(EXTENSION_XLS) || filePath
//                .endsWith(EXTENSION_XLSX))) {
//            throw new FileFormatException("传入的文件不是excel");
//        }
//    }

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
     * 取单元格的值
     *
     * @param cell
     *            单元格对象
     * @param treatAsStr
     *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    public static String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    /**
     * 取单元格的值
     *
     * @param cell
     *            单元格对象
     * @param treatAsStr
     *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    public static void setCellValue(Cell cell, boolean treatAsStr,
                                     Object object) {
        if (cell == null) {
            return;
        }
        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

            cell.setCellValue((boolean) object);
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellValue((double) object);
        } else {
            cell.setCellValue((String) object);
        }
    }

    /**
     * 将多单元格的数据进行分开
     *
     * @param ws
     * @param st
     */
    public static void update(HSSFSheet ws, Sheet st) {
        List<CellRangeAddress> rangeCell = st.getMergedRegions();
        for (CellRangeAddress r : rangeCell) {

            int topLeftCol = r.getFirstColumn();
            int topLeftRaw = r.getFirstRow();
            int bottemRightCol = r.getLastColumn();
            int bottemRightRaw = r.getLastRow();

            Cell cell = ws.getRow(topLeftRaw).getCell(topLeftCol);
            for (int i = topLeftRaw + 1; i < bottemRightRaw; i++) {

                String content = getCellValue(cell, true);

                for (int j = topLeftCol; j < bottemRightCol; j++) {

                    setCellValue(ws.getRow(i).createCell(j), true, content);

                }
            }
        }
    }



    /**
     * 获取单元格
     *
     * @param row
     * @param cellIndex
     * @return
     */
    public static Cell getCell(Row row, int cellIndex) {
        //row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
        Cell cell =row.getCell(cellIndex);
        if(null==cell){
            return new Cell() {
                @Override
                public String getStringCellValue() {
                    // TODO Auto-generated method stub
                    return "";
                }

                @Override
                public int getColumnIndex() {
                    // TODO Auto-generated method stub
                    return 0;
                }

                @Override
                public int getRowIndex() {
                    // TODO Auto-generated method stub
                    return 0;
                }

                @Override
                public Sheet getSheet() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public Row getRow() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void setCellType(int cellType) {
                    // TODO Auto-generated method stub

                }

                @Override
                public int getCellType() {
                    // TODO Auto-generated method stub
                    return 1;
                }

                @Override
                public int getCachedFormulaResultType() {
                    // TODO Auto-generated method stub
                    return 0;
                }

                @Override
                public void setCellValue(double value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void setCellValue(Date value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void setCellValue(Calendar value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void setCellValue(RichTextString value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void setCellValue(String value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void setCellFormula(String formula)
                        throws FormulaParseException {
                    // TODO Auto-generated method stub

                }

                @Override
                public String getCellFormula() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public double getNumericCellValue() {
                    // TODO Auto-generated method stub
                    return 0;
                }

                @Override
                public Date getDateCellValue() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public RichTextString getRichStringCellValue() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void setCellValue(boolean value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void setCellErrorValue(byte value) {
                    // TODO Auto-generated method stub

                }

                @Override
                public boolean getBooleanCellValue() {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public byte getErrorCellValue() {
                    // TODO Auto-generated method stub
                    return 0;
                }

                @Override
                public void setCellStyle(CellStyle style) {
                    // TODO Auto-generated method stub

                }

                @Override
                public CellStyle getCellStyle() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void setAsActiveCell() {
                    // TODO Auto-generated method stub

                }

                @Override
                public CellAddress getAddress() {
                    return null;
                }

                @Override
                public void setCellComment(Comment comment) {
                    // TODO Auto-generated method stub

                }

                @Override
                public Comment getCellComment() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void removeCellComment() {
                    // TODO Auto-generated method stub

                }

                @Override
                public Hyperlink getHyperlink() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public void setHyperlink(Hyperlink link) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void removeHyperlink() {

                }

                @Override
                public CellRangeAddress getArrayFormulaRange() {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public boolean isPartOfArrayFormulaGroup() {
                    // TODO Auto-generated method stub
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
