package testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestWriter {
	
    private static Workbook workbook;
    private static Sheet sheet;

	public static void main(String[] args) throws IOException {
		workbook = WorkbookFactory.create(new FileInputStream("src/test/resources/Data/Test.xlsx"));
        sheet = workbook.createSheet("Recipe Data");
        
        int rowNum = sheet.getLastRowNum() + 1;

        String[] properties = {
                "Recipe ID", "Recipe Name", "Recipe Category(Breakfast/lunch/snack/dinner)",
                "Food Category(Veg/non-veg/vegan/Jain)", "Ingredients", "Preparation Time", "Cooking Time",
                "Preparation method", "Nutrient values", "Targetted morbid conditions", "Recipe URL"
        };

        for (int i = 0; i < properties.length; i++) {
            Row headerRow = sheet.createRow(rowNum + i);

            Cell cellLabel = headerRow.createCell(0);
            cellLabel.setCellValue(properties[i]);
        }
        
        workbook.close();
	}
}
