package utilities;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    String filePath;
    Workbook workbook;
    
    public ExcelReader(String filePath) {
        this.filePath = filePath;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(fileInputStream);
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }

//    public List<String> readColumnFromExcel(int columnIndex) {
//        List<String> columnData = new ArrayList<>();
//
//        try {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//                Row row = sheet.getRow(rowIndex);
//
//                if (row != null) {
//                    Cell cell = row.getCell(columnIndex);
//
//                    if (cell != null) {
//                        columnData.add(cell.getStringCellValue());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return columnData;
//    }
    public List<List<String>> readColumnsFromExcel(int[] columnIndices,int startRow) {
        List<List<String>> columnDataList = new ArrayList<>();

        try {
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    List<String> rowData = new ArrayList<>();
                    for (int columnIndex : columnIndices) {
                        Cell cell = row.getCell(columnIndex);

                        if (cell != null) {
                            rowData.add(cell.getStringCellValue());
                        } else {
                            rowData.add(""); // Add an empty string if the cell is null
                        }
                    }
                    columnDataList.add(rowData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return columnDataList;
    }


    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
   
    }}