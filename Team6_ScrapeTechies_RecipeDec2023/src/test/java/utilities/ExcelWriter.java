package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Recipe;

public class ExcelWriter {

	String fileName;

	public ExcelWriter(String filename, String headers) {
		fileName = filename;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Recipes");

			XSSFRow rowhead = sheet.createRow((short) 0);
			List<String> lstHeaders = Arrays.asList(headers.split(","));
			for (int i = 0; i <= lstHeaders.size() - 1; i++) {
				rowhead.createCell(i).setCellValue(lstHeaders.get(i));
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			Log.info("Excel file with headers has been generated successfully.");

		} catch (Exception ex) {
			Log.info(ex.getMessage());
		}
	}

	public void WriteRecipeToExcel(Recipe recipe) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);

			int noOfColumns = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);

			for (int i = 0; i < noOfColumns; i++) {

				Cell cellValue = row.createCell(i);
				switch (i) {
				case 0:
					cellValue.setCellValue(recipe.recipeId);
					break;
				case 1:
					cellValue.setCellValue(recipe.recipeName);
					break;
				case 2:
					cellValue.setCellValue(recipe.recipeCategory.toString());
					break;
				case 3:
					cellValue.setCellValue(recipe.foodCategory.toString());
					break;
				case 4:
					cellValue.setCellValue(recipe.ingredients);
					break;
				case 5:
					cellValue.setCellValue(recipe.prepTime);
					break;
				case 6:
					cellValue.setCellValue(recipe.cookingTime);
					break;
				case 7:
					cellValue.setCellValue(recipe.preparationMethod);
					break;
				case 8:
					cellValue.setCellValue(recipe.nutritionValue);
					break;
				case 9:
					cellValue.setCellValue(recipe.targetCondition);
					break;
				case 10:
					cellValue.setCellValue(recipe.recipeURL);
					break;
				}
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			Log.info("Row added to excel");
		} catch (Exception ex) {
			Log.info(ex.getMessage());
		}
	}

	public void WriteRecipesToExcel(List<Recipe> lstRecipe) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int noOfColumns;
			Row row;
			
			for (Recipe recipe : lstRecipe) {
				
				noOfColumns = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
				row = sheet.createRow(sheet.getLastRowNum() + 1);
				
				for (int i = 0; i < noOfColumns; i++) {

					Cell cellValue = row.createCell(i);
					switch (i) {
					case 0:
						cellValue.setCellValue(recipe.recipeId);
						break;
					case 1:
						cellValue.setCellValue(recipe.recipeName);
						break;
					case 2:
						cellValue.setCellValue(recipe.recipeCategory.toString());
						break;
					case 3:
						cellValue.setCellValue(recipe.foodCategory.toString());
						break;
					case 4:
						cellValue.setCellValue(recipe.ingredients);
						break;
					case 5:
						cellValue.setCellValue(recipe.prepTime);
						break;
					case 6:
						cellValue.setCellValue(recipe.cookingTime);
						break;
					case 7:
						cellValue.setCellValue(recipe.preparationMethod);
						break;
					case 8:
						cellValue.setCellValue(recipe.nutritionValue);
						break;
					case 9:
						cellValue.setCellValue(recipe.targetCondition);
						break;
					case 10:
						cellValue.setCellValue(recipe.recipeURL);
						break;
					}
				}
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			Log.info("Rows added to excel");
		} catch (Exception ex) {
			Log.info(ex.getMessage());
		}
	}

}
