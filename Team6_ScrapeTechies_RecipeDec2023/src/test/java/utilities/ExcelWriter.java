package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelWriter {
	private String fileName;
    private Workbook workbook;
    private Sheet sheet;
    private Set<String> uniqueRecipeIds;
	public ExcelWriter(String fileName) {
        this.fileName = fileName;
        this.workbook = new XSSFWorkbook();
        this.sheet = workbook.createSheet("Recipe Data");
        this.uniqueRecipeIds = new HashSet<>();
    }

    public void writeHeader(List<String> headers) {
    	 for (int i = 0; i < headers.size(); i++) {
    	        Row row = sheet.createRow(i);
    	        Cell cell = row.createCell(0);
    	        cell.setCellValue(headers.get(i));
    	    }
    }

    public void writeData(List<String> rowData) {
        String recipeId = rowData.get(0);

        if (!uniqueRecipeIds.contains(recipeId)) {
            int colNum = sheet.getRow(0).getPhysicalNumberOfCells(); 
            Row headerRow = sheet.getRow(0);
            Cell cell = headerRow.createCell(colNum);
            cell.setCellValue(recipeId);

            for (int i = 0; i < rowData.size(); i++) {
                Row row = sheet.createRow(i + 1); // Start from row 1 to avoid overwriting headers
                Cell dataCell = row.createCell(colNum);
                dataCell.setCellValue(rowData.get(i));
            }

            uniqueRecipeIds.add(recipeId);
        }
    }

    public void saveFile() throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}