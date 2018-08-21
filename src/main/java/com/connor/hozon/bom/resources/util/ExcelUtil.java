package com.connor.hozon.bom.resources.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.*;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:
 */
public class ExcelUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";

    /***
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
     */
    public static Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook = null;
        InputStream is = new FileInputStream(filePath);
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
    public static void preReadCheck(String filePath)
            throws FileNotFoundException, FileFormatException {
        // 常规检查
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }

        if (!(filePath.endsWith(EXTENSION_XLS) || filePath
                .endsWith(EXTENSION_XLSX))) {
            throw new FileFormatException("传入的文件不是excel");
        }
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
//				System.out.println("======================="
//						+ sheet.getSheetName() + "=========================");

                int firstRowIndex = sheet.getFirstRowNum();
                int lastRowIndex = sheet.getLastRowNum();
                // HSSFSheet s = wb.createSheet(sheet.getSheetName());
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

//							System.out.print(cellValue + "\t");
                            HSSFCell cell1 = currentRow1
                                    .createCell(columnIndex);
                            cell1.setCellValue(cellValue);
                            HSSFRichTextString cellValue111 = (HSSFRichTextString) cell1
                                    .getRichStringCellValue();
//							System.out.print(cellValue + "\t");
                        } else {
                            String cellValue = String.valueOf(currentCell
                                    .getNumericCellValue());
//							System.out.print(cellValue + "\t");
                            HSSFCell cell1 = currentRow1
                                    .createCell(columnIndex);
                            cell1.setCellValue(cellValue);
                        }

                    }

                }

                /*
                 * FileOutputStream fos = new
                 * FileOutputStream("D:\\Documents\\test2.xls");
                 *
                 * // update(s,sheet); wb.write(fos); wb.close(); fos.close();
                 */
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
    private static String getCellValue(Cell cell, boolean treatAsStr) {
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
    private static void setCellValue(Cell cell, boolean treatAsStr,
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
     * 写文件到指定的电脑位置
     * @param filePath
     */
    public static void writeFileToLocation(String filePath,File dest) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = new FileInputStream(filePath);
            outputStream = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, bytesRead);
            }
        }finally {
            inputStream.close();
            outputStream.close();
        }
    }

}
